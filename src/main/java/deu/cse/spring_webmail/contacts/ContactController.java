package deu.cse.spring_webmail.contacts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

/**
 * 연락처 컨트롤러
 */
@Controller
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    /**
     * 연락처 목록 페이지 요청 메소드
     *
     * @return 연락처 목록 페이지
     */
    @GetMapping
    public String contacts(Model model, Principal principal) {
        model.addAttribute("contacts", contactService.getContacts(principal.getName()));
        return "contacts/contacts";
    }

    @GetMapping("/add")
    public String addContact() {
        return "contacts/add_contact";
    }

    @PostMapping("/add")
    public String addContact(String friend, Principal principal, RedirectAttributes attrs, String nickname,
                             @RequestParam(required = false) Long friendId) {

        String result;

        if (friendId == null) {
            result = contactService.addContact(principal.getName(), friend, nickname);
        } else {
            result = contactService.editContact(friendId, friend, nickname);
        }

        attrs.addFlashAttribute("msg", result);
        return "redirect:/contacts";
    }

    @GetMapping("/delete")
    public String deleteContact(
            @RequestParam(value = "id") Long contactId
    ) {
        contactService.deleteContact(contactId);
        return "redirect:/contacts";
    }

    @GetMapping("/edit")
    public String editContact(
            @RequestParam(value = "id") Long contactId,
            Model model
    ) {
        model.addAttribute("contact", contactService.getContact(contactId));
        return "contacts/add_contact";
    }
}
