package com.hsinpingweng.shopping.model;

import com.hsinpingweng.shopping.model.data.Admin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findByUsername(String username);
}
