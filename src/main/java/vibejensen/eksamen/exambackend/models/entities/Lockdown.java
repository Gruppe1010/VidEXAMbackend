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
@AllArgsConstructor
@Table(name="lockdowns")
public class Lockdown  implements Comparable<Lockdown>{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @ManyToOne //(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_parish")
    private Parish lockedDownParish;
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.DATE)
    private Date endDate;

    // constructor
    public Lockdown() {
        this.startDate = new Date();
    }

    public Lockdown(Parish parish) {
        this.lockedDownParish = parish;
        this.startDate = new Date();
    }

    @Override
    public String toString() {
        return "Lockdown{" +
                "id=" + id +
                ", lockedDownParish=" + lockedDownParish.getId() +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    @Override
    public int compareTo(Lockdown o) {
        return this.startDate.compareTo(o.startDate);
    }
}
