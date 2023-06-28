package com.exadel.nikas_shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
    private Long id;
    @Min(value = 1, message = "Must be a positive number")
    private Integer quantity;
    @NotNull(message = "Can't be null")
    private Long orderId;
    @NotNull(message = "Can't be null")
    private Long productId;
    private LocalDateTime modified;
    private LocalDateTime created;
}
