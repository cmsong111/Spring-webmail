package deu.cse.spring_webmail;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * WAS 배포를 위한 ServletInitializer 클래스
 *
 * <p>
 * SpringBootServletInitializer 클래스를 상속받아 configure 메소드를 오버라이딩하여 WAS 배포를 위한 설정을 추가합니다.
 * </p>
 */
public class ServletInitializer extends SpringBootServletInitializer {

    /**
     * SpringApplicationBuilder를 사용하여 SpringBootServletInitializer 설정을 추가합니다.
     *
     * @param application SpringApplicationBuilder 객체
     * @return SpringApplicationBuilder 객체
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringWebmailApplication.class);
    }

}
