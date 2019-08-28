package com.example.clientservice.dao;

import com.example.clientservice.model.Account;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class AccountDaoJdbcTemplateImplTest {

    @Autowired
    AccountDao dao;

    @Before
    public void setUp() throws Exception {
        dao.getAll().stream().forEach(account -> dao.delete(account.getId()));
    }

    @Test
    public void shouldCreateAndGetAccount() {
        Account account = new Account(100);
        // add
        account = dao.create(account);

        // get
        Account account1 = dao.getOne(account.getId());
        assertEquals(account, account1);
    }

//    @Test(expected = IllegalArgumentException.class)
//    public void shouldThrowIllegalExForBadRequest(){
//        Account account = new Account(100);
//        // add
//        account = dao.create(account);
//
//        // get
//        Account account1 = dao.getOne(55);
//        assertEquals(account, account1);
//
//    }

    @Test
    public void updateAccount() {
        Account account = new Account(100);
        // add
        account = dao.create(account);
        account.setBalance(500);
        dao.updateAccount(account);

        // get
        Account account1 = dao.getOne(account.getId());
        assertEquals(500, dao.getOne(account.getId()).getBalance());
    }

    @Test
    public void shouldDeleteAccount(){
        Account account = new Account(100);
        // add
        account = dao.create(account);
        dao.create(account);
        dao.create(account);
        dao.delete(account.getId());

        assertEquals(2, dao.getAll().size());


    }
}