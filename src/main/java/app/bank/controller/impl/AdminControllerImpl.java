package app.bank.controller.impl;

import app.bank.controller.AdminController;
import app.bank.entity.User;
import app.bank.service.AdminService;
import app.bank.service.impl.JwtService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/bank/user")
public class AdminControllerImpl implements AdminController {

    private AdminService adminService;

    public AdminControllerImpl(AdminService adminService, JwtService jwtService) {
        this.adminService = adminService;
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping(path = "/list")
    @Override
    public List<User> getAllUsers(@RequestHeader("Authorization") String token) {
        return adminService.getAllUsers();
    }
}
