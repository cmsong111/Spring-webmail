package deu.cse.spring_webmail.auth;

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
public class JamesWebAdmin {

    @Value("${james.admin.host}")
    private String JamesWebAdminUrl;

    @Value("${james.admin.port}")
    private Integer JamesWebAdminPort;

    public boolean addUser(String userid, String password) {
        RestTemplate restTemplate = new RestTemplate();

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        // URL
        String url = "http://" + JamesWebAdminUrl + ":" + JamesWebAdminPort + "/users/" + userid;

        // Body
        String body = "{\"password\":\"" + password + "\"}";

        // Send request
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to add user: " + response.getStatusCode());
        }
        return true;
    }

    public boolean changePassword(String userid, String password) {
        RestTemplate restTemplate = new RestTemplate();

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        // URL
        String url = "http://" + JamesWebAdminUrl + ":" + JamesWebAdminPort + "/users/" + userid + "?force";

        // Body
        String body = "{\"password\":\"" + password + "\"}";

        // Send request
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to change password: " + response.getStatusCode());
        }
        return true;
    }

    public boolean deleteUser(String userid) {
        RestTemplate restTemplate = new RestTemplate();

        // URL
        String url = "http://" + JamesWebAdminUrl + ":" + JamesWebAdminPort + "/users/" + userid;

        // Send request
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to delete user: " + response.getStatusCode());
        }
        return true;
    }
}
