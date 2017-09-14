package com.jason.controller;

import com.jason.model.database.AccountJsonDao;
import com.jason.model.json.patch.Command;
import com.jason.model.json.patch.Patch;
import com.jason.model.pojo.Account;
import com.jason.model.service.AccountService;
import com.jason.model.util.PATCH;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Path("/v1/accounts")
public class AccountRestService {
    final static Logger logger = LoggerFactory.getLogger(AccountRestService.class);

    @Autowired
    AccountService accountService;

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getAccount(@PathParam("id") Long accountId) {

        Optional<Account> account = accountService.getById(accountId);

        return returnEntityOrErrorMessage(account, "Cant retrieve accounts");
    }

    @GET
    @Path("/account")
    @Produces("application/json")
    public Response getAccounts() {

        List<Account> accounts = accountService.getAllAccounts();

        return Response.status(200).entity(accounts).build();
    }

    @POST
    @Path("/account")
    @Consumes({"application/json"})
    @Produces("application/json")
    public Response createAccount(Account account) {

        Optional<Account> savedAccount = accountService.createAccount(account);

        return returnEntityOrErrorMessage(savedAccount, "cant create new account");
    }

    //so for now only json-patch+json implemented.
    @PATCH
    @Path("/{id}")
    @Consumes({"application/json-patch+json"})
    @Produces("application/json")
    public Response editAccount(@PathParam("id") Long accountId,Command[] commands) {

        Optional<Account> updatedAccount = accountService.updateAccount(accountId,commands);

        return returnEntityOrErrorMessage(updatedAccount, "cant update account");
    }

    private Response returnEntityOrErrorMessage(Optional<? extends Object> entity, String errorMessage) {
        Response response = null;
        if (entity.isPresent()) {
            response = Response.status(200).entity(entity.get()).build();
        } else {
            response = Response.status(500).entity(Arrays.asList(errorMessage)).build();
        }

        return response;
    }
}
