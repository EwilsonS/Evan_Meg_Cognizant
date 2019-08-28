package com.example.clientservice.dao;

import com.example.clientservice.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AccountDaoJdbcTemplateImpl implements AccountDao {

    // init jdbc
    private JdbcTemplate jdbc;

    // prepared statements
    private static final String INSERT_SQL = "insert into account(balance) values(?)";
    private static final String SELECT_SQL = "select * from account where id=?";
    private static final String UPDATE_SQL = "update account set balance=? where id=?";
    private static final String SELECT_ALL_SQL = "select * from account";
    private  static final String DELETE_SQL = "delete from account where id=?";
    // constructor injection
    @Autowired
    public AccountDaoJdbcTemplateImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // implementation
    @Override
    @Transactional
    public Account create(Account account) {
        jdbc.update(
                INSERT_SQL,
                account.getBalance()
        );

        account.setId(jdbc.queryForObject("select LAST_INSERT_ID()", Integer.class));

        return account;
    }

    @Override
    public Account getOne(int id) {
        try {
            return jdbc.queryForObject(SELECT_SQL, this::mapRowToAccount, id);
        } catch (EmptyResultDataAccessException e) {
            // No account matching the given id
            return null;
        }
    }

    @Override
    public void updateAccount(Account account) {
        jdbc.update(
                UPDATE_SQL,
                account.getBalance(),
                account.getId()
                );

    }

    @Override
    public List<Account> getAll() {
        return jdbc.query(SELECT_ALL_SQL, this::mapRowToAccount);
    }

    @Override
    public void delete(int id) {
        jdbc.update(DELETE_SQL, id);
    }

    // mapper
    private Account mapRowToAccount(ResultSet rs, int rowNum) throws SQLException {
        Account account = new Account();
        account.setId(rs.getInt("id"));
        account.setBalance(rs.getInt("balance"));
        return account;
    }
}
