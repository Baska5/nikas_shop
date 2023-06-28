package com.exadel.nikas_shop.repository;

import com.exadel.nikas_shop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
