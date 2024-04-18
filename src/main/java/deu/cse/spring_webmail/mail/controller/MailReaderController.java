package deu.cse.spring_webmail.mail.controller;

import deu.cse.spring_webmail.mail.dto.MailBoxType;
import deu.cse.spring_webmail.mail.service.MailReceiver;
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
import java.security.Principal;

@Controller
@AllArgsConstructor
@RequestMapping("/mail/{mailBoxType}")
public class MailReaderController {

    private final MailReceiver mailReceiver;


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
     * 첨부파일 다운로드
     *
     * @param mailBoxType 메일함 유형
     * @param mailId    메일 아이디
     * @param filename  파일 이름
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
            @PathVariable("mailBoxId") Long mailBoxId,
            @PathVariable("id") Long id,
            Principal principal) {
        mailReceiver.deleteMail(mailBoxId, id);
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
            @PathVariable("mailBoxId") Long mailBoxId,
            @PathVariable("id") Long id) {
        mailReceiver.restoreMail(mailBoxId, id);
        return "redirect:/mail/deleted";
    }

}
