package com.exadel.nikas_shop.repository;

import com.exadel.nikas_shop.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByAccountId(Long accountId);
}
