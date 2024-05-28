package deu.cse.spring_webmail.james.dto;

import lombok.Data;

/**
 * Apache James 에서 사용되는 DTO 클래스입니다.
 */
@Data
class Check {
    /**
     * The component name.
     */
    public String componentName;
    /**
     * The escaped component name.
     */
    public String escapedComponentName;
    /**
     * The type.
     */
    public String status;
    /**
     * The cause.
     */
    public Object cause;
}
