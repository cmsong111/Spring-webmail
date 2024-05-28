package deu.cse.spring_webmail.auth;

/**
 * 로그인 폼 Record 클래스
 *
 * @param username 사용자 아이디
 * @param password 사용자 비밀번호
 */
public record LoginForm(
        /**
         * 사용자 아이디
         */
        String username,
        /**
         * 사용자 비밀번호
         */
        String password
) {
}
