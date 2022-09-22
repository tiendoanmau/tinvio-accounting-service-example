package com.tinvio.accounting.repository;

import com.tinvio.accounting.model.entity.UserEntity;
import com.tinvio.accounting.repository.template.SqlUserTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Slf4j
@Repository
public class UserRepository {

    private final NamedParameterJdbcTemplate namedParameterROJdbcTemplate;

    public UserRepository(@Qualifier(value = "read_db_named_template") NamedParameterJdbcTemplate namedParameterROJdbcTemplate) {
        this.namedParameterROJdbcTemplate = namedParameterROJdbcTemplate;
    }


    public UserEntity findBySupplierId(Integer supplierId) {
        try {
            return namedParameterROJdbcTemplate.queryForObject(SqlUserTemplate.GET_SUPPLIER_USER_INFORMATION
                    , new HashMap<>() {
                        {
                            put("supplieruserid", supplierId);
                        }
                    }
                    , new BeanPropertyRowMapper<>(UserEntity.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public UserEntity findByMerchantId(Integer merchantId) {
        try {
            return namedParameterROJdbcTemplate.queryForObject(SqlUserTemplate.GET_MERCHANT_USER_INFORMATION
                    , new HashMap<>() {
                        {
                            put("merchantuserid", merchantId);
                        }
                    }
                    , new BeanPropertyRowMapper<>(UserEntity.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public UserEntity findByToken(String token) {
        try {
            return namedParameterROJdbcTemplate.queryForObject(SqlUserTemplate.GET_USER_INFORMATION
                    , new HashMap<>() {
                        {
                            put("authtoken", token);
                            put("isactive", true);
                            put("isdeleted", false);
                        }
                    }
                    , new BeanPropertyRowMapper<>(UserEntity.class));
        } catch(EmptyResultDataAccessException e) {
            return null;
        }
    }
}
