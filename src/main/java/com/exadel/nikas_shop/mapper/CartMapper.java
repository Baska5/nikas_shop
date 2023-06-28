package com.exadel.nikas_shop.mapper;

import com.exadel.nikas_shop.dto.CartDto;
import com.exadel.nikas_shop.dto.DataResponseDto;
import com.exadel.nikas_shop.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(uses = {AccountMapper.class})
public interface CartMapper {

    default Long cartToId(Cart cart) {
        return cart.getId();
    }

    default Cart idToCart(Long id) {
        Cart cart = new Cart();
        cart.setId(id);
        return cart;
    }

    CartDto mapCartToResponse(Cart cart);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "status", ignore = true)
    Cart mapCreateToCart(CartDto cartDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "accountId", ignore = true)
    void mapUpdateToCart(@MappingTarget Cart cart, CartDto cartDto);

    @Mapping(target = "data", source = "list")
    @Mapping(target = "size", source = "size")
    DataResponseDto<CartDto> mapCartsToResponse(Integer size, List<CartDto> list);

}
