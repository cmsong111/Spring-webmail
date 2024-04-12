package deu.cse.spring_webmail.mail;

import deu.cse.spring_webmail.mail.service.MailService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.security.Principal;

@Controller
@RequestMapping("/mail")
public class MailController {
    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping
    public String mailbox(Model model, Principal principal) {
        String userid = principal.getName();
        model.addAttribute("messageList", mailService.getMailsByUserName(userid));
        return "read_mail/main_menu";
    }

    @GetMapping("/{id}")
    public String getMail(Model model, @PathVariable("id") Long id) {
        model.addAttribute("message", mailService.getMail(id));
        model.addAttribute("id", id);
        return "read_mail/show_message";
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadAttachment(@PathVariable("id") Long id, @RequestParam("filename") String filename) {
        Resource file = mailService.downloadAttachment(id, filename);

        String encodedFilename = UriUtils.encode(filename, StandardCharsets.UTF_8);
        String contentDisposition = String.format("attachment; filename=\"%s\"", encodedFilename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(file);
    }
}
