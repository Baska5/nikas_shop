package com.exadel.nikas_shop.service;

import com.exadel.nikas_shop.dto.ShopAccountDetails;
import com.exadel.nikas_shop.entity.Account;
import com.exadel.nikas_shop.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShopAccountDetailService implements UserDetailsService {

    @Autowired
    AccountRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = repository.findByUsername(username);

        account.orElseThrow(() -> new UsernameNotFoundException(String.format("User with username: %s doesn't exist.", username)));

        return account.map(ShopAccountDetails::new).get();
    }
}
