package com.jason.model.database;

import com.jason.model.pojo.Account;

import java.util.List;
import java.util.Optional;

public interface AccountDao {

    List<Account> getAllAccounts();

    Optional<Account> getById(Long id);

    Optional<Account> saveAccount(Account account);
}
