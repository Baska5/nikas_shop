package com.exadel.nikas_shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    @NotBlank(message = "can't be blank")
    private String firstName;
    @NotBlank(message = "can't be blank")
    private String lastName;
    @Email(message = "is not valid")
    private String email;
    private LocalDateTime modified;
    private LocalDateTime created;
}
