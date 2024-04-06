package deu.cse.spring_webmail.auth;

import deu.cse.spring_webmail.user.User;

import java.util.List;

public interface AuthService {

    /**
     * 아이디 사용 가능 여부 확인 메소드
     *
     * @param userid 사용자 아이디
     * @return 아이디 사용 가능 여부
     */

    boolean isAvailableUserId(String userid);

    /**
     * 사용자 추가 메소드
     *
     * @param userid   사용자 아이디
     * @param password 사용자 비밀번호
     * @return 추가된 사용자 정보
     */
    User addUser(String userid, String password);

    /**
     * 사용자 인증 메소드
     *
     * @param userid   사용자 아이디
     * @param password 사용자 비밀번호
     * @return 인증된 사용자 정보
     */
    User authenticate(String userid, String password);

    /**
     * 사용자 삭제 메소드
     *
     * @param userid 사용자 아이디
     * @return 사용자 삭제 성공 여부
     */
    boolean deleteUser(String userid);

    /**
     * 사용자 비밀번호 변경 메소드
     *
     * @param userid      사용자 아이디
     * @param oldPassword 사용자 이전 비밀번호
     * @param newPassword 사용자 새 비밀번호
     * @return 비밀번호 변경된 사용자 정보
     */
    User changePassword(String userid, String oldPassword, String newPassword);

    /**
     * 사용자 목록 조회 메소드
     *
     * @return 사용자 목록
     */
    List<User> getUserList();
}
