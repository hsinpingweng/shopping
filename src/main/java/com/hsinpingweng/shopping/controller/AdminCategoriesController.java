package com.hsinpingweng.shopping.controller;

import java.util.List;

import com.hsinpingweng.shopping.model.CategoryRepository;
import com.hsinpingweng.shopping.model.data.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoriesController {

    @Autowired
    private CategoryRepository categoryRepo;

    @GetMapping
    public String index(Model model) {

        List<Category> categories = categoryRepo.findAll();

        model.addAttribute("categories", categories);

        return "admin/categories/index";
    }
}
