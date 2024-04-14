package deu.cse.spring_webmail.mail.service;

import deu.cse.spring_webmail.mail.dto.MailDto;
import deu.cse.spring_webmail.mail.entity.Mail;
import deu.cse.spring_webmail.mail.entity.MailBox;
import deu.cse.spring_webmail.mail.mapper.MailMapper;
import deu.cse.spring_webmail.mail.repository.MailBoxRepository;
import deu.cse.spring_webmail.mail.repository.MailPageableRepository;
import deu.cse.spring_webmail.mail.repository.MailRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMultipart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MailReceiver {

    @Autowired
    public MailReceiver(MailMapper mailMapper, MailRepository mailRepository, MailBoxRepository mailBoxRepository, MailPageableRepository mailPageableRepository) {
        this.mailMapper = mailMapper;
        this.mailRepository = mailRepository;
        this.mailBoxRepository = mailBoxRepository;
        this.mailPageableRepository = mailPageableRepository;
    }

    private final MailMapper mailMapper;
    private final MailRepository mailRepository;
    private final MailBoxRepository mailBoxRepository;
    private final MailPageableRepository mailPageableRepository;

    // file.download_folder = Base directory for storing attachments
    @Value("${file.download_folder}")
    private String downloadFolder;

    /**
     * 사용자 이름으로 메일함을 찾아서 해당 메일함에 있는 모든 메일을 가져옴
     * DTO로 변환하여 반환 (메일 내용과 첨부파일은 제외)
     *
     * @param userName 사용자 아이디
     *                 (메일함은 사용자 이름으로 생성되므로 사용자 이름이 메일함 이름과 동일)
     * @param page     페이지 번호
     * @param size     페이지 크기
     * @return 사용자 메일함에 있는 모든 메일
     */
    public List<MailDto> getMailsByUserName(String userName, int page, int size) {
        // 사용자 이름으로 메일함을 찾음
        MailBox mailBox = mailBoxRepository.findByUserName(userName).orElseThrow(
                () -> new IllegalArgumentException("MailBox not found for userName: " + userName)
        );

        // 메일함에 있는 모든 메일을 가져옴
        List<MailDto> userMails = new ArrayList<>();
        Pageable pageable = PageRequest.of(page - 1, size,Sort.by("mailDate").descending());

        // 사용자 메일함에 존재하는 메일들을 DTO로 변환
        for (Mail mail : mailPageableRepository.findAllByMailbox_MailboxIdAndMailIsDeleted(mailBox.getMailboxId(), false, pageable)) {
            userMails.add(mailMapper.toMailDto(mail));
        }
        return userMails;
    }

    /**
     * 사용자 이름으로 메일함을 찾아서 해당 메일함에 있는 모든 메일의 개수를 가져옴
     *
     * @param userName 사용자 아이디
     * @return 사용자 메일함에 있는 모든 메일의 개수
     */
    public Long countMailsByUserName(String userName) {
        MailBox mailBox = mailBoxRepository.findByUserName(userName).orElseThrow(
                () -> new IllegalArgumentException("MailBox not found for userName: " + userName)
        );
        return mailRepository.countByMailbox_MailboxIdAndMailIsDeleted(mailBox.getMailboxId(), false);
    }


    /**
     * 사용자 이름으로 삭제된 메일함을 찾아서 해당 메일함에 있는 모든 메일을 가져옴
     * DTO로 변환하여 반환 (메일 내용과 첨부파일은 제외)
     *
     * @param userName 사용자 아이디
     *                 (메일함은 사용자 이름으로 생성되므로 사용자 이름이 메일함 이름과 동일)
     * @param page     페이지 번호
     * @param size     페이지 크기
     * @return 사용자 메일함에 있는 모든 메일
     */
    public List<MailDto> getDeletedMailsByUserName(String userName, int page, int size) {
        // 사용자 이름으로 메일함을 찾음
        MailBox mailBox = mailBoxRepository.findByUserName(userName).orElseThrow(
                () -> new IllegalArgumentException("MailBox not found for userName: " + userName)
        );

        // 메일함에 있는 모든 메일을 가져옴
        List<MailDto> userMails = new ArrayList<>();
        Pageable pageable = PageRequest.of(page - 1, size);

        // 사용자 메일함에 존재하는 메일들을 DTO로 변환
        for (Mail mail : mailPageableRepository.findAllByMailbox_MailboxIdAndMailIsDeleted(mailBox.getMailboxId(), true, pageable)) {
            userMails.add(mailMapper.toMailDto(mail));
        }
        return userMails;
    }

    /**
     * 사용자 이름으로 삭제된 메일의 개수를 가져옴
     *
     * @param userName 사용자 아이디
     * @return 사용자 메일함에 있는 모든 메일의 개수
     */
    public Long countDeletedMailsByUserName(String userName) {
        MailBox mailBox = mailBoxRepository.findByUserName(userName).orElseThrow(
                () -> new IllegalArgumentException("MailBox not found for userName: " + userName)
        );
        return mailRepository.countByMailbox_MailboxIdAndMailIsDeleted(mailBox.getMailboxId(), true);
    }

    /**
     * 사용자 이름으로 삭제된 메일 중 읽지 않은 메일의 개수를 가져옴
     * (휴지통에 있는 메일은 읽지 않은 것으로 처리)
     *
     * @param userName 사용자 아이디
     * @return 읽지 않은, 삭제된 메일의 개수
     */
    public Long countUnreadDeletedMailsByUserName(String userName) {
        MailBox mailBox = mailBoxRepository.findByUserName(userName).orElseThrow(
                () -> new IllegalArgumentException("MailBox not found for userName: " + userName)
        );
        return mailRepository.countByMailbox_MailboxIdAndMailIsSeenAndMailIsDeleted(mailBox.getMailboxId(), false, true);
    }

    /**
     * 사용자 이름으로 읽지 않은 메일을 가져옴
     *
     * @param userName 사용자 아이디
     * @param page     페이지 번호
     * @param size     페이지 크기
     * @return 읽지 않은 메일
     */
    public List<MailDto> getUnreadMailsByUserName(String userName, int page, int size) {
        MailBox mailBox = mailBoxRepository.findByUserName(userName).orElseThrow(
                () -> new IllegalArgumentException("MailBox not found for userName: " + userName)
        );

        List<MailDto> userMails = new ArrayList<>();
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("mailDate").descending());

        for (Mail mail : mailPageableRepository.findAllByMailbox_MailboxIdAndMailIsSeenAndMailIsDeleted(mailBox.getMailboxId(), false, false, pageable)) {
            userMails.add(mailMapper.toMailDto(mail));
        }
        return userMails;
    }

    /**
     * 사용자 이름으로 읽지 않은 메일의 개수를 가져옴
     * (휴지통에 있는 메일은 읽지 않은 것으로 처리)
     *
     * @param userName 사용자 아이디
     * @return 읽지 않은 메일의 개수
     */
    public Long countUnreadMailsByUserName(String userName) {
        MailBox mailBox = mailBoxRepository.findByUserName(userName).orElseThrow(
                () -> new IllegalArgumentException("MailBox not found for userName: " + userName)
        );
        return mailRepository.countByMailbox_MailboxIdAndMailIsSeenAndMailIsDeleted(mailBox.getMailboxId(), false, false);
    }

    /**
     * 메일을 삭제함
     *
     * @param id 메일 UID
     */
    public void deleteMail(Long id) {
        Mail mail = mailRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Mail not found for id: " + id)
        );
        mail.setMailIsDeleted(true);
        mailRepository.save(mail);
    }

    public void restoreMail(Long id) {
        Mail mail = mailRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Mail not found for id: " + id)
        );
        mail.setMailIsDeleted(false);
        mailRepository.save(mail);
    }


    /**
     * 메일 UID를 통해 메일을 가져옴 - 메일 내용과 첨부파일을 포함
     * (메일을 가져오면 메일을 읽은 것으로 처리)
     *
     * @param id 메일 UID
     * @return 메일 DTO
     */
    public MailDto getMail(Long id) {
        Mail mail = mailRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Mail not found for id: " + id)
        );
        // 메일을 읽은 것으로 처리
        mail.setMailIsSeen(true);
        mailRepository.save(mail);

        try {
            // 메일 Headers와 Body를 합쳐서 Blob으로 반환
            mail.setHeaderBytes(mergeBlobs(mail.getHeaderBytes(), mail.getMailBytes()));
            // 메일 DTO로 변환
            MailDto mailDto = mailMapper.toMailDto(mail);
            // 메일 내용과 첨부파일을 추출
            mailDto.setMailContent(extractMailContent(extractMailContent(mailDto.getMimeMessage().getContent())));
            mailDto.setAttachments(extractAttachments(mailDto.getMimeMessage().getContent(), mailDto.getMailUid()));
            return mailDto;
        } catch (MessagingException | IOException | SQLException e) {
            log.error("Error while extracting mail content or attachments", e);
        }
        return null;
    }

    /**
     * 임시 디렉토리에 저장된 첨부파일을 다운로드
     *
     * @param id       메일 UID
     * @param filename 첨부파일 이름
     */
    public Resource downloadAttachment(Long id, String filename) {
        try {
            File file = new File(downloadFolder + File.separator + id + File.separator + filename);
            Resource resource = new UrlResource(file.toURI());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
        } catch (IOException e) {
            log.error("Error while downloading attachment", e);
        }
        return null;
    }

    /**
     * 메일 Headers와 Body를 합쳐서 Blob으로 반환
     *
     * @param mailHeaders 메일 Headers
     * @param MailBytes   메일 Body
     * @return 합쳐진 Blob
     * @throws SQLException SQL 예외
     */
    protected Blob mergeBlobs(Blob mailHeaders, Blob MailBytes) throws SQLException {
        byte[] mailHeadersBytes = mailHeaders.getBytes(1, (int) mailHeaders.length());
        byte[] mailBodyBytes = MailBytes.getBytes(1, (int) MailBytes.length());

        byte[] mergedBytes = new byte[mailHeadersBytes.length + mailBodyBytes.length];
        System.arraycopy(mailHeadersBytes, 0, mergedBytes, 0, mailHeadersBytes.length);
        System.arraycopy(mailBodyBytes, 0, mergedBytes, mailHeadersBytes.length, mailBodyBytes.length);

        return new javax.sql.rowset.serial.SerialBlob(mergedBytes);
    }

    /**
     * 메일 Contents에서 내용만 추출
     * (첨부파일은 제외)
     *
     * @param mailContents 메일 Contents
     */
    protected String extractMailContent(Object mailContents) throws MessagingException, IOException {
        // 본문이 Multipart인 경우
        if (mailContents instanceof MimeMultipart multipart) {
            // Text인 본문을 찾아서 반환
            for (int i = 0; i < multipart.getCount(); i++) {
                MimeBodyPart bodyPart = (MimeBodyPart) multipart.getBodyPart(i);
                if (bodyPart.getContentType().startsWith("text")) {
                    return bodyPart.getContent().toString();
                }
            }
        }
        // 본문이 단일인 경우
        return mailContents.toString();
    }

    /**
     * 메일 Content에서 첨부파일 이름만 추출
     * 파일은 서버의 임시 디렉토리에 저장
     * (mailUid/fileName)
     *
     * @param mailContents 메일 Contents
     */
    protected List<String> extractAttachments(Object mailContents, Long mailUid) throws MessagingException, IOException {
        log.info("Extracting attachments for mail UID: {}", mailUid);
        List<String> attachments = new ArrayList<>();
        // 본문이 Multipart인 경우
        if (mailContents instanceof MimeMultipart multipart) {
            // 첨부파일을 찾아서 반환
            for (int i = 0; i < multipart.getCount(); i++) {
                MimeBodyPart bodyPart = (MimeBodyPart) multipart.getBodyPart(i);
                if (!bodyPart.getContentType().startsWith("text")) {
                    attachments.add(bodyPart.getFileName());

                    // 첨부파일을 서버의 임시 디렉토리에 저장
                    File attachment = new File(downloadFolder + File.separator + mailUid + File.separator + bodyPart.getFileName());
                    attachment.getParentFile().mkdirs();
                    bodyPart.saveFile(attachment);
                    log.info("Attachment saved to: {}", attachment.getAbsolutePath());
                }
            }
        }
        return attachments;
    }
}
