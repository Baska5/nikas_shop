package com.exadel.nikas_shop.mapper;

import com.exadel.nikas_shop.dto.CartItemDto;
import com.exadel.nikas_shop.dto.DataResponseDto;
import com.exadel.nikas_shop.dto.OrderItemDto;
import com.exadel.nikas_shop.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(uses = {OrderMapper.class, ProductMapper.class})
public interface OrderItemMapper {

    OrderItemDto mapOrderItemToResponse(OrderItem orderItem);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "created", ignore = true)
    OrderItem mapCreateToOrderItem(OrderItemDto orderItemDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "orderId", ignore = true)
    void mapUpdateToOrderItem(@MappingTarget OrderItem orderItem, OrderItemDto orderItemDto);

    @Mapping(target = "data", source = "list")
    @Mapping(target = "size", source = "size")
    DataResponseDto<OrderItemDto> mapOrderItemsToResponse(Integer size, List<OrderItemDto> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "created", ignore = true)
    OrderItemDto mapCartItemToOrderItem(CartItemDto cartItemDto);
}
