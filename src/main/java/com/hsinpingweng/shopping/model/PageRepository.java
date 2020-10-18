package com.hsinpingweng.shopping.model;

import com.hsinpingweng.shopping.model.data.Page;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PageRepository extends JpaRepository<Page, Integer> {
    
    // check is slug entity exist
    // spring data jpa query creation from method name
    Page findBySlug(String slug);

}
