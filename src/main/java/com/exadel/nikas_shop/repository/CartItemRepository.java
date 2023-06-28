package com.exadel.nikas_shop.repository;

import com.exadel.nikas_shop.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query(value = "select ci from CartItem ci where ci.cartId = :cartId")
    List<CartItem> findByCartId(Long cartId);
}
