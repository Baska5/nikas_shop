package com.exadel.nikas_shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    private Long id;
    @NotBlank(message = "can't be blank")
    private String name;
    @NotBlank(message = "can't be blank")
    private String desc;
    private LocalDateTime modified;
    private LocalDateTime created;
}
