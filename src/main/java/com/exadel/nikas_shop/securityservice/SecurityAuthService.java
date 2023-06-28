package com.exadel.nikas_shop.securityservice;

import com.exadel.nikas_shop.dto.CartDto;
import com.exadel.nikas_shop.dto.OrderDto;
import com.exadel.nikas_shop.dto.ShopAccountDetails;
import com.exadel.nikas_shop.service.CartService;
import com.exadel.nikas_shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

@Component
public class SecurityAuthService {

    private static final String ROLE_PREFIX = "ROLE_";
    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    public boolean hasAccess(String role) {
        return hasAnyAccess(role);
    }

    public boolean hasAnyAccess(String... roles) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.isAuthenticated())
            return false;
        ShopAccountDetails accountDetails = (ShopAccountDetails) auth.getPrincipal();
        Set<String> authorities = AuthorityUtils.authorityListToSet(accountDetails.getAuthorities());
        return Arrays.stream(roles).map(this::getRoleWithPrefix).anyMatch(authorities::contains);
    }

    public boolean isAccountOwner(Long accountId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.isAuthenticated())
            return false;
        ShopAccountDetails accountDetails = (ShopAccountDetails) auth.getPrincipal();
        if (isAdmin(accountDetails))
            return true;
        return accountId.equals(accountDetails.getAccount().getId());
    }

    public boolean isUserOwner(Long userId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.isAuthenticated())
            return false;
        ShopAccountDetails accountDetails = (ShopAccountDetails) auth.getPrincipal();
        if (isAdmin(accountDetails))
            return true;
        return userId.equals(accountDetails.getAccount().getUserId());
    }

    public boolean isCartOwner(Long cartId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.isAuthenticated())
            return false;
        ShopAccountDetails accountDetails = (ShopAccountDetails) auth.getPrincipal();
        if (isAdmin(accountDetails))
            return true;
        ResponseEntity<CartDto> cart = cartService.getById(cartId);
        return Objects.requireNonNull(cart.getBody()).getAccountId().equals(accountDetails.getAccount().getId());
    }

    public boolean isOrderOwner(Long orderId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.isAuthenticated())
            return false;
        ShopAccountDetails accountDetails = (ShopAccountDetails) auth.getPrincipal();
        if (isAdmin(accountDetails))
            return true;
        ResponseEntity<OrderDto> order = orderService.getById(orderId);
        return Objects.requireNonNull(order.getBody()).getAccountId().equals(accountDetails.getAccount().getId());
    }

    private String getRoleWithPrefix(String role) {
        return ROLE_PREFIX + role.toUpperCase();
    }

    private boolean isAdmin(ShopAccountDetails account) {
        return account.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
    }
}
