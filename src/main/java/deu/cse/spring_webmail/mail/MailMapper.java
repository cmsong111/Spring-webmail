package deu.cse.spring_webmail.mail;


import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.apache.commons.mail.util.MimeMessageParser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Properties;

@Mapper(componentModel = "spring")
public interface MailMapper {


    @Mapping(target = "mailboxId", source = "mailbox.mailboxId")
    @Mapping(target = "message", source = "headerBytes", qualifiedByName = "blobToMessage")
    MailHeader toMailHeader(Mail mail);


    @Named("blobToMessage")
    default MimeMessage blobToMessage(Blob blob) throws SQLException, MessagingException {
        Session session = Session.getInstance(new Properties());
        InputStream inputStream = blob.getBinaryStream();
        return new MimeMessage(session, inputStream);
    }
}
