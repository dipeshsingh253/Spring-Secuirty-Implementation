package com.customsecurity.SpringSecurityImplementation.repository;

import com.customsecurity.SpringSecurityImplementation.model.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    User findByUsername(String name);


}
