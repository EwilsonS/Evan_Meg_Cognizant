package com.example.bankingservice.service;

import com.example.bankingservice.exception.NotFoundException;
import com.example.bankingservice.model.Account;
import com.example.bankingservice.util.feign.AccountClient;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class BankingServiceLayerTest {

    @InjectMocks
    BankingServiceLayer service;

    @Mock
    AccountClient client;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        setUpAccountClientMock();
    }

    private void setUpAccountClientMock(){
        Account account = new Account(1,500);
        Account account2 = new Account(500);

        Account account3 = new Account(3,500);

        doReturn(account).when(client).createAccount(account2);
        doReturn(account).when(client).getAccount(1);
        doReturn(null).when(client).getAccount(999);

    }
    @Test
    public void shouldAddAndFindAccount() {
        Account account = new Account(500);

        // add
        account = service.addAccount(account);

        // get
        Account fromService = service.findAccount(account.getId());
        assertEquals(account, fromService);

    }

    @Test
    public void updateAccount() {
        Account account = new Account(500);
        account = service.addAccount(account);

        account.setBalance(1000);
        service.updateAccount(account,account.getId());

        assertEquals(1000, service.findAccount(account.getId()).getBalance());
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowExceptionWhenAccountIsNull() throws Exception{
        int id = 999;
        service.findAccount(id);
    }

}