package com.exadel.nikas_shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private Long id;
    @NotBlank(message = "Can't be blank")
    private String username;
    @NotBlank(message = "Can't be blank")
    @Size(min = 8, message = "Must have minimum of 8 characters")
    private String password;
    @NotNull(message = "Can't be null")
    private Boolean isActive;
    @NotNull(message = "Can't be null")
    private Long userId;
    @NotEmpty(message = "Can't be empty")
    private List<Long> roleIds;
    private LocalDateTime modified;
    private LocalDateTime created;
}
