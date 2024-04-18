package deu.cse.spring_webmail.james;

import deu.cse.spring_webmail.exception.CustomException;
import deu.cse.spring_webmail.james.dto.HealthDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class JamesAdminHealth {
    RestTemplate restTemplate = new RestTemplate();


    @Value("${james.admin.host}")
    String jamesWebAdminUrl;

    @Value("${james.admin.port}")
    Integer jamesWebAdminPort;

    /**
     * Check all components
     * <p>
     * This endpoint is simple for now and is just returning the http status code corresponding to the state of checks (see below). The user has to check in the logs in order to have more information about failing checks.
     * <p>
     */
    public HealthDto checkAllComponents() {
        // Send request
        ResponseEntity<HealthDto> response = restTemplate.getForEntity(jamesWebAdminUrl + ":" + jamesWebAdminPort + "/healthcheck", HealthDto.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new CustomException("Failed to get health check: " + response.getStatusCode());
        }
        return response.getBody();
    }


    /**
     * This Api lists all the available health checks.
     *
     * @return HealthDto
     */
    public HealthDto allHealthCheck() {
        ResponseEntity<HealthDto> response = restTemplate.getForEntity(jamesWebAdminUrl + ":" + jamesWebAdminPort + "/healthcheck/checks", HealthDto.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new CustomException("Failed to get health check: " + response.getStatusCode());
        }

        return response.getBody();
    }
}
