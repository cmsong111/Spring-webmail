package deu.cse.spring_webmail.mail.controller;

import deu.cse.spring_webmail.mail.service.MailReceiver;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.security.Principal;

@Controller
@RequestMapping("/mail")
public class MailController {
    private final MailReceiver mailReceiver;

    public MailController(MailReceiver mailReceiver) {
        this.mailReceiver = mailReceiver;
    }

    /**
     * 메일함 페이지 요청 (받은 메일함)
     *
     * @param model     Model
     * @param principal Principal
     * @param page      페이지 번호
     * @param size      페이지 크기
     * @return 메일함 페이지
     */
    @GetMapping
    public String mailbox(Model model, Principal principal,
                          @RequestParam(value = "page", defaultValue = "1") int page,
                          @RequestParam(value = "size", defaultValue = "10") int size) {
        String userid = principal.getName();
        model.addAttribute("messageList", mailReceiver.getMailsByUserName(userid, page, size));
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("total", mailReceiver.countMailsByUserName(userid));
        model.addAttribute("unread", mailReceiver.countUnreadMailsByUserName(userid));
        model.addAttribute("type", "/mail");
        return "read_mail/main_menu";
    }

    /**
     * 휴지통 페이지 요청
     *
     * @param model     Model
     * @param principal Principal
     * @param page      페이지 번호
     * @param size      페이지 크기
     * @return 휴지통 페이지
     */
    @GetMapping("deleted")
    public String deletedMailbox(Model model, Principal principal,
                                 @RequestParam(value = "page", defaultValue = "1") int page,
                                 @RequestParam(value = "size", defaultValue = "10") int size) {
        String userid = principal.getName();
        model.addAttribute("messageList", mailReceiver.getDeletedMailsByUserName(userid, page, size));
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("total", mailReceiver.countDeletedMailsByUserName(userid));
        model.addAttribute("unread", mailReceiver.countUnreadDeletedMailsByUserName(userid));
        model.addAttribute("type", "/mail/deleted");
        return "read_mail/main_menu";
    }

    @GetMapping("unread")
    public String unreadMailbox(Model model, Principal principal,
                                @RequestParam(value = "page", defaultValue = "1") int page,
                                @RequestParam(value = "size", defaultValue = "10") int size) {
        String userid = principal.getName();
        model.addAttribute("messageList", mailReceiver.getUnreadMailsByUserName(userid, page, size));
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("total", mailReceiver.countUnreadMailsByUserName(userid));
        model.addAttribute("unread", mailReceiver.countUnreadMailsByUserName(userid));
        model.addAttribute("type", "/mail/unread");
        return "read_mail/main_menu";
    }

    /**
     * 메일 자세히 보기
     *
     * @param model Model
     * @param id    메일 아이디
     * @return 메일 자세히 보기 페이지
     */
    @GetMapping("/{id}")
    public String getMail(Model model, @PathVariable("id") Long id) {
        model.addAttribute("message", mailReceiver.getMail(id));
        model.addAttribute("id", id);
        return "read_mail/show_message";
    }

    /**
     * 첨부파일 다운로드
     *
     * @param id       메일 아이디
     * @param filename 파일 이름
     * @return 파일 다운로드
     */
    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadAttachment(@PathVariable("id") Long id, @RequestParam("filename") String filename) {
        Resource file = mailReceiver.downloadAttachment(id, filename);

        String encodedFilename = UriUtils.encode(filename, StandardCharsets.UTF_8);
        String contentDisposition = String.format("attachment; filename=\"%s\"", encodedFilename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(file);
    }

    /**
     * 메일 삭제
     *
     * @param id 메일 아이디
     * @return 메일함 페이지
     */
    @GetMapping("/delete/{id}")
    public String deleteMail(@PathVariable("id") Long id) {
        mailReceiver.deleteMail(id);
        return "redirect:/mail";
    }

    /**
     * 메일 복구
     *
     * @param id 메일 아이디
     * @return 메일함 페이지
     */
    @GetMapping("/restore/{id}")
    public String restoreMail(@PathVariable("id") Long id) {
        mailReceiver.restoreMail(id);
        return "redirect:/mail/deleted";
    }

}
