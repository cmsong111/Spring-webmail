package deu.cse.spring_webmail.auth;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;


    @GetMapping("/signup")
    public String signup() {
        return "auth/sign_up";
    }

    @PostMapping("/signup.do")
    public String signupDo(String username, String password) {
        authService.addUser(new LoginForm(username, password));
        return "redirect:/";
    }

    @GetMapping("/login_fail")
    public String loginFail() {
        return "auth/login_fail";
    }
}
