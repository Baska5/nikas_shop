package com.exadel.nikas_shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WelcomeController {

    @GetMapping()
    String welcome() {
        return "<h1>Welcome!</h1>" +
                "<a href=\"http://localhost:8080/user\">\n" +
                "   <button>GO TO USER</button>\n" +
                "</a>" +
                "<a href=\"http://localhost:8080/admin\">\n" +
                "   <button>GO TO ADMIN</button>\n" +
                "</a>" +
                "<a href=\"http://localhost:8080/swagger-ui.html\">\n" +
                "   <button>GO TO SWAGGER</button>\n" +
                "</a>" +
                "<a href=\"http://localhost:8080/login\">\n" +
                "   <button>LOGIN</button>\n" +
                "</a>";
    }

    @GetMapping("/user")
    @PreAuthorize("@securityAuthService.hasAnyAccess('admin', 'staff', 'user')")
    String commonUser() {
        return "<h1>Hello User</h1>" +
                "<a href=\"http://localhost:8080/\">\n" +
                "   <button>BACK</button>\n" +
                "</a>" +
                "<a href=\"http://localhost:8080/logout\">\n" +
                "   <button>LOGOUT</button>\n" +
                "</a>";
    }

    @GetMapping("/admin")
    @PreAuthorize("@securityAuthService.hasAccess('admin')")
    String adminUser() {
        return "<h1>Hello Admin</h1>" +
                "<a href=\"http://localhost:8080/\">\n" +
                "   <button>BACK</button>\n" +
                "</a>"+
                "<a href=\"http://localhost:8080/logout\">\n" +
                "   <button>LOGOUT</button>\n" +
                "</a>";
    }

    @GetMapping("/staff")
    @PreAuthorize("@securityAuthService.hasAnyAccess('admin', 'staff')")
    String staffUser() {
        return "<h1>Hello Staff</h1>" +
                "<a href=\"http://localhost:8080/\">\n" +
                "   <button>BACK</button>\n" +
                "</a>"+
                "<a href=\"http://localhost:8080/logout\">\n" +
                "   <button>LOGOUT</button>\n" +
                "</a>";
    }


}
