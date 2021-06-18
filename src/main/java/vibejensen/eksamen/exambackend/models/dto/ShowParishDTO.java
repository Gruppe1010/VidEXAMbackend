package vibejensen.eksamen.exambackend.models.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShowParishDTO {

    private int id;

    private int code;

    private String name;

    private int municipalityCode;

    @JsonProperty
    private boolean isOnLockdown;

    private double currentRNumber;



}
