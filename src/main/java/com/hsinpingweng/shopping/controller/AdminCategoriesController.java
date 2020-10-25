package com.hsinpingweng.shopping.controller;

import java.util.List;

import javax.validation.Valid;

import com.hsinpingweng.shopping.model.CategoryRepository;
import com.hsinpingweng.shopping.model.data.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    // @GetMapping("/add")
    // public String add(Category category) {
    //     return "admin/categories/add";
    // }

    

    @GetMapping("/add")
    public String add(Category category) {
        return "admin/categories/add";
    }


    @PostMapping("/add")
    public String add(@Valid Category category, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        if(bindingResult.hasErrors()) return "admin/categories/add";

        redirectAttributes.addFlashAttribute("message", "Category added");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        String slug = category.getName().toLowerCase().replace(" ", "-");
    
        Category categoryExist = categoryRepo.findByName(category.getName());
        if (categoryExist != null) {
            // add value in session
            redirectAttributes.addFlashAttribute("message", "Category exists!");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("categoryInfo", category);
        } else {
            category.setSlug(slug);
            category.setSorting(100);
            categoryRepo.save(category);
        }

        return "redirect:/admin/categories/add";
    }
}
