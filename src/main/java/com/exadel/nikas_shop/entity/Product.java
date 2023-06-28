package com.exadel.nikas_shop.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product", schema = "public")

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "category_id", nullable = false, updatable = false)
    private Long categoryId;

    @Column(name = "product_name", nullable = false, unique = true)
    private String name;

    @Column(name = "product_desc", nullable = false)
    private String desc;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @LastModifiedDate
    @Column(name = "product_modified")
    @UpdateTimestamp
    private LocalDateTime modified;

    @CreatedDate
    @Column(name = "product_created", updatable = false)
    @CreationTimestamp
    private LocalDateTime created;

}
