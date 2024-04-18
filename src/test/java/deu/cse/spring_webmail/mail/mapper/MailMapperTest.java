package deu.cse.spring_webmail.mail.mapper;

import deu.cse.spring_webmail.mail.dto.MailBoxType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        MailMapper.class,
        MailMapperImpl.class
})
class MailMapperTest {

}
