package com.exadel.nikas_shop.mapper;

import com.exadel.nikas_shop.dto.CartDto;
import com.exadel.nikas_shop.dto.DataResponseDto;
import com.exadel.nikas_shop.dto.OrderDto;
import com.exadel.nikas_shop.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(uses = {AccountMapper.class})
public interface OrderMapper {

    OrderDto mapOrderToResponse(Order order);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "status", ignore = true)
    Order mapCreateToOrder(OrderDto orderDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "accountId", ignore = true)
    void mapUpdateToOrder(@MappingTarget Order order, OrderDto orderDto);

    @Mapping(target = "data", source = "list")
    @Mapping(target = "size", source = "size")
    DataResponseDto<OrderDto> mapOrdersToResponse(Integer size, List<OrderDto> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "created", ignore = true)
    OrderDto mapCartToOrder(CartDto cartDto);
}
