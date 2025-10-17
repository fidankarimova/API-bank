package app.bank.controller.impl;

import app.bank.controller.AdminController;
import app.bank.entity.User;
import app.bank.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/bank/user")
public class AdminControllerImpl implements AdminController {

    private final AdminService adminService;

    @PreAuthorize("hasRole('admin')")
    @GetMapping(path = "/list")
    @Override
    public List<User> getAllUsers(@RequestHeader("Authorization") String token) {
        return adminService.getAllUsers();
    }
}
