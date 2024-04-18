package deu.cse.spring_webmail.james;

import deu.cse.spring_webmail.james.dto.HealthDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JamesAdminHealthTest {

    @InjectMocks
    JamesAdminHealth jamesAdminHealth;

    @Mock
    RestTemplate restTemplate;

    @Test
    @DisplayName("모든 컴포넌트 체크")
    void checkAllComponents() {
        // Given
        HealthDto healthDto = new HealthDto();
        when(restTemplate.getForEntity(anyString(), eq(HealthDto.class))).thenReturn(ResponseEntity.ok(healthDto));

        // When
        HealthDto result = jamesAdminHealth.checkAllComponents();

        // Then
        assertNotNull(result);
    }

    @Test
    @DisplayName("모든 컴포넌트 체크 - 실패")
    void checkAllComponentsFail() {
        // Given
        when(restTemplate.getForEntity(anyString(), eq(HealthDto.class))).thenReturn(ResponseEntity.badRequest().build());

        // When & Then
        assertThrows(RuntimeException.class, () -> jamesAdminHealth.checkAllComponents());
    }

    @Test
    @DisplayName("모든 헬스체크")
    void allHealthCheck() {
        // Given
        HealthDto healthDto = new HealthDto();
        when(restTemplate.getForEntity(anyString(), eq(HealthDto.class))).thenReturn(ResponseEntity.ok(healthDto));

        // When
        HealthDto result = jamesAdminHealth.allHealthCheck();

        // Then
        assertNotNull(result);
    }

    @Test
    @DisplayName("모든 헬스체크 - 실패")
    void allHealthCheckFail() {
        // Given
        when(restTemplate.getForEntity(anyString(), eq(HealthDto.class))).thenReturn(ResponseEntity.badRequest().build());

        // When & Then
        assertThrows(RuntimeException.class, () -> jamesAdminHealth.allHealthCheck());
    }
}
