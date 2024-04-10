package deu.cse.spring_webmail.james;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    RestTemplate restTemplate = new RestTemplate();


    @Value("${james.admin.host}")
    String jamesWebAdminUrl;

    @Value("${james.admin.port}")
    Integer jamesWebAdminPort;


    public boolean addUser(String userid, String password) {
        // Body
        String body = getPasswordJson(password);

        // Send request
        HttpEntity<String> request = new HttpEntity<>(body, getHeaders());

        ResponseEntity<String> response = restTemplate.exchange(jamesWebAdminUrl + ":" + jamesWebAdminPort + "/users/" + userid, HttpMethod.PUT, request, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new CustomException("Failed to add user: " + response.getStatusCode());
        }
        return true;
    }

    public boolean changePassword(String userid, String password) {
        // Body
        String body = getPasswordJson(password);

        // Send request
        HttpEntity<String> request = new HttpEntity<>(body, getHeaders());
        ResponseEntity<String> response = restTemplate.exchange(jamesWebAdminUrl + ":" + jamesWebAdminPort + "/users/" + userid + "/password", HttpMethod.PUT, request, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new CustomException("Failed to change password: " + response.getStatusCode());
        }
        return true;
    }

    public boolean deleteUser(String userid) {
        // Send request
        ResponseEntity<String> response = restTemplate.exchange(jamesWebAdminUrl + ":" + jamesWebAdminPort + "/users/" + userid, HttpMethod.DELETE, null, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new CustomException("Failed to delete user: " + response.getStatusCode());
        }
        return true;
    }

    public HealthDto getHealthCheck() {
        // Send request
        ResponseEntity<HealthDto> response = restTemplate.exchange(jamesWebAdminUrl + ":" + jamesWebAdminPort + "/healthcheck", HttpMethod.GET, null, HealthDto.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new CustomException("Failed to get health check: " + response.getStatusCode());
        }
        return response.getBody();
    }


    protected String getPasswordJson(String password) {
        return "{\"password\":\"" + password + "\"}";
    }

    protected HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return headers;
    }


}
