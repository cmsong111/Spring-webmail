package deu.cse.spring_webmail.mail.mapper;


import deu.cse.spring_webmail.mail.dto.MailDto;
import deu.cse.spring_webmail.mail.entity.Mail;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 매일 엔티티와 매일 DTO 사이의 매핑을 처리하는 매퍼 인터페이스
 */
@Mapper(componentModel = "spring")
public interface MailMapper {


    /**
     * 매일 엔티티를 매일 DTO로 변환
     * 메일 내용과 첨부파일은 제외므로 별도로 처리해야함
     *
     * @param mail 매일 엔티티
     * @return 매일 DTO
     */
    @Mapping(target = "mailContent", ignore = true)
    @Mapping(target = "attachments", ignore = true)
    @Mapping(target = "mailUid", source = "mailKey.mailUid")
    @Mapping(target = "mailboxMailboxId", source = "mailKey.mailboxMailboxId")
    @Mapping(target = "mimeMessage", source = "headerBytes", qualifiedByName = "blobToMessage")
    MailDto toMailDto(Mail mail);


    /**
     * Blob을 MimeMessage로 변환
     *
     * @param blob Blob 객체
     * @return MimeMessage 객체 반환
     * @throws SQLException       SQL 예외
     * @throws MessagingException 메시징 예외
     */
    @Named("blobToMessage")
    default MimeMessage blobToMessage(Blob blob) throws SQLException, MessagingException {
        Session session = Session.getInstance(new Properties());
        InputStream inputStream = blob.getBinaryStream();
        return new MimeMessage(session, inputStream);
    }
}
