package deu.cse.spring_webmail.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    void getAuthority() {
        //given
        Role role = Role.USER;

        //when
        String authority = role.getAuthority();

        //then
        assertEquals("ROLE_USER", authority);
    }
}
