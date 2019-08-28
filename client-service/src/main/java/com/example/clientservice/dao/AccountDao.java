package com.example.clientservice.dao;

import com.example.clientservice.model.Account;

import java.util.List;

public interface AccountDao {
    // create
    Account create(Account account);

    // get
    Account getOne(int id);

    // update
    void updateAccount(Account account);

    // get all
    List<Account> getAll();

    // delete
    void delete(int id);
}
