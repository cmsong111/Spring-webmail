package deu.cse.spring_webmail.mail;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return "main_menu";
    }

    @GetMapping("/{id}")
    public String getMail(Model model, @PathVariable("id") Long id) {
        model.addAttribute("message", mailService.getMail(id));
        model.addAttribute("id", id);
        return "read_mail/show_message";
    }
}
