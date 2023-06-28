package com.exadel.nikas_shop.service;

import com.exadel.nikas_shop.dto.AccountDto;
import com.exadel.nikas_shop.dto.DataResponseDto;
import com.exadel.nikas_shop.entity.Account;
import com.exadel.nikas_shop.entity.Role;
import com.exadel.nikas_shop.exception.exceptions.EntityNotFoundException;
import com.exadel.nikas_shop.mapper.AccountMapper;
import com.exadel.nikas_shop.repository.AccountRepository;
import com.exadel.nikas_shop.repository.RoleRepository;
import com.exadel.nikas_shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

@Service
public class AccountServiceImpl implements AccountService {
    private final ExampleMatcher usernameMatcher = ExampleMatcher.matching()
            .withIgnoreCase("id")
            .withMatcher("username", ignoreCase());
    @Autowired
    private AccountRepository repository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AccountMapper mapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<DataResponseDto<AccountDto>> getList() {
        List<AccountDto> accounts = repository.findAll().stream()
                .map(account -> mapper.mapAccountToResponse(account))
                .collect(Collectors.toList());
        return ResponseEntity.ok
                (mapper.mapAccountsToResponse
                        (accounts.size(), accounts));
    }

    @Override
    public ResponseEntity<AccountDto> getById(Long accountId) {
        Optional<Account> account = repository.findById(accountId);

        account.orElseThrow(() -> new EntityNotFoundException(String.format("Account with ID %d does not exist.", accountId)));

        return ResponseEntity.ok(mapper.mapAccountToResponse(account.get()));
    }

    @Override
    public ResponseEntity<AccountDto> create(AccountDto accountDto) {
        Account account = mapper.mapCreateToAccount(accountDto);
        List<Role> roles = roleRepository.findByListOfIds(accountDto.getRoleIds());
        if (roles.isEmpty())
            roles.add(roleRepository.findByRoleName("ROLE_USER"));
        account.setRoles(roles);
        encodePassword(account);
        AccountDto accountResponseDto = mapper.mapAccountToResponse(repository.save(account));
        return new ResponseEntity<>(accountResponseDto, HttpStatus.CREATED);
    }

    @Override
    public void delete(Long accountId) {
        Optional<Account> account = repository.findById(accountId);

        account.orElseThrow(() -> new EntityNotFoundException(String.format("Account with ID %d does not exist.", accountId)));

        repository.deleteById(accountId);
    }

    @Override
    public ResponseEntity<AccountDto> update(Long accountId, AccountDto accountDto) {
        Account account = repository.findById(accountId).orElseThrow(() -> new EntityNotFoundException(String.format("Account with ID %d does not exist.", accountId)));

        mapper.mapUpdateToAccount(account, accountDto);
        encodePassword(account);
        AccountDto accountResponseDto = mapper.mapAccountToResponse(repository.save(account));
        return ResponseEntity.ok(accountResponseDto);
    }

    private void encodePassword(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
    }
}
