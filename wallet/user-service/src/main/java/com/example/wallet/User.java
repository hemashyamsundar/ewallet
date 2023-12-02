package com.example.wallet;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable, UserDetails {
    //serializable bcos to persist in cache.  UserDetails for authentication

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank
    private String name;

    @Column(unique=true)
    private String phonenumber; // will be acting as a username in spring security

    @Column(unique=true)
    private String email;
    private String password;

    @Column(unique=true)
    private String identifierValue;

    @Enumerated(value=EnumType.STRING)
    private UserIdentifier userIdentifier;

    private String country;
    private String dob;
    @CreationTimestamp
    private Date createdOn;
    @UpdateTimestamp
    private Date updatedOn;

    private String authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       String allAuthorities[] = this.authorities.split(UserConstants.AUTHORITIES_DELIMITER);
        return Arrays.stream(allAuthorities)
                .map(auth-> new SimpleGrantedAuthority(auth))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.phonenumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
