package com.hsinpingweng.shopping.model;

import com.hsinpingweng.shopping.model.data.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
