package deu.cse.spring_webmail.mail.dto;

import lombok.Getter;

/**
 * 메일함 타입 Enum
 */
@Getter
public enum MailBoxType {
    /**
     * 받은 편지함
     */
    INBOX(1, "INBOX", "받은 편지함"),
    /**
     * 보낸 편지함
     */
    SENT(2, "Sent", "보낸 편지함"),
    /**
     * 임시 보관함
     */
    DRAFT(3, "Draft", "임시 보관함"),
    /**
     * 휴지통
     */
    TRASH(4, "Trash", "휴지통");

    /**
     * 메일함 번호
     */
    private final int value;
    /**
     * 메일함 이름
     */
    private final String mailBoxName;
    /**
     * 메일함 설명
     */
    private final String description;

    /**
     * 생성자
     *
     * @param value       메일함 번호
     * @param mailBoxName 메일함 이름
     * @param description 메일함 설명
     */
    MailBoxType(int value, String mailBoxName, String description) {
        this.value = value;
        this.mailBoxName = mailBoxName;
        this.description = description;
    }

    /**
     * 메일함 번호로부터 해당 Enum 객체를 반환합니다.
     *
     * @param value 메일함 번호
     * @return 메일함 타입
     */
    public static MailBoxType fromValue(int value) {
        for (MailBoxType type : MailBoxType.values()) {
            if (type.value == value) {
                return type;
            }
        }
        return INBOX;
    }
}
