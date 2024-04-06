package deu.cse.spring_webmail.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Properties;

@Mapper(componentModel = "spring")
public interface MailMapper {

    @Mapping(target = "mimeMessage", source = "mailBytes", qualifiedByName = "blobToString")
    @Mapping(target = "headerBytes", source = "headerBytes", qualifiedByName = "blobToMessage")
    MailDto toDto(Mail mail);

    @Named("blobToMessage")
    default MimeMessage blobToMessage(Blob blob) throws SQLException, MessagingException, IOException {
        Session session = Session.getInstance(new Properties());
        InputStream inputStream = blob.getBinaryStream();
        return new MimeMessage(session, inputStream);
    }

    @Named("blobToString")
    default String blobToString(Blob blob) throws SQLException {
        return new String(blob.getBytes(1, (int) blob.length()));
    }
}
