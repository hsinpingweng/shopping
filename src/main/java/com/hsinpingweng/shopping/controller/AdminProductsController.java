package com.hsinpingweng.shopping.controller;

import java.util.List;

import com.hsinpingweng.shopping.model.ProductRepository;
import com.hsinpingweng.shopping.model.data.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/products")
public class AdminProductsController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public String index(Model model) {

        List<Product> products = productRepository.findAll();

        model.addAttribute("products", products);

        return "admin/products/index";
    }
    
}
