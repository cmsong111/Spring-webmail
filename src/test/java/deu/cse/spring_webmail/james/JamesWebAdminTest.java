package deu.cse.spring_webmail.james;

import deu.cse.spring_webmail.james.dto.HealthDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JamesWebAdminTest {

    @Autowired
    JamesHealthCheck jamesWebAdmin;

    @Test
    @Disabled
    void getHealthCheck() {
        HealthDto healthCheck = jamesWebAdmin.checkAllComponents();
        System.out.println(healthCheck);
    }
}
