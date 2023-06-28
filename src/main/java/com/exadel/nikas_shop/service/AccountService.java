package com.exadel.nikas_shop.service;

import com.exadel.nikas_shop.dto.AccountDto;
import com.exadel.nikas_shop.dto.DataResponseDto;
import org.springframework.http.ResponseEntity;


public interface AccountService {

    ResponseEntity<DataResponseDto<AccountDto>> getList();

    ResponseEntity<AccountDto> getById(Long accountId);

    ResponseEntity<AccountDto> create(AccountDto accountDto);

    void delete(Long accountId);

    ResponseEntity<AccountDto> update(Long accountId, AccountDto accountDto);

}
