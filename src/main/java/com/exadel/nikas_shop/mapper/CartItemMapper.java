package com.exadel.nikas_shop.mapper;

import com.exadel.nikas_shop.dto.CartItemDto;
import com.exadel.nikas_shop.dto.DataResponseDto;
import com.exadel.nikas_shop.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(uses = {CartMapper.class, ProductMapper.class})
public interface CartItemMapper {

    default Long cartItemToId(CartItem cartItem) {
        return cartItem.getId();
    }

    CartItemDto mapCartItemToResponse(CartItem cartItem);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "created", ignore = true)
    CartItem mapCreateToCartItem(CartItemDto cartItemDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "cartId", ignore = true)
    void mapUpdateToCartItem(@MappingTarget CartItem cartItem, CartItemDto cartItemDto);

    @Mapping(target = "data", source = "list")
    @Mapping(target = "size", source = "size")
    DataResponseDto<CartItemDto> mapCartItemsToResponse(Integer size, List<CartItemDto> list);

}
