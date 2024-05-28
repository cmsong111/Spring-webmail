package deu.cse.spring_webmail.user;

import org.springframework.security.core.GrantedAuthority;

/**
 * 사용자 권한 Enum
 */
public enum Role implements GrantedAuthority {
    /**
     * 사용자
     */
    USER,
    /**
     * 관리자
     */
    ADMIN;


    /**
     * 권한을 반환합니다. (ROLE_ 접두사를 붙여서 반환합니다.)
     *
     * @return 권한
     */
    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
