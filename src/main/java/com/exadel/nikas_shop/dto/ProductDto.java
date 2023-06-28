package com.exadel.nikas_shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id;
    @NotBlank(message = "can't be blank")
    private String name;
    @NotBlank(message = "can't be blank")
    private String desc;
    @DecimalMin(value = "0.0", inclusive = false, message = "must be a positive number")
    private BigDecimal price;
    @NotNull(message = "Can't be null")
    private Long categoryId;
    private LocalDateTime modified;
    private LocalDateTime created;
}
