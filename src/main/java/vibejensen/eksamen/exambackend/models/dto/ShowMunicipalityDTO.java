package vibejensen.eksamen.exambackend.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShowMunicipalityDTO {
    private int code;
    private String name;
    private double currentRNumber;

    @Override
    public String toString() {
        return "ShowMunicipalityDTO{" +
                "code=" + code +
                ", name='" + name + '\'' +
                ", currentRNumber=" + currentRNumber +
                '}';
    }
}
