package com.hsinpingweng.shopping.model;

import java.util.List;

import com.hsinpingweng.shopping.model.data.Product;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	Product findBySlug(String slug);

	Product findBySlugAndIdNot(String slug, int id);

	List<Product> findAllByCategoryId(String categoryId, Pageable pageable);

	long countByCategoryId(String categoryId);


}