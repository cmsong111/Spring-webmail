package deu.cse.spring_webmail.james.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class HealthDto {
    public String status;
    public ArrayList<Check> checks;
}
