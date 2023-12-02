package com.example.wallet;


import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String phonenumber; // will be acting as a username in spring security
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String identifierValue;
    @NotNull
    private UserIdentifier userIdentifier;
    private String country;
    private String dob;

    public User to(){
        return User.builder()
                .name(this.name)
                .phonenumber(this.phonenumber)
                .email(this.email)
                .password(this.password)
                .country(this.country)
                .dob(this.dob)
                .userIdentifier(this.userIdentifier)
                .identifierValue(this.identifierValue)
                .build();
    }
}
