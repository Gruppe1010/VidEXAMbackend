package vibejensen.eksamen.exambackend.models.entities;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="r_numbers")
public class RNumber {

    // attributter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private double number;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date date;


    @NotNull
    @ManyToOne//(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_parish")
    private Parish parish;


    // constructors
    public RNumber(double number) {
        this.number = number;
        this.date = new Date();
    }
    public RNumber(double number, Date date, Parish parish) {
        this.number = number;
        this.date = date;
        this.parish = parish;
    }


}
