package com.exadel.nikas_shop.repository;

import com.exadel.nikas_shop.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findById(Long accountId);

    Optional<Account> findByUsername(String username);

}
