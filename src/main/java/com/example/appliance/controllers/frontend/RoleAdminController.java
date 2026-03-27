package com.example.appliance.controllers.frontend;

import com.example.appliance.models.Role;
import com.example.appliance.services.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/roles")
public class RoleAdminController {

    private final RoleService roleService;

    public RoleAdminController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("roles", roleService.findAll());
        return "admin/roles/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("role", new Role());
        model.addAttribute("formTitle", "Создание роли");
        return "admin/roles/form";
    }

    @PostMapping
    public String create(@ModelAttribute Role role) {
        roleService.save(role);
        return "redirect:/admin/roles";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Integer id, Model model) {
        model.addAttribute("role", roleService.findById(id));
        return "admin/roles/view";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("role", roleService.findById(id));
        model.addAttribute("formTitle", "Редактирование роли");
        return "admin/roles/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Integer id, @ModelAttribute Role role) {
        role.setIdRole(id);
        roleService.save(role);
        return "redirect:/admin/roles";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
        roleService.deleteById(id);
        return "redirect:/admin/roles";
    }
}