package deu.cse.spring_webmail.mail.controller;

import deu.cse.spring_webmail.mail.dto.MailBoxType;
import deu.cse.spring_webmail.mail.service.MailReceiver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/mail")
public class MailBoxController {
    private final MailReceiver mailReceiver;

    public MailBoxController(MailReceiver mailReceiver) {
        this.mailReceiver = mailReceiver;
    }

    /**
     * 로그인 완료 후 받은 메일함으로 이동
     *
     * @return 받은 메일함 페이지
     */
    @GetMapping
    public String mailIndex() {
        return "redirect:/mail/1";
    }

    /**
     * 메일함 페이지 요청 (받은 메일함)
     *
     * @param model       Model
     * @param principal   Principal
     * @param mailBoxTypeId 메일함 아이디 (1: 받은 메일함, 2: 보낸 메일함, 3: 임시보관함, 4: 휴지통)
     * @param page        페이지 번호
     * @param size        페이지 크기
     * @return 메일함 페이지
     */
    @GetMapping("/{mailBoxId}")
    public String mailbox(Model model, Principal principal,
                          @PathVariable(value = "mailBoxId") int mailBoxTypeId,
                          @RequestParam(value = "page", defaultValue = "1") int page,
                          @RequestParam(value = "size", defaultValue = "10") int size) {
        // 파라미터
        String userid = principal.getName();
        MailBoxType mailBoxType = MailBoxType.fromValue(mailBoxTypeId);

        // Model에 데이터 추가
        model.addAttribute("messageList", mailReceiver.getMailFromMailBox(userid, mailBoxType, page, size));
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("total", mailReceiver.getCountMailAtMailbox(userid, mailBoxType));
        model.addAttribute("unread", mailReceiver.getCountUnReadMailAtMailbox(userid, mailBoxType));
        model.addAttribute("mailBoxType", "/mail/" + mailBoxTypeId);
        return "read_mail/main_menu";
    }

    /**
     * 읽지 않은 메일함 페이지 요청
     *
     * @param model       Model
     * @param principal   Principal
     * @param mailBoxTypeId 메일함 아이디 (1: 받은 메일함, 2: 보낸 메일함, 3: 임시보관함, 4: 휴지통)
     * @param page        페이지 번호
     * @param size        페이지 크기
     * @return 메일함 페이지
     */
    @GetMapping("/{mailBoxId}/unread")
    public String unreadMailbox(Model model, Principal principal,
                                @PathVariable("mailBoxId") int mailBoxTypeId,
                                @RequestParam(value = "page", defaultValue = "1") int page,
                                @RequestParam(value = "size", defaultValue = "10") int size) {
        // 파라미터
        String userid = principal.getName();
        MailBoxType mailBoxType = MailBoxType.fromValue(mailBoxTypeId);

        model.addAttribute("messageList", mailReceiver.getMailFromMailBox(userid, mailBoxType, page, size));
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("total", mailReceiver.getCountMailAtMailbox(userid, mailBoxType));
        model.addAttribute("unread", mailReceiver.getCountUnReadMailAtMailbox(userid, mailBoxType));
        model.addAttribute("mailBoxType", "/mail/" + mailBoxTypeId);
        return "read_mail/main_menu";
    }
}
