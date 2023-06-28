package com.exadel.nikas_shop.mapper;

import com.exadel.nikas_shop.dto.AccountDto;
import com.exadel.nikas_shop.dto.DataResponseDto;
import com.exadel.nikas_shop.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(uses = {UserMapper.class, RoleMapper.class})
public interface AccountMapper {

    default Long accountToId(Account account) {
        return account.getId();
    }

    default Account idToAccount(Long id) {
        Account account = new Account();
        account.setId(id);
        return account;
    }

    @Mapping(source = "roles", target = "roleIds")
    AccountDto mapAccountToResponse(Account account);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(source = "roleIds", target = "roles")
    Account mapCreateToAccount(AccountDto accountDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "userId", ignore = true)
    void mapUpdateToAccount(@MappingTarget Account account, AccountDto accountDto);

    @Mapping(target = "data", source = "list")
    @Mapping(target = "size", source = "size")
    DataResponseDto<AccountDto> mapAccountsToResponse(Integer size, List<AccountDto> list);

}
