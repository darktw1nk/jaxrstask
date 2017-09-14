package com.jason.model.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.jason.model.pojo.Account;
import com.jason.model.pojo.AccountBuilder;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;

public class AccountDeserializer extends StdDeserializer<Account> {

    public AccountDeserializer() {
        this(null);
    }

    public AccountDeserializer(Class<Account> t) {
        super(t);
    }

    @Override
    public Account deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        Long id = node.get("accountId") == null || node.get("accountId").isNull() ? null : (node.get("accountId")).longValue();
        String customerName = node.get("customerName") == null || node.get("customerName").isNull() ? null : node.get("customerName").asText();
        String currency = node.get("currency") == null || node.get("currency").isNull() ? null : node.get("currency").asText();
        BigDecimal amount = null;
        String amountText = (node.get("amount") == null || node.get("amount").isNull()) ? null : node.get("amount").asText();
        if (!StringUtils.isEmpty(amountText))
            amount = new BigDecimal(amountText);

        AccountBuilder builder = new AccountBuilder();
        return builder.setAccountId(id)
                .setCustomerName(customerName)
                .setCurrency(currency)
                .setAmount(amount)
                .createAccount();
    }

}
