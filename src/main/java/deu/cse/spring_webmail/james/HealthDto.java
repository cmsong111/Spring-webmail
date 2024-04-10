package deu.cse.spring_webmail.james;

import lombok.Data;

import java.util.ArrayList;

@Data
public class HealthDto {
    public String status;
    public ArrayList<Check> checks;
}
