package vibejensen.eksamen.exambackend.models.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vibejensen.eksamen.exambackend.models.entities.Lockdown;
import vibejensen.eksamen.exambackend.models.entities.Municipality;
import vibejensen.eksamen.exambackend.models.entities.Parish;
import vibejensen.eksamen.exambackend.models.entities.RNumber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateParishDTO {

    @JsonProperty
    private boolean isOnLockdown;
    private int code;
    private String name;
    private double currentRNumber;
    private int municipalityCode;


    // bruges ved createParish
    public Parish convertToParish(Municipality municipality){

        // hvis den er on lockdown tilføjes lockdown-obj til liste
        List<Lockdown> lockdowns = null;
        if(isOnLockdown){
            lockdowns = new ArrayList<>(Arrays.asList(new Lockdown()));
        }

        List<RNumber> rNumbers = new ArrayList<>(Arrays.asList(new RNumber(currentRNumber)));


        return new Parish(code, name, municipality, isOnLockdown, lockdowns, rNumbers);
    }

    @Override
    public String toString() {
        return "CreateParishDTO{" +
                "isOnLockdown=" + isOnLockdown +
                ", code=" + code +
                ", name='" + name + '\'' +
                ", currentRNumber=" + currentRNumber +
                ", municipalityCode=" + municipalityCode +
                '}';
    }
}
