package deu.cse.spring_webmail.mail.dto;

import lombok.Getter;

@Getter
public enum MailBoxType {
    INBOX(1, "INBOX", "받은 편지함"),
    SENT(2, "Sent", "보낸 편지함"),
    DRAFT(3, "Draft", "임시 보관함"),
    TRASH(4, "Trash", "휴지통");

    private final int value;
    private final String mailBoxName;
    private final String description;

    MailBoxType(int value, String mailBoxName, String description) {
        this.value = value;
        this.mailBoxName = mailBoxName;
        this.description = description;
    }

    public static MailBoxType fromValue(int value) {
        for (MailBoxType type : MailBoxType.values()) {
            if (type.value == value) {
                return type;
            }
        }
        return INBOX;
    }
}
