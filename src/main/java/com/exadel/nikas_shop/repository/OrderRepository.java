package com.exadel.nikas_shop.repository;

import com.exadel.nikas_shop.entity.Cart;
import com.exadel.nikas_shop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
