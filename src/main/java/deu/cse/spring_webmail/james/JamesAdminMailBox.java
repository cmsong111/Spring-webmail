package deu.cse.spring_webmail.james;

import deu.cse.spring_webmail.james.dto.JamesMailBoxDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class JamesAdminMailBox {
    RestTemplate restTemplate = new RestTemplate();


    @Value("${james.admin.host}")
    String jamesWebAdminUrl;

    @Value("${james.admin.port}")
    Integer jamesWebAdminPort;


    /**
     * 사용자 메일박스 생성
     *
     * @param usernameToBeUsed       신청자 아이디
     * @param mailboxNameToBeCreated 생성할 메일박스 이름
     * @return 사용자 메일박스 생성 성공 여부
     */
    public boolean createMailBox(String usernameToBeUsed, String mailboxNameToBeCreated) {
        ResponseEntity<String> response = restTemplate.exchange(jamesWebAdminUrl + ":" + jamesWebAdminPort + "/users/" + usernameToBeUsed + "/mailboxes/" + mailboxNameToBeCreated, HttpMethod.PUT, null, String.class);
        return response.getStatusCode().is2xxSuccessful();
    }

    /**
     * 사용자 메일 박스 삭제
     *
     * @param usernameToBeUsed       신청자 아이디
     * @param mailboxNameToBeDeleted 삭제할 메일박스 이름
     * @return 사용자 메일박스 삭제 성공 여부
     */
    public boolean deleteMailBox(String usernameToBeUsed, String mailboxNameToBeDeleted) {
        ResponseEntity<String> response = restTemplate.exchange(jamesWebAdminUrl + ":" + jamesWebAdminPort + "/users/" + usernameToBeUsed + "/mailboxes/" + mailboxNameToBeDeleted, HttpMethod.DELETE, null, String.class);
        return response.getStatusCode().is2xxSuccessful();
    }

    /**
     * 사용자 메일박스 존재 여부 확인
     *
     * @param usernameToBeUsed       신청자 아이디
     * @param mailboxNameToBeChecked 확인할 메일박스 이름
     * @return 사용자 메일박스 존재 여부 (true: 존재, false: 미존재)
     */
    public boolean testMailBoxExist(String usernameToBeUsed, String mailboxNameToBeChecked) {
        ResponseEntity<String> response = restTemplate.exchange(jamesWebAdminUrl + ":" + jamesWebAdminPort + "/users/" + usernameToBeUsed + "/mailboxes/" + mailboxNameToBeChecked, HttpMethod.HEAD, null, String.class);
        return response.getStatusCode().is2xxSuccessful();
    }

    /**
     * 사용자 메일박스 리스트 조회
     *
     * @param usernameToBeUsed 신청자 아이디
     * @return 사용자 메일박스 리스트
     */
    public JamesMailBoxDto[] getMailBoxList(String usernameToBeUsed) {
        ResponseEntity<JamesMailBoxDto[]> response = restTemplate.getForEntity(jamesWebAdminUrl + ":" + jamesWebAdminPort + "/users/" + usernameToBeUsed + "/mailboxes", JamesMailBoxDto[].class);
        return response.getBody();
    }

    /**
     * 메일함에 포함된 메일 개수 조회
     *
     * @param usernameToBeUsed 신청자 아이디
     * @param mailboxName      확인할 메일박스 이름
     *                         (ex. INBOX, Outbox, Sent, Draft, Trash, ...)
     * @return 메일 개수 (0 이상의 정수 반환, 조회 실패 시 -1 반환)
     */
    public Integer getMailCount(String usernameToBeUsed, String mailboxName) {
        ResponseEntity<Integer> response = restTemplate.getForEntity(jamesWebAdminUrl + ":" + jamesWebAdminPort + "/users/" + usernameToBeUsed + "/mailboxes/" + mailboxName + "/messageCount", Integer.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            return -1;
        }
    }

    /**
     * 메일함에 포함된 읽지 않은 메일 개수 조회
     *
     * @param usernameToBeUsed 신청자 아이디
     * @param mailboxName      확인할 메일박스 이름
     *                         (ex. INBOX, Outbox, Sent, Draft, Trash, ...)
     * @return 읽지 않은 메일 개수 (0 이상의 정수, 조회 실패 시 -1 반환)
     */
    public Integer getUnseenMailCount(String usernameToBeUsed, String mailboxName) {
        ResponseEntity<Integer> response = restTemplate.getForEntity(jamesWebAdminUrl + ":" + jamesWebAdminPort + "/users/" + usernameToBeUsed + "/mailboxes/" + mailboxName + "/unseenMessageCount", Integer.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            return -1;
        }
    }
}
