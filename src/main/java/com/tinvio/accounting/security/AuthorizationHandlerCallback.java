package com.tinvio.accounting.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jwt.JWTClaimsSet;
import com.tinvio.accounting.constant.Constants;
import com.tinvio.accounting.model.entity.UserEntity;
import com.tinvio.accounting.model.enums.ResultCode;
import com.tinvio.accounting.model.enums.UserType;
import com.tinvio.accounting.model.response.ErrorResponse;
import com.tinvio.accounting.repository.UserRepository;
import com.tinvio.collect.auth.Authorization;
import com.tinvio.collect.auth.AuthorizationHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class AuthorizationHandlerCallback implements AuthorizationHandler {

    private final ObjectMapper mapper;

    @Autowired
    private UserRepository userRepository;

    @Value("${auth.secret}")
    private String secretKey;

    private static final String ROLE_FINANCE_ADMIN = "finance_admin";

    @Autowired
    public AuthorizationHandlerCallback(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public String tokenSecret() {
        return this.secretKey;
    }

    @Override
    public void tokenAbort(TokenStatus tokenStatus, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.resetBuffer();
        response.setStatus(401);
        response.setHeader("Content-Type", "application/json");
        ServletOutputStream outputStream = response.getOutputStream();

        if (tokenStatus.equals(TokenStatus.Invalid)) {
            String errorResponseAsString = mapper.writeValueAsString(
                    new ErrorResponse(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "Token is invalid", tokenStatus.name()));
            outputStream.print(errorResponseAsString);
            response.flushBuffer();
            return;
        }

        if (tokenStatus.equals(TokenStatus.Expired)) {
            String errorResponseAsString = mapper.writeValueAsString(
                    new ErrorResponse(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "Token is expired", tokenStatus.name()));
            outputStream.print(errorResponseAsString);
            response.flushBuffer();
            return;
        }

        if (tokenStatus == TokenStatus.AuthHeaderInvalid || tokenStatus == TokenStatus.AuthHeaderMissing) {
            String errorResponseAsString = mapper.writeValueAsString(
                    new ErrorResponse(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "Auth header is missing or invalid", tokenStatus.name()));
            outputStream.print(errorResponseAsString);
            response.flushBuffer();
            return;
        }

        String errorResponseAsString = mapper.writeValueAsString(
                new ErrorResponse(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "Unknown error with token " + tokenStatus, tokenStatus.name()));
        outputStream.print(errorResponseAsString);
        response.flushBuffer();
    }

    @Override
    public void permissionsAbort(List<String> tokenRoles, List<String> tokenPermission, String[] authRoles, String[] authPermissions, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.resetBuffer();
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setHeader("Content-Type", "application/json");
        ServletOutputStream outputStream = response.getOutputStream();

        String errorResponseAsString = mapper.writeValueAsString(
                new ErrorResponse("Abort with permissions or roles", ResultCode.PE_C_ERROR_005));
        outputStream.print(errorResponseAsString);
        response.flushBuffer();
    }

    @Override
    public boolean shouldIntercept(Object claim, Authorization access, HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, SQLException, JsonProcessingException, ParseException {
        log.info("Authorization Handler - Intercept Only");
        if (!(claim instanceof JWTClaimsSet)) {
            return false;
        }

        JWTClaimsSet claimsSet = (JWTClaimsSet)claim;
        String tokenType = claimsSet.getStringClaim("name");

        if (!(tokenType.equals("refresh_token") || tokenType.equals("access_token"))) {
            return false;
        }

        boolean shouldIntercept = false;
        String subject = claimsSet.getSubject();
        String userType = claimsSet.getStringClaim("userType");

        if (userType.equals(UserType.SUPPLIER.forClaims())) {
            MDC.put("supplier_id", ""+claimsSet.getClaim("supplierId")+"");
            UserEntity supplierUserEntity = userRepository.findBySupplierId(Integer.parseInt(subject));
            shouldIntercept = supplierUserEntity != null;
        } else if (userType.equals(UserType.MERCHANT.forClaims())) {
            MDC.put("outlet_id", "" + claimsSet.getClaim("outletId") + "");
            UserEntity merchantUserEntity = userRepository.findByMerchantId(Integer.parseInt(subject));
            shouldIntercept = merchantUserEntity != null;
        }

        JSONArray roles = (JSONArray) claimsSet.getClaim("roles");
        MDC.put("is_finance_admin",
                (roles != null && roles.size() > 0 && roles.contains(ROLE_FINANCE_ADMIN) ? "1" : "0"));

        String username = ((String)claimsSet.getClaim("firstName") + " " +
                StringUtils.trimToEmpty((String)claimsSet.getClaim("lastName"))).trim();
        MDC.put("user_id", subject);
        MDC.put("user_name", username);
        MDC.put("user_type", userType);
        MDC.put("crumb", mapper.writeValueAsString(
                new HashMap<String, Object>(){{
                    put("name", username);
                    put("phone", claimsSet.getClaim("phone"));
                    put("id", Integer.parseInt(subject));
                    put("path", request.getRequestURI());
                    put("timestamp", System.currentTimeMillis());
                    put("action", request.getMethod().toLowerCase());
                }}
        ));
        MDC.put("auth", request.getHeader("Authorization"));

        return shouldIntercept;
    }

    @SneakyThrows
    @Override
    public boolean shouldInterceptAdmin(String authHeader, Authorization authorization, HttpServletRequest request,
                                        HttpServletResponse response) {
        log.info("Authorization Handler - Intercept Admin");
        UserEntity userEntity = null;
        if (StringUtils.isBlank(authHeader))
            return false;
        String[] parts = authHeader.split(" ");
        if (parts.length < 2) {
            return false;
        } else {
            String token = parts[1].trim();
            if (token.length() != Constants.USER_TOKEN_LENGTH) {
                return false;
            } else {
                userEntity = userRepository.findByToken(token);
                if (userEntity == null) {
                    return false;
                }
            }
        }

        int adminId = userEntity.getId();
        String userName = userEntity.getUserName();
        String email = userEntity.getEmail();
        String phoneNumber = userEntity.getPhoneNumber();
        Map<String, Object> crumbMap = new LinkedHashMap<>();
        crumbMap.put("user_id", adminId);
        crumbMap.put("user_name", userName);
        crumbMap.put("phone", phoneNumber);
        crumbMap.put("email", email);
        crumbMap.put("path", request.getRequestURI());
        crumbMap.put("action", request.getMethod());
        MDC.put("crumb", mapper.writeValueAsString(crumbMap));
        MDC.put("auth", request.getHeader("Authorization"));
        return true;
    }
}