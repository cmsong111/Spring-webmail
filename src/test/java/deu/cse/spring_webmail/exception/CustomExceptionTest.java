package deu.cse.spring_webmail.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomExceptionTest {

    @Test
    void testCustomException() {
        CustomException customException = new CustomException("test");
        assertEquals("test", customException.getMessage());
    }

}
