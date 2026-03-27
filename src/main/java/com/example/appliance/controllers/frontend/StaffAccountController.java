package com.example.appliance.controllers.frontend;

import com.example.appliance.models.Account;
import com.example.appliance.services.AccountService;
import com.example.appliance.services.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/staff/accounts")
public class StaffAccountController {

    private final AccountService accountService;
    private final RoleService roleService;

    public StaffAccountController(AccountService accountService,
                                  RoleService roleService) {
        this.accountService = accountService;
        this.roleService = roleService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("accounts", accountService.findAll());
        return "staff/accounts/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("account", new Account());
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("formTitle", "Создание аккаунта");
        return "staff/accounts/form";
    }

    @PostMapping
    public String create(@ModelAttribute Account account,
                         @RequestParam Integer roleId,
                         Model model) {
        try {
            account.setRole(roleService.findById(roleId));
            accountService.create(account);
            return "redirect:/staff/accounts";
        } catch (RuntimeException ex) {
            model.addAttribute("account", account);
            model.addAttribute("roles", roleService.findAll());
            model.addAttribute("formTitle", "Создание аккаунта");
            model.addAttribute("errorMessage", ex.getMessage());
            return "staff/accounts/form";
        }
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Integer id, Model model) {
        model.addAttribute("account", accountService.findById(id));
        return "staff/accounts/view";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, Model model) {
        Account account = accountService.findById(id);
        account.setUserPassword("");
        model.addAttribute("account", account);
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("formTitle", "Редактирование аккаунта");
        return "staff/accounts/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Integer id,
                         @ModelAttribute Account account,
                         @RequestParam Integer roleId,
                         Model model) {
        try {
            account.setIdAccount(id);
            account.setRole(roleService.findById(roleId));
            accountService.update(account);
            return "redirect:/staff/accounts";
        } catch (RuntimeException ex) {
            model.addAttribute("account", account);
            model.addAttribute("roles", roleService.findAll());
            model.addAttribute("formTitle", "Редактирование аккаунта");
            model.addAttribute("errorMessage", ex.getMessage());
            return "staff/accounts/form";
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
        accountService.deleteById(id);
        return "redirect:/staff/accounts";
    }
}