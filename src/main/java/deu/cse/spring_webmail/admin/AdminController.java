package deu.cse.spring_webmail.admin;

import deu.cse.spring_webmail.auth.AuthServiceJPA;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
//@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    AdminService adminService;
    AuthServiceJPA authService;

    @GetMapping("/admin_menu")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminMenu(Model model) {
        model.addAttribute("userList", authService.getUserList());
        return "admin/admin_menu";
    }
}
