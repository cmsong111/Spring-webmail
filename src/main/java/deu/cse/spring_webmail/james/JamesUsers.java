package deu.cse.spring_webmail.james;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class JamesUsers {

    RestTemplate restTemplate = new RestTemplate();


    @Value("${james.admin.host}")
    String jamesWebAdminUrl;

    @Value("${james.admin.port}")
    Integer jamesWebAdminPort;

    /**
     * 사용자 생성
     *
     * @param userid   사용자 아이디
     * @param password 사용자 비밀번호
     * @return 사용자 생성 성공 여부
     */
    public boolean createUser(String userid, String password) {
        String body = getPasswordJson(password);
        HttpEntity<String> request = new HttpEntity<>(body, getHeaders());
        ResponseEntity<String> response = restTemplate.exchange(jamesWebAdminUrl + ":" + jamesWebAdminPort + "/users/" + userid, HttpMethod.PUT, request, String.class);
        return response.getStatusCode().is2xxSuccessful();
    }

    /**
     * 유저 존재 여부 확인
     *
     * @param userid 사용자 아이디
     * @return 사용자 존재 여부 (true: 존재, false: 미존재)
     */
    public boolean testUserExist(String userid) {
        ResponseEntity<String> response = restTemplate.exchange(jamesWebAdminUrl + ":" + jamesWebAdminPort + "/users/" + userid, HttpMethod.HEAD, null, String.class);
        return response.getStatusCode().is2xxSuccessful();
    }

    /**
     * 비밀번호 변경 요청
     *
     * @param userid   사용자 아이디
     * @param password 변경할 비밀번호
     *                 Spring Security를 통해 비밀번호를 암호화하고 전달해야 함
     * @return 비밀번호 변경 성공 여부
     */
    public boolean changePassword(String userid, String password) {
        String body = getPasswordJson(password);
        HttpEntity<String> request = new HttpEntity<>(body, getHeaders());
        ResponseEntity<String> response = restTemplate.exchange(jamesWebAdminUrl + ":" + jamesWebAdminPort + "/users/" + userid + "/password", HttpMethod.PUT, request, String.class);
        return response.getStatusCode().is2xxSuccessful();
    }

    /**
     * 사용자 삭제
     *
     * @param userid 삭제할 사용자 아이디
     * @return 사용자 삭제 성공 여부(true: 성공, false: 실패)
     */
    public boolean deleteUser(String userid) {
        ResponseEntity<String> response = restTemplate.exchange(jamesWebAdminUrl + ":" + jamesWebAdminPort + "/users/" + userid, HttpMethod.DELETE, null, String.class);
        return response.getStatusCode().is2xxSuccessful();
    }


    /**
     * 비밀번호를 Json 형태로 변환
     *
     * @param password 비밀번호
     * @return Json 형태의 비밀번호
     */
    protected String getPasswordJson(String password) {
        return "{\"password\":\"" + password + "\"}";
    }

    /**
     * 헤더 생성
     *
     * @return HttpHeaders
     */
    protected HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return headers;
    }
}
