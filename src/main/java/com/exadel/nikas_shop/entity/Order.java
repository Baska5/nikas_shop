package com.exadel.nikas_shop.entity;

import com.exadel.nikas_shop.enums.CartState;
import com.exadel.nikas_shop.enums.OrderState;
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
@Table(name = "order", schema = "public")

public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "account_id", nullable = false, updatable = false)
    private Long accountId;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderState status;

    @LastModifiedDate
    @Column(name = "order_modified")
    @UpdateTimestamp
    private LocalDateTime modified;

    @CreatedDate
    @Column(name = "order_created", updatable = false)
    @CreationTimestamp
    private LocalDateTime created;

}