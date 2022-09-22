package com.tinvio.accounting.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinvio.accounting.config.MDCFilterConfiguration;
import com.tinvio.accounting.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Slf4j
public class MDCFilter extends OncePerRequestFilter {

    private final String mdcKey;
    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private UserService userService;

    public MDCFilter() {
        this.mdcKey = MDCFilterConfiguration.DEFAULT_MDC_TOKEN_KEY;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        try {
            final String mdcToken = extractTraceId(httpServletRequest);
            MDC.put(mdcKey, mdcToken);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            MDC.remove(mdcKey);
        }
    }

    private String extractTraceId(final HttpServletRequest request) {
        String token = request.getHeader("x-tinvio-trace-id");

        if (org.apache.commons.lang3.StringUtils.isBlank(token))
            token = UUID.randomUUID().toString();
        return token;
    }

    @Override
    protected boolean isAsyncDispatch(HttpServletRequest request) {
        return false;
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return false;
    }
}
