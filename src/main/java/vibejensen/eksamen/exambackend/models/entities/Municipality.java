package vibejensen.eksamen.exambackend.models.entities;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vibejensen.eksamen.exambackend.models.dto.ShowMunicipalityDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="municipalities")
public class Municipality {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(unique = true)
    private int code;

    @NotNull
    @Column(unique = true)
    private String name;

    @NotNull
    @OneToMany(mappedBy = "municipality", cascade = CascadeType.ALL)
    private List<Parish> parishes;

    @Override
    public String toString() {
        return "Municipality{" +
                "id=" + id +
                ", code=" + code +
                ", name='" + name + '\'' +
                '}';
    }


    public ShowMunicipalityDTO convertToShowMunicipalitiesDTO(double currentRNumber){

        return new ShowMunicipalityDTO(code, name, currentRNumber);
    }

}
