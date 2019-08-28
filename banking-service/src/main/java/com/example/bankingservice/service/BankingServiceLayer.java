package com.example.bankingservice.service;

import com.example.bankingservice.exception.NotFoundException;
import com.example.bankingservice.model.Account;
import com.example.bankingservice.util.feign.AccountClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BankingServiceLayer {

    private AccountClient client;

    @Autowired
    public BankingServiceLayer(AccountClient client) {
        this.client = client;
    }

    public Account addAccount(Account account){
        return client.createAccount(account);
    }

    public void updateAccount(Account account, int id){
        client.updateAccount(account, id);
    }

    public Account findAccount(int id) throws NotFoundException {
        if(client.getAccount(id) == null){
            throw new NotFoundException("There is no account with id "+ id);
        }
        return client.getAccount(id);
    }

}
