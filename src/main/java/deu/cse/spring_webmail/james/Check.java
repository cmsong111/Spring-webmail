package deu.cse.spring_webmail.james;

import lombok.Data;

@Data
class Check {
    public String componentName;
    public String escapedComponentName;
    public String status;
    public Object cause;
}
