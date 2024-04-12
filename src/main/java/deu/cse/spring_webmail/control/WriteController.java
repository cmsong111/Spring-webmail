/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.control;

import deu.cse.spring_webmail.mail.service.EmailSender;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
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
public class WriteController {
    @Value("${file.upload_folder}")
    private String UPLOAD_FOLDER;
    @Value("${file.max_size}")
    private String MAX_SIZE;

    private EmailSender emailSender;

    @Autowired
    public WriteController(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @GetMapping("/write_mail")
    public String writeMail() {
        log.debug("write_mail called...");
        return "write_mail/write_mail";
    }

    @PostMapping("/write_mail.do")
    public String writeMailDo(@RequestParam String to, @RequestParam String cc,
                              @RequestParam String subj, @RequestParam String body,
                              @RequestParam(name = "file1") MultipartFile upFile,
                              RedirectAttributes attrs, Principal principal) throws MessagingException {
        log.debug("write_mail.do called...");

        List<MultipartFile> files = new ArrayList<>();
        // 첨부파일이 있을 경우, 업로드 처리
        if (upFile != null && !upFile.isEmpty()) {
            files.add(upFile);
        }
        boolean sendSuccessful;
        String from = principal.getName() + "@localhost";

        if (files.isEmpty()) {
            sendSuccessful = emailSender.sendMail(from, to, cc, subj, body);
        } else {
            sendSuccessful = emailSender.sendEmail(from, to, cc, subj, body, files);
        }

        if (sendSuccessful) {
            attrs.addFlashAttribute("msg", "메일 전송이 성공했습니다.");
        } else {
            attrs.addFlashAttribute("msg", "메일 전송이 실패했습니다.");
        }

        return "redirect:/main_menu";
    }
}
