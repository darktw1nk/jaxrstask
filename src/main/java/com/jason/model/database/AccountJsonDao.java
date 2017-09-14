package com.jason.model.database;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.jason.model.pojo.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class AccountJsonDao implements AccountDao {
    final static Logger logger = LoggerFactory.getLogger(AccountJsonDao.class);

    @Value("${database.file.name}")
    String databaseFileName;

    @Override
    public List<Account> getAllAccounts() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Account> accounts = new ArrayList<>();
        try {
            accounts = objectMapper.readValue(ResourceUtils.getFile("classpath:" + databaseFileName), new TypeReference<List<Account>>() {
            });
        } catch (JsonMappingException ex) {
            if (!ex.getMessage().contains("No content")) {
                logger.error("", ex);
            }
        } catch (IOException e) {
            logger.error("", e);
        }
        return accounts;
    }

    @Override
    public Optional<Account> getById(Long id) {
        return getAllAccounts().stream().filter(account -> account.getAccountId().equals(id)).findFirst();
    }

    @Override
    public Optional<Account> saveAccount(Account account) {

        if (account != null) {
            List<Account> accounts = getAllAccounts();
            if (account.getAccountId() == null) {
                account.setAccountId(getCurrentId());
                accounts.add(account);
            } else {
                Account savedAccount = accounts.stream().filter(x -> x.getAccountId().equals(account.getAccountId())).findFirst().orElse(null);
                if (savedAccount == null) {
                    accounts.add(account);
                } else {
                    accounts.set(accounts.indexOf(savedAccount), account);
                }
            }

            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
            try {
                writer.writeValue(ResourceUtils.getFile("classpath:" + databaseFileName), accounts);
            } catch (IOException e) {
                logger.error("", e);
                return Optional.empty();
            }
            return Optional.of(account);
        } else
            return Optional.empty();
    }

    private Long getCurrentId() {
        AtomicLong currentId = null;
        List<Account> accounts = getAllAccounts();
        if (accounts.size() == 0) currentId = new AtomicLong(0);
        else {
            currentId = new AtomicLong(accounts.stream().max(Comparator.comparingLong(Account::getAccountId)).get().getAccountId());
        }
        return currentId.incrementAndGet();
    }
}
