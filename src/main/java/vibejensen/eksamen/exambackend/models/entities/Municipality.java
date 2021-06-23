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
    // {...} // andre attributter

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // @NotNull // bruges hvis vi IKKE genererer db automatisk
    @Column(unique = true, nullable = false) // bruges hvis vi genererer automatisk
    private int code;

    @Column(unique = true, nullable = false)
    private String name;

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
