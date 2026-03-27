package com.example.appliance.controllers.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.appliance.models.Brand;
import com.example.appliance.services.BrandService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/brands")
public class BrandAdminController {

    private final BrandService brandService;

    public BrandAdminController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public String listBrands(Model model) {
        model.addAttribute("brands", brandService.findAll());
        return "admin/brands/list";
    }

    @GetMapping("/{id}")
    public String viewBrand(@PathVariable Integer id, Model model) {
        model.addAttribute("brand", brandService.findById(id));
        return "admin/brands/view";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("brand", new Brand());
        model.addAttribute("formTitle", "Добавление бренда");
        return "admin/brands/form";
    }

    @PostMapping
    public String createBrand(@Valid @ModelAttribute("brand") Brand brand,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("formTitle", "Добавление бренда");
            return "admin/brands/form";
        }
        brandService.save(brand);
        return "redirect:/admin/brands";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("brand", brandService.findById(id));
        model.addAttribute("formTitle", "Редактирование бренда");
        return "admin/brands/form";
    }

    @PostMapping("/{id}")
    public String updateBrand(@PathVariable Integer id,
                              @Valid @ModelAttribute("brand") Brand brand,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("formTitle", "Редактирование бренда");
            return "admin/brands/form";
        }
        brand.setIdBrand(id);
        brandService.save(brand);
        return "redirect:/admin/brands";
    }

    @PostMapping("/{id}/delete")
    public String deleteBrand(@PathVariable Integer id) {
        brandService.deleteById(id);
        return "redirect:/admin/brands";
    }
}