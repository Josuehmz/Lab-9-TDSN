package com.lab.secureweb;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String redirectToLoginPage() {
        return "redirect:/login.html";
    }

    @GetMapping("/login.html")
    public String loginPage(HttpServletRequest request, Model model) {
        Object csrf = request.getAttribute("_csrf");
        if (csrf == null) {
            csrf = request.getAttribute(CsrfToken.class.getName());
        }
        model.addAttribute("_csrf", csrf);
        return "login";
    }
}
