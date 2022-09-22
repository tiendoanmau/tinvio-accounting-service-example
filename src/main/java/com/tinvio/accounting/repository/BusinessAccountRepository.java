package com.tinvio.accounting.repository;

import com.tinvio.accounting.model.entity.FinanceBusinessAccount;
import com.tinvio.accounting.repository.template.SqlBusinessAccountTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Slf4j
@Repository
public class BusinessAccountRepository {

    private final JdbcTemplate roJDBCTemplate;

    public BusinessAccountRepository(@Qualifier(value = "read_db") JdbcTemplate roJDBCTemplate) {
        this.roJDBCTemplate = roJDBCTemplate;
    }

    public FinanceBusinessAccount findById(Integer id) {
        try {
            return roJDBCTemplate.queryForObject(
                    SqlBusinessAccountTemplate.FIND_BY_ID
                    , new Object[]{id}
                    , new int[]{Types.INTEGER}
                    , new BeanPropertyRowMapper<>(FinanceBusinessAccount.class));
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }
}
