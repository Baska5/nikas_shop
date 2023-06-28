package com.exadel.nikas_shop.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart_item", schema = "public")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cart_id", nullable = false, updatable = false)
    private Long cartId;

    @Column(name = "product_id", nullable = false, updatable = false)
    private Long productId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @LastModifiedDate
    @Column(name = "cart_item_modified")
    @UpdateTimestamp
    private LocalDateTime modified;

    @CreatedDate
    @Column(name = "cart_item_created", updatable = false)
    @CreationTimestamp
    private LocalDateTime created;

}
