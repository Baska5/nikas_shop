package com.exadel.nikas_shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataResponseDto<T>{
    private Integer size;
    private List<T> data;
}
