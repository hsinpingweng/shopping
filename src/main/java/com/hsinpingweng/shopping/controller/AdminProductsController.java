package com.hsinpingweng.shopping.controller;

import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import com.hsinpingweng.shopping.model.CategoryRepository;
import com.hsinpingweng.shopping.model.ProductRepository;
import com.hsinpingweng.shopping.model.data.Category;
import com.hsinpingweng.shopping.model.data.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/products")
public class AdminProductsController {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @GetMapping
    public String index(Model model) {

        List<Product> products = productRepo.findAll();
        List<Category> categories = categoryRepo.findAll();
        HashMap<Integer, String> cats = new HashMap<>();
        for(Category cat : categories){
            cats.put(cat.getId(), cat.getName());
        }

        model.addAttribute("products", products);
        model.addAttribute("cats", cats);

        return "admin/products/index";
    }

    @GetMapping("/add")
    public String add(Product product, Model model) {

        List<Category> categories = categoryRepo.findAll();
        model.addAttribute("categories", categories);

        return "admin/products/add";
    }


    @PostMapping("/add")
    public String add(@Valid Product product, 
                        BindingResult bindingResult, 
                        MultipartFile file, 
                        RedirectAttributes redirectAttributes, 
                        Model model) throws IOException {

        List<Category> categories = categoryRepo.findAll();

        if(bindingResult.hasErrors()) {
            model.addAttribute("categories", categories);
            return "admin/products/add";
        }

        boolean fileOk = false;
        byte[] bytes = file.getBytes();
        String filename = file.getOriginalFilename();
        Path path = Paths.get("src/main/resources/static/media/"+filename);
        
        if (filename.endsWith("jpg") || filename.endsWith("png")){
            fileOk = true;
        }

        redirectAttributes.addFlashAttribute("message", "Paged added");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        String slug = product.getName().toLowerCase().replace(" ", "-");
    
        Product productExist = productRepo.findBySlug(slug);

        if (!fileOk) {
            redirectAttributes.addFlashAttribute("message", "Image must be JPG or PNG");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("product", product);
        } else if (productExist != null) {
            redirectAttributes.addFlashAttribute("message", "Product exists, choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("product", product);
            
        } else {
            product.setSlug(slug);
            product.setImage(filename);
            productRepo.save(product);
            Files.write(path, bytes);
        }

        return "redirect:/admin/products/add";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {

        List<Category> categories = categoryRepo.findAll();
        Product product = productRepo.getOne(id);

        model.addAttribute("product", product);
        model.addAttribute("categories", categories);

        return "admin/products/edit";
    }


    @PostMapping("/edit")
    public String edit(@Valid Product product, 
                        BindingResult bindingResult, 
                        MultipartFile file, 
                        RedirectAttributes redirectAttributes, 
                        Model model) throws IOException {

        Product currentProduct = productRepo.getOne(product.getId());

        List<Category> categories = categoryRepo.findAll();

        if(bindingResult.hasErrors()) {
            model.addAttribute("productName", currentProduct.getName());
            model.addAttribute("categories", categories);
            return "admin/products/edit";
        }

        boolean fileOk = false;
        byte[] bytes = file.getBytes();
        String filename = file.getOriginalFilename();
        Path path = Paths.get("src/main/resources/static/media/" + filename);
        
        if (!file.isEmpty()){
            if (filename.endsWith("jpg") || filename.endsWith("png")){
                fileOk = true;
            }
        } else {
            fileOk = true;
        }
    

        redirectAttributes.addFlashAttribute("message", "Paged edited");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        String slug = product.getName().toLowerCase().replace(" ", "-");
    
        Product productExist = productRepo.findBySlugAndIdNot(slug, product.getId());

        if (!fileOk) {
            redirectAttributes.addFlashAttribute("message", "Image must be JPG or PNG");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("product", product);
        } else if (productExist != null) {
            redirectAttributes.addFlashAttribute("message", "Product exists, choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("product", product);
            
        } else {
            product.setSlug(slug);

            if (!file.isEmpty()) {
                Path path2 = Paths.get("src/main/resources/static/media/" + product.getImage());
                Files.delete(path2);
                product.setImage(filename);
                Files.write(path, bytes);
            } else {
                product.setImage(currentProduct.getImage());
            }

            productRepo.save(product);
        }

        return "redirect:/admin/products/edit/" + product.getId();
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, RedirectAttributes redirectAttributes) throws IOException {

        Product product = productRepo.getOne(id);
        Product currentProduct = productRepo.getOne(product.getId());

        Path path = Paths.get("src/main/resources/static/media/" + currentProduct.getImage());
        Files.delete(path);
        productRepo.deleteById(id);

        redirectAttributes.addFlashAttribute("message", "product deleted!");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        return "redirect:/admin/products";
    }

    
}
