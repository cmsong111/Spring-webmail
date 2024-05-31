package deu.cse.spring_webmail.mail.controller;

import deu.cse.spring_webmail.mail.dto.MailBoxType;
import deu.cse.spring_webmail.mail.dto.MailDto;
import deu.cse.spring_webmail.mail.service.MailManager;
import deu.cse.spring_webmail.mail.service.MailReceiver;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
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
import java.security.GeneralSecurityException;
import java.security.Principal;

@Controller
@AllArgsConstructor
@RequestMapping("/mail/{mailBoxType}")
public class MailReaderController {

    private final MailReceiver mailReceiver;
    private final MailManager mailManager;


    /**
     * 메일 자세히 보기
     *
     * @param model       Model
     * @param mailBoxType 메일 아이디
     *                    (1: 받은 메일함, 2: 보낸 메일함, 3: 임시보관함, 4: 휴지통)
     * @param mailId      메일 아이디
     * @return 메일 자세히 보기 페이지
     */
    @GetMapping("/{mailId}")
    public String getMail(Model model,
                          @PathVariable("mailId") Long mailId,
                          @PathVariable("mailBoxType") int mailBoxType,
                          Principal principal) {
        model.addAttribute("message", mailReceiver.getMail(mailBoxType, mailId, principal.getName()));
        model.addAttribute("id", mailId);
        return "read_mail/show_message";
    }

    /**
     * 임시 저장 메일 작성 페이지로 이동
     *
     * @param model       Model
     * @param mailBoxType 메일 아이디
     *                    (1: 받은 메일함, 2: 보낸 메일함, 3: 임시보관함, 4: 휴지통)
     * @param mailId      메일 아이디
     * @return 메일 자세히 보기 페이지
     */
    @GetMapping("/{mailId}/edit")
    public String getMailEdit(Model model,
                               @PathVariable("mailId") Long mailId,
                               @PathVariable("mailBoxType") int mailBoxType,
                               Principal principal) {
        model.addAttribute("message", mailReceiver.getMail(mailBoxType, mailId, principal.getName()));
        model.addAttribute("id", mailId);
        return "write_mail/write_mail_edit";
    }

    @GetMapping("/{mailId}/reply")
    public String getMailReply(Model model,
                               @PathVariable("mailId") Long mailId,
                               @PathVariable("mailBoxType") int mailBoxType,
                               Principal principal) {
        MailDto message = mailReceiver.getMail(mailBoxType, mailId, principal.getName());
        model.addAttribute("message", message);
        model.addAttribute("id", mailId);
        return "write_mail/write_mail_reply";
    }


    /**
     * 첨부파일 다운로드
     *
     * @param mailBoxType 메일함 유형
     * @param mailId      메일 아이디
     * @param filename    파일 이름
     * @return 파일 다운로드
     */
    @GetMapping("/{mailId}/download")
    public ResponseEntity<Resource> downloadAttachment(
            @PathVariable("mailBoxType") int mailBoxType,
            @PathVariable("mailId") Long mailId,
            @RequestParam("filename") String filename,
            Principal principal) {
        Resource file = mailReceiver.downloadAttachment(mailId, filename);

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
    @GetMapping("/{id}/delete")
    public String deleteMail(
            @PathVariable("mailBoxType") Long mailBoxId,
            @PathVariable("id") Long id,
            Principal principal) throws MessagingException, GeneralSecurityException {
        mailManager.moveMail(principal.getName(), id, MailBoxType.TRASH, MailBoxType.INBOX);
        return "redirect:/mail";
    }

    /**
     * 메일 복구
     *
     * @param id 메일 아이디
     * @return 메일함 페이지
     */
    @GetMapping("/{id}/restore")
    public String restoreMail(
            @PathVariable("mailBoxType") Long mailBoxId,
            @PathVariable("id") Long id, Principal principal) throws MessagingException,GeneralSecurityException {
        mailManager.moveMail(principal.getName(), id, MailBoxType.INBOX, MailBoxType.TRASH);
        return "redirect:/mail";
    }


    /**
     * 메일 영구 삭제
     *
     * @param id 메일 아이디
     * @return 메일함 페이지
     */
    @GetMapping("/{id}/clear")
    public String clearMail(
            @PathVariable("mailBoxType") int mailBoxType,
            @PathVariable("id") Long id, Principal principal) {
        mailManager.deleteMail(principal.getName(), MailBoxType.fromValue(mailBoxType), id);
        return "redirect:/mail";
    }

}
