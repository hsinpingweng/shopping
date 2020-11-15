package com.hsinpingweng.shopping;

import java.util.List;

import com.hsinpingweng.shopping.model.CategoryRepository;
import com.hsinpingweng.shopping.model.PageRepository;
import com.hsinpingweng.shopping.model.data.Category;
import com.hsinpingweng.shopping.model.data.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class Common {

    @Autowired
    private PageRepository pageRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    
    @ModelAttribute
    public void sharedData(Model model) {

        List<Page> pages = pageRepo.findAllByOrderBySortingAsc();
        List<Category> categories = categoryRepo.findAll();

        model.addAttribute("cpages", pages);
        model.addAttribute("ccategories", categories);
    }

}
