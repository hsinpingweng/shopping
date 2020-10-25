package com.hsinpingweng.shopping.model;

import com.hsinpingweng.shopping.model.data.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category findByName(String name);
}
