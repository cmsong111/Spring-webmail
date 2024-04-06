/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.control;

import deu.cse.spring_webmail.auth.AuthService;
import deu.cse.spring_webmail.auth.AuthServiceImpl;
import deu.cse.spring_webmail.model.Pop3Agent;
import deu.cse.spring_webmail.user.User;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * 초기 화면과 관리자 기능(사용자 추가, 삭제)에 대한 제어기
 *
 * @author skylo
 */
@Slf4j
@Controller
@AllArgsConstructor
public class SystemController {

    AuthService authService;

    @RequestMapping(value = "/login.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String loginDo(@RequestParam Integer menu, HttpServletRequest request, HttpSession session) {
        String url = "";
        log.debug("로그인 처리: menu = {}", menu);
        switch (menu) {
            case CommandType.LOGIN:
                String userid = request.getParameter("userid");
                String password = request.getParameter("passwd");

                // Check the login information is valid using <<model>>Pop3Agent.

                // Now call the correct page according to its validation result.
                if (authService.authenticate(userid, password)) {
                    if (isAdmin(userid)) {
                        // HttpSession 객체에 userid를 등록해 둔다.
                        session.setAttribute("userid", userid);
                        // response.sendRedirect("admin_menu.jsp");
                        url = "redirect:/admin_menu";
                    } else {
                        // HttpSession 객체에 userid와 password를 등록해 둔다.
                        session.setAttribute("userid", userid);
                        session.setAttribute("password", password);
                        // response.sendRedirect("main_menu.jsp");
                        url = "redirect:/main_menu";  // URL이 http://localhost:8080/webmail/main_menu 이와 같이 됨.
                        // url = "/main_menu";  // URL이 http://localhost:8080/webmail/login.do?menu=91 이와 같이 되어 안 좋음
                    }
                } else {
                    // RequestDispatcher view = request.getRequestDispatcher("login_fail.jsp");
                    // view.forward(request, response);
                    url = "redirect:/login_fail";
                }
                break;
            case CommandType.LOGOUT:
                session.invalidate();
                url = "redirect:/";  // redirect: 반드시 넣어야만 컨텍스트 루트로 갈 수 있음
                break;
            default:
                break;
        }
        return url;
    }

    @GetMapping("/login_fail")
    public String loginFail() {
        return "login_fail";
    }

    protected boolean isAdmin(String userid) {
        boolean status = false;

        if (userid.equals("admin")) {
            status = true;
        }

        return status;
    }

    @GetMapping("/main_menu")
    public String mainMenu(Model model, HttpSession session) {
        String userid = (String) session.getAttribute("userid");

        String messageList = "메시지 목록이 없습니다.";
        model.addAttribute("messageList", userid);
        return "main_menu";
    }

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
    public String addUserDo(@RequestParam String id, @RequestParam String password,
                            RedirectAttributes attrs) {

        try {
            // if (addUser successful)  사용자 등록 성공 팦업창
            // else 사용자 등록 실패 팝업창
            if (authService.addUser(id, password)) {
                attrs.addFlashAttribute("msg", String.format("사용자(%s) 추가를 성공하였습니다.", id));
            } else {
                attrs.addFlashAttribute("msg", String.format("사용자(%s) 추가를 실패하였습니다.", id));
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
     * @param selectedUsers <input type=checkbox> 필드의 선택된 이메일 ID. 자료형: String[]
     * @return
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


    @GetMapping("/img_test")
    public String imgTest() {
        return "img_test/img_test";
    }

    /**
     * https://34codefactory.wordpress.com/2019/06/16/how-to-display-image-in-jsp-using-spring-code-factory/
     *
     * @param imageName
     * @return
     */
    @RequestMapping(value = "/get_image/{imageName}")
    @ResponseBody
    public byte[] getImage(@PathVariable String imageName, ServletContext ctx) {
        try {
            String folderPath = ctx.getRealPath("/WEB-INF/views/img_test/img");
            return getImageBytes(folderPath, imageName);
        } catch (Exception e) {
            log.error("/get_image 예외: {}", e.getMessage());
        }
        return new byte[0];
    }

    private byte[] getImageBytes(String folderPath, String imageName) {
        ByteArrayOutputStream byteArrayOutputStream;
        BufferedImage bufferedImage;
        byte[] imageInByte;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            bufferedImage = ImageIO.read(new File(folderPath + File.separator + imageName));
            String format = imageName.substring(imageName.lastIndexOf(".") + 1);
            ImageIO.write(bufferedImage, format, byteArrayOutputStream);
            byteArrayOutputStream.flush();
            imageInByte = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return imageInByte;
        } catch (FileNotFoundException e) {
            log.error("getImageBytes 예외: {}", e.getMessage());
        } catch (Exception e) {
            log.error("getImageBytes 예외: {}", e.getMessage());
        }
        return null;
    }

}
