package com.jason.model.service;

import com.jason.model.json.patch.Command;
import com.jason.model.pojo.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    List<Account> getAllAccounts();

    Optional<Account> getById(Long id);

    Optional<Account> createAccount(Account account);

    Optional<Account> updateAccount(Long id, Command[] commands);

}
