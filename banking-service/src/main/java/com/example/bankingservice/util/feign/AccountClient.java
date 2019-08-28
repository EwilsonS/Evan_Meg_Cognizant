package com.example.bankingservice.util.feign;

import com.example.bankingservice.model.Account;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "client-service")
@RequestMapping("/accounts")
public interface AccountClient {

    @PostMapping
    Account createAccount(@RequestBody Account account);

    @GetMapping("/{id}")
    Account getAccount(@PathVariable int id);

    @PutMapping("/{id}")
    void updateAccount(@RequestBody Account account, @PathVariable int id);

}
