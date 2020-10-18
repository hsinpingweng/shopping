package com.hsinpingweng.shopping.controller;

import java.util.List;

import javax.validation.Valid;

import com.hsinpingweng.shopping.model.PageRepository;
import com.hsinpingweng.shopping.model.data.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/admin/pages")
public class AdminPagesController {

    @Autowired
    private PageRepository pageRepo;
    // public AdminPagesController(PageRepository pageRepo) {
    //     this.pageRepo = pageRepo;
    // }

    @GetMapping
    public String index(Model model) {

        List<Page> pages = pageRepo.findAll();

        model.addAttribute("pages", pages);

        return "admin/pages/index";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute Page page) {

        return "admin/pages/add";
    }

    @PostMapping("/add")
    public String add(@Valid Page page, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

            if(bindingResult.hasErrors()) return "admin/pages/add";

            redirectAttributes.addFlashAttribute("message", "Paged added");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");

            String slug = page.getSlug() == "" ? page.getTitle().toLowerCase().replace(" ", "-") : page.getSlug().toLowerCase().replace(" ", "-");
        

            Page slugExist = pageRepo.findBySlug(slug);
            if (slugExist != null) {
                // add value in session
                redirectAttributes.addFlashAttribute("message", "Slug exists!");
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                redirectAttributes.addFlashAttribute("page", page);
            } else {
                page.setSlug(slug);
                page.setSorting(100);
                pageRepo.save(page);
            }

        return "redirect:/admin/pages/add";
    }
}
