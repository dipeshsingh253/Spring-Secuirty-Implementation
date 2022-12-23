package com.customsecurity.SpringSecurityImplementation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String username;


    private String password;
    private String roles;

    private String authorities;


    public List<String> getRolesList() {

        if (this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }

        return new ArrayList<>();

    }

    public List<String> getAuthoritiesList() {

        if (this.authorities.length() > 0) {
            return Arrays.asList(this.authorities.split(","));
        }

        return new ArrayList<>();

    }


}
