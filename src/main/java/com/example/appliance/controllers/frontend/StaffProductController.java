package com.example.appliance.controllers.frontend;

import com.example.appliance.models.Product;
import com.example.appliance.services.BrandService;
import com.example.appliance.services.CategoryService;
import com.example.appliance.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/staff/products")
public class StaffProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;

    public StaffProductController(ProductService productService,
                                  CategoryService categoryService,
                                  BrandService brandService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.brandService = brandService;
    }

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.findAll());
        return "staff/products/list";
    }

    @GetMapping("/{id}")
    public String viewProduct(@PathVariable Integer id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "staff/products/view";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("formTitle", "Добавление товара");
        return "staff/products/form";
    }

    @PostMapping
    public String createProduct(@Valid @ModelAttribute("product") Product product,
                                BindingResult bindingResult,
                                @RequestParam("categoryId") Integer categoryId,
                                @RequestParam("brandId") Integer brandId,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("brands", brandService.findAll());
            model.addAttribute("formTitle", "Добавление товара");
            return "staff/products/form";
        }

        product.setCategory(categoryService.findById(categoryId));
        product.setBrand(brandService.findById(brandId));
        productService.save(product);
        return "redirect:/staff/products";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("product", productService.findById(id));
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("formTitle", "Редактирование товара");
        return "staff/products/form";
    }

    @PostMapping("/{id}")
    public String updateProduct(@PathVariable Integer id,
                                @Valid @ModelAttribute("product") Product product,
                                BindingResult bindingResult,
                                @RequestParam("categoryId") Integer categoryId,
                                @RequestParam("brandId") Integer brandId,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("brands", brandService.findAll());
            model.addAttribute("formTitle", "Редактирование товара");
            return "staff/products/form";
        }

        product.setIdProduct(id);
        product.setCategory(categoryService.findById(categoryId));
        product.setBrand(brandService.findById(brandId));
        productService.save(product);
        return "redirect:/staff/products";
    }

    @PostMapping("/{id}/delete")
    public String deleteProduct(@PathVariable Integer id) {
        productService.deleteById(id);
        return "redirect:/staff/products";
    }
}