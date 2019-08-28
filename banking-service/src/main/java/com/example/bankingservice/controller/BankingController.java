package com.example.bankingservice.controller;

import com.example.bankingservice.model.Account;
import com.example.bankingservice.service.BankingServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class BankingController {

    @Autowired
    BankingServiceLayer service;

    public BankingController(BankingServiceLayer service) {
        this.service = service;
    }

    @PutMapping(value = "/account/addfunds/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addFunds(@RequestBody Account account, @PathVariable int id) throws Exception{
        if(account.getId() ==  0){
            account.setId(id);
            if(id != account.getId()){
                throw  new IllegalArgumentException("Id on path must match id in account");
            }
            service.updateAccount(account, id);
        }

    }

    @GetMapping(value = "/account/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account getAccountById(@PathVariable int id){
        return service.findAccount(id);
    }

    @PostMapping(value = "/account")
    @ResponseStatus(HttpStatus.CREATED)
    public Account addAccount(@RequestBody Account account){
        return service.addAccount(account);
    }
}
