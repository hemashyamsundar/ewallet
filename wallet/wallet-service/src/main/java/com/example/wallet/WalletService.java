package com.example.wallet;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.CommonConstants;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WalletRepository walletRepository;

    @KafkaListener(topics=CommonConstants.USER_CREATED_TOPIC,groupId = "group_user")
    public void createWallet(String msg){
        JSONObject data = objectMapper.convertValue(msg, JSONObject.class);
        Long userId = (Long) data.get(CommonConstants.USER_CREATED_TOPIC_USERID);
        String phoneNumber = (String) data.get(CommonConstants.USER_CREATED_TOPIC_PHONE_NUMBER);
        String userIdentifier = (String) data.get(CommonConstants.USER_CREATED_TOPIC_IDENTIFIER_KEY);
        String identifierValue = (String) data.get(CommonConstants.USER_CREATED_TOPIC_IDENTIFIER_VALUE);

        Wallet wallet = Wallet.builder()
                .userId(userId)
                .phoneNumber(phoneNumber)
                .userIdentifier(UserIdentifier.valueOf(userIdentifier))
                .identifierValue(identifierValue)
                .balance(10.0)
                .build();
        walletRepository.save(wallet);
    }
}
