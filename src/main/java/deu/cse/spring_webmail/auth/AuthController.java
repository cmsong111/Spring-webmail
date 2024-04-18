package deu.cse.spring_webmail.auth;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String signupDo(String username, String password, RedirectAttributes attrs) {
        boolean result =  authService.addUser(new LoginForm(username, password));
        if (result) {
            attrs.addFlashAttribute("msg", "회원가입에 성공하였습니다.");
        } else {
            attrs.addFlashAttribute("msg", "회원가입에 실패하였습니다.");
        }
        return "redirect:/";
    }

    @GetMapping("/login_fail")
    public String loginFail() {
        return "auth/login_fail";
    }
}
