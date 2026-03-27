package com.example.appliance.controllers.frontend;

import com.example.appliance.models.Category;
import com.example.appliance.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/staff/categories")
public class StaffCategoryController {

    private final CategoryService categoryService;

    public StaffCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "staff/categories/list";
    }

    @GetMapping("/{id}")
    public String viewCategory(@PathVariable Integer id, Model model) {
        model.addAttribute("category", categoryService.findById(id));
        return "staff/categories/view";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("formTitle", "Добавление категории");
        return "staff/categories/form";
    }

    @PostMapping
    public String createCategory(@Valid @ModelAttribute("category") Category category,
                                 BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("formTitle", "Добавление категории");
            return "staff/categories/form";
        }

        categoryService.save(category);
        return "redirect:/staff/categories";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("category", categoryService.findById(id));
        model.addAttribute("formTitle", "Редактирование категории");
        return "staff/categories/form";
    }

    @PostMapping("/{id}")
    public String updateCategory(@PathVariable Integer id,
                                 @Valid @ModelAttribute("category") Category category,
                                 BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("formTitle", "Редактирование категории");
            return "staff/categories/form";
        }

        category.setIdCategory(id);
        categoryService.save(category);
        return "redirect:/staff/categories";
    }

    @PostMapping("/{id}/delete")
    public String deleteCategory(@PathVariable Integer id) {
        categoryService.deleteById(id);
        return "redirect:/staff/categories";
    }
}