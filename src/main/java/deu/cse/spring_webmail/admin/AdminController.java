package deu.cse.spring_webmail.admin;

import deu.cse.spring_webmail.auth.AuthService;
import deu.cse.spring_webmail.auth.LoginForm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
//@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@AllArgsConstructor
public class AdminController {

    AdminService adminService;
    AuthService authService;

    @GetMapping("/admin_menu")
    public String adminMenu(Model model) {
        model.addAttribute("userList", authService.getUserList());
        return "admin/admin_menu";
    }

    @GetMapping("/add_user")
    public String addUser() {
        return "admin/add_user";
    }

    @PostMapping("/add_user.do")
    public String addUserDo(String username, String password,
                            RedirectAttributes attrs) {

        LoginForm loginForm = new LoginForm(username, password);

        try {
            if (authService.addUser(loginForm)) {
                attrs.addFlashAttribute("msg", String.format("사용자(%s) 추가를 성공하였습니다.", loginForm.username()));
            } else {
                attrs.addFlashAttribute("msg", String.format("사용자(%s) 추가를 실패하였습니다.", loginForm.username()));
            }
        } catch (Exception ex) {
            log.error("add_user.do: 시스템 접속에 실패했습니다. 예외 = {}", ex.getMessage());
        }

        return "redirect:/admin_menu";
    }


    @GetMapping("/delete_user")
    public String deleteUser(Model model) {
        log.debug("delete_user called");
        model.addAttribute("userList", authService.getUserList());
        return "admin/delete_user";
    }

    /**
     * @param selectedUsers : 삭제할 사용자 아이디 목록
     * @return "redirect:/admin_menu" : 사용자 삭제 성공 시, 관리자 메뉴로 이동
     */
    @PostMapping("delete_user.do")
    public String deleteUserDo(@RequestParam String[] selectedUsers) {
        log.debug("delete_user.do: selectedUser = {}", List.of(selectedUsers));

        try {
            for (String id : selectedUsers) {
                authService.deleteUser(id);
            }
        } catch (Exception ex) {
            log.error("delete_user.do : 예외 = {}", ex);
        }

        return "redirect:/admin_menu";
    }
}
