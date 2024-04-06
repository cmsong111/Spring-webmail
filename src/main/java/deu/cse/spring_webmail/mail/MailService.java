package deu.cse.spring_webmail.mail;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MailService {
    private final MailMapper mailMapper;

    MailRepository mailRepository;
    MailBoxRepository mailBoxRepository;

    public List<MailDto> getMailsByUserName(String userName) {
        MailBox mailBox = getMailBoxByUserName(userName);
        if (mailBox == null) {
            return null;
        }

        return getMailsByMailBoxId(mailBox.getMailboxId()).stream().map(mailMapper::toDto)
                .toList();
    }

    public MailDto getMail(Long id) {
        return mailMapper.toDto(mailRepository.findById(id).orElse(null));
    }

    public MailBox getMailBoxByUserName(String userName) {
        return mailBoxRepository.findByUserName(userName).orElse(null);
    }

    List<Mail> getMailsByMailBoxId(Long mailBoxId) {
        return mailRepository.findAllByMailbox_MailboxId(mailBoxId);
    }
}
