package com.exadel.nikas_shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private Long id;
    @NotBlank(message = "can't be blank")
    private String status;
    @NotNull(message = "Can't be null")
    private Long accountId;
    private LocalDateTime modified;
    private LocalDateTime created;
}
