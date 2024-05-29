/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.mail.controller;

import deu.cse.spring_webmail.mail.service.EmailSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * 메일 쓰기를 위한 제어기
 *
 * @author Prof.Jong Min Lee
 */
@Slf4j
@Controller
public class MailWriteController {

    @Value("${file.max_size}")
    private String MAX_SIZE;

    private EmailSender emailSender;

    @Autowired
    public MailWriteController(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @GetMapping("/write_mail")
    public String writeMail(
            @RequestParam(value = "mail", required = false) String mail,
            Model model) {
        if (!mail.contains("@")) {
            mail += "@localhost";
        }
        model.addAttribute("mail", mail);
        return "write_mail/write_mail";
    }

    @GetMapping("/write_mail_success")
    public String writeMailSuccess() {
        return "write_mail/write_mail_success";
    }

    @GetMapping("/write_mail_fail")
    public String writeMailFail(@RequestParam(name = "error", required = false) String error, Model model) {
        model.addAttribute("error", error);
        return "write_mail/write_mail_fail";
    }


    @PostMapping("/write_mail.do")
    public String writeMailDo(@RequestParam String to, @RequestParam String cc,
                              @RequestParam String subj, @RequestParam String body,
                              @RequestParam(name = "file1", required = false) MultipartFile upFile,
                              RedirectAttributes attrs, Principal principal) {

        List<MultipartFile> files = new ArrayList<>();
        String resultMessage = "";


        // 첨부파일 크기 제한
        if (!upFile.isEmpty() && upFile.getSize() > Long.parseLong(MAX_SIZE)) {
            attrs.addFlashAttribute("msg", "첨부파일 크기는 " + MAX_SIZE + "바이트 이하여야 합니다.");
            return "redirect:/write_mail";
        }
        // 첨부파일이 있을 경우, 업로드 처리
        if (!upFile.isEmpty()) {
            files.add(upFile);
        }

        try {
            resultMessage = emailSender.sendEmail(principal.getName(), to, cc, subj, body, files);
            attrs.addFlashAttribute("msg", resultMessage);
        } catch (MailException e) {
            String errorMessage = e.getMessage().replace("\n", "<br>").replace("\"", "\\\"");
            attrs.addAttribute("error", errorMessage);
            return "redirect:/write_mail_fail";
        }
        return "redirect:/write_mail_success";
    }

    @PostMapping("write_mail.temp")
    public String writeMailTemp(@RequestParam String to, @RequestParam String cc,
                                @RequestParam String subj, @RequestParam String body,
                                @RequestParam(name = "file1", required = false) MultipartFile upFile,
                                RedirectAttributes attrs, Principal principal) {
        List<MultipartFile> files = new ArrayList<>();
        if (!upFile.isEmpty()) {
            files.add(upFile);
        }
        String result = emailSender.saveTempMail(principal.getName(), to, cc, subj, body, files);
        attrs.addFlashAttribute("msg", result);
        return "redirect:/mail";
    }

}
