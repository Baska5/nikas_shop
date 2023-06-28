package com.exadel.nikas_shop.controller;

import com.exadel.nikas_shop.dto.AccountDto;
import com.exadel.nikas_shop.dto.DataResponseDto;
import com.exadel.nikas_shop.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("account")
@RequiredArgsConstructor
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/list")
    @PreAuthorize("@securityAuthService.hasAccess('admin')")
    ResponseEntity<DataResponseDto<AccountDto>> getList() {
        return accountService.getList();
    }

    @GetMapping("/{accountId}")
    @PreAuthorize("@securityAuthService.isAccountOwner(#accountId)")
    ResponseEntity<AccountDto> getAccountById(@PathVariable("accountId") Long accountId) {
        return accountService.getById(accountId);
    }

    @PostMapping()
    @PreAuthorize("permitAll()")
    ResponseEntity<AccountDto> createAccount(@Valid @RequestBody AccountDto accountDto) {
        return accountService.create(accountDto);
    }

    @DeleteMapping("/{accountId}")
    @PreAuthorize("@securityAuthService.isAccountOwner(#accountId)")
    void deleteById(@PathVariable("accountId") Long accountId) {
        accountService.delete(accountId);
    }

    @PutMapping("/{accountId}")
    @PreAuthorize("@securityAuthService.isAccountOwner(#accountId)")
    ResponseEntity<AccountDto> updateAccount(@PathVariable("accountId") Long accountId,
                                                     @Valid @RequestBody AccountDto accountDto) {
        return accountService.update(accountId, accountDto);
    }
}
