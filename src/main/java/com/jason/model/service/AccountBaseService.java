package com.jason.model.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jason.model.database.AccountDao;
import com.jason.model.json.patch.Command;
import com.jason.model.json.patch.Patch;
import com.jason.model.json.patch.PatchBuilder;
import com.jason.model.json.patch.PatchException;
import com.jason.model.pojo.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class AccountBaseService implements AccountService {
    final static Logger logger = LoggerFactory.getLogger(AccountBaseService.class);

    @Autowired
    AccountDao accountDao;

    @Override
    public Optional<Account> getById(Long id) {
        return accountDao.getById(id);
    }

    @Override
    public Optional<Account> createAccount(Account account) {
        //if account with this id already exists - do nothing, as this method only for creation
        if (account.getAccountId() != null && accountDao.getById(account.getAccountId()).isPresent()) {
            return Optional.empty();
        } else {
            return accountDao.saveAccount(account);
        }
    }

    @Override
    public Optional<Account> updateAccount(Long id, Command[] commands) {
        //if account with this id not exists - do nothing, as this method only for updating existing accounts
        if (id == null || !accountDao.getById(id).isPresent()) {
            return Optional.empty();
        } else {
            Account account = getById(id).get();
            Patch patch = new PatchBuilder().setDomain(account).setCommands(Arrays.asList(commands)).createPatch();
            try {
                JsonNode editedObject = patch.apply();
                ObjectMapper mapper = new ObjectMapper();
                Account editedAccount = mapper.treeToValue(editedObject,Account.class);
                return accountDao.saveAccount(editedAccount);
            } catch (Exception e) {
                logger.error("", e);
                return Optional.empty();
            }
        }
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountDao.getAllAccounts();
    }


}
