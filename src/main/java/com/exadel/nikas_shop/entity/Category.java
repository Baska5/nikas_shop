package com.exadel.nikas_shop.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category", schema = "public")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "category_name", nullable = false)
    private String name;

    @Column(name = "category_desc", nullable = false)
    private String desc;

    @LastModifiedDate
    @Column(name = "category_modified")
    @UpdateTimestamp
    private LocalDateTime modified;

    @CreatedDate
    @Column(name = "category_created", updatable = false)
    @CreationTimestamp
    private LocalDateTime created;

}
