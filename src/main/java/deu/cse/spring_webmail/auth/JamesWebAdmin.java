package deu.cse.spring_webmail.auth;

import deu.cse.spring_webmail.exception.CustomException;
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

    RestTemplate restTemplate;
    HttpHeaders headers;
    String url;

    public JamesWebAdmin(@Value("${james.admin.host}") String jamesWebAdminUrl, @Value("${james.admin.port}") Integer jamesWebAdminPort) {
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        url = "http://" + jamesWebAdminUrl + ":" + jamesWebAdminPort + "/users/%s";
    }

    public boolean addUser(String userid, String password) {
        // Body
        String body = "{\"password\":\"" + password + "\"}";

        // Send request
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(String.format(url, userid), HttpMethod.PUT, request, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new CustomException("Failed to add user: " + response.getStatusCode());
        }
        return true;
    }

    public boolean changePassword(String userid, String password) {
        // Body
        String body = "{\"password\":\"" + password + "\"}";

        // Send request
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(String.format(url, userid), HttpMethod.PUT, request, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new CustomException("Failed to change password: " + response.getStatusCode());
        }
        return true;
    }

    public boolean deleteUser(String userid) {
        // Send request
        ResponseEntity<String> response = restTemplate.exchange(String.format(url, userid), HttpMethod.DELETE, null, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new CustomException("Failed to delete user: " + response.getStatusCode());
        }
        return true;
    }
}
