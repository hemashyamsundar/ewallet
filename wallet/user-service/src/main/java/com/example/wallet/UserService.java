package com.example.wallet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.CommonConstants;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    KafkaTemplate kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void create(UserCreateRequest userCreateRequest) throws JsonProcessingException {
        User user = userCreateRequest.to();
        user.setPassword(encryptPwd(user.getPassword()));
        user.setAuthorities(UserConstants.USER_AUTHORITY);
        userRepository.save(user);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId",user.getId());
        jsonObject.put("phoneNumber",user.getPhonenumber());
        jsonObject.put("identifier",user.getUserIdentifier());
        jsonObject.put("identifierValue", user.getIdentifierValue());

        //TODO publish the event post user creation which can  be consumed by wallet service.
        kafkaTemplate.send(CommonConstants.USER_CREATED_TOPIC,
                objectMapper.writeValueAsString(jsonObject));
    }

    private String encryptPwd(String rawPwd){
        return passwordEncoder.encode(rawPwd);
    }
}
