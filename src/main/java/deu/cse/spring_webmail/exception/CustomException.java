package deu.cse.spring_webmail.exception;

/**
 * 사용자 정의 예외 클래스
 */
public class CustomException extends RuntimeException {
    /**
     * 생성자
     *
     * @param message 예외 메시지
     */
    public CustomException(String message) {
        super(message);
    }
}
