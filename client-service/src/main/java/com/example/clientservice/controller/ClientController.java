package com.example.clientservice.controller;

import com.example.clientservice.dao.AccountDao;
import com.example.clientservice.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class ClientController {

    @Autowired
    AccountDao dao;

    public ClientController(AccountDao dao) {
        this.dao = dao;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Account addFunds(@RequestBody Account account){
        return dao.create(account);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account getAccount(@PathVariable int id){
        return dao.getOne(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAccout(@RequestBody Account account, @PathVariable int id) throws Exception{
        if(account.getId() ==  0){
            account.setId(id);
            if(id != account.getId()){
                throw  new IllegalArgumentException("Id on path must match id in account");
            }
            dao.updateAccount(account);
        }
    }


}
