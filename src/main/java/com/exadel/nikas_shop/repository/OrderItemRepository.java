package com.exadel.nikas_shop.repository;

import com.exadel.nikas_shop.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
