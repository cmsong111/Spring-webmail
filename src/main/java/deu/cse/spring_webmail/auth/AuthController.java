package deu.cse.spring_webmail.auth;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 로그인/회원가입 컨트롤러
 */
@Controller
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {


    private final AuthService authService;

    /**
     * 로그인 페이지 요청 메소드
     *
     * @return 로그인 페이지
     */
    @GetMapping("/signup")
    public String signup() {
        return "auth/sign_up";
    }

    /**
     * 회원가입 요청 메소드 (POST)
     *
     * @param username 사용자 아이디
     * @param password 사용자 비밀번호
     * @param attrs    RedirectAttributes 객체
     * @return 로그인 페이지로 리다이렉트
     */
    @PostMapping("/signup.do")
    public String signupDo(String username, String password, RedirectAttributes attrs) {
        boolean result = authService.addUser(new LoginForm(username, password));
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
