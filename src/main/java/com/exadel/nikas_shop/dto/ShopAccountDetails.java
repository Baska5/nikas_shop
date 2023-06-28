package com.exadel.nikas_shop.dto;

import com.exadel.nikas_shop.entity.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
@NoArgsConstructor
public class ShopAccountDetails implements UserDetails {

    private Account account;
    private String username;
    private String password;
    private List<GrantedAuthority> authorities = new ArrayList<>();

    public ShopAccountDetails(Account account) {
        this.account = account;
        this.username = account.getUsername();
        this.password = account.getPassword();
        this.authorities = account.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Account getAccount() {
        return this.account;
    }
}
