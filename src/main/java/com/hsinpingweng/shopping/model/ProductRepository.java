package com.hsinpingweng.shopping.model;

import com.hsinpingweng.shopping.model.data.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}