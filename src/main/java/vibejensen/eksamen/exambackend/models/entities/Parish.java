package vibejensen.eksamen.exambackend.models.entities;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vibejensen.eksamen.exambackend.models.dto.ShowParishDTO;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="parishes")
public class Parish{


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
    @ManyToOne
    @JoinColumn(name = "id_municipality")
    private Municipality municipality;
    @NotNull
    private boolean isOnLockdown;
    @OneToMany(mappedBy = "lockedDownParish", cascade = CascadeType.ALL)
    private List<Lockdown> lockdowns;
    @NotNull
    @OneToMany(mappedBy = "parish", cascade = CascadeType.ALL)
    private List<RNumber> rNumbers;

    // constructor
    public Parish(int code, String name, Municipality municipality, boolean isOnLockdown, List<Lockdown> lockdowns, List<RNumber> rNumbers) {
        this.code = code;
        this.name = name;
        this.municipality = municipality;
        this.isOnLockdown = isOnLockdown;
        this.lockdowns = lockdowns;
        this.rNumbers = rNumbers;
    }

    // andre metoder
    public ShowParishDTO convertToShowParishDTO(double currentRNumber){
        return new ShowParishDTO(id, code, name, municipality.getCode(), isOnLockdown, currentRNumber);
    }

    public void setLastLockdownsEndDate(){
        Collections.sort(lockdowns);

        lockdowns.get(lockdowns.size()-1).setEndDate(new Date());
        System.out.println(lockdowns);
    }
    public void addRNumber(double rNumber){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date date = new Date();
        String currentDate = dateFormat.format(date);

        boolean didNotAddRNumber = true;
        if(rNumbers != null){
            for(RNumber r : rNumbers){
                String rDate = dateFormat.format(r.getDate());
                // hvis der findes denne date på listen
                if(rDate.equals(currentDate)){
                    didNotAddRNumber = false;
                    // erstat dens smittetal med det nye fra formen
                    r.setNumber(rNumber);
                    r.setParish(this);
                    System.out.println(r.getNumber());
                }
            }
            if(didNotAddRNumber){
                rNumbers.add(new RNumber(rNumber, date, this));
            }
        }else{
            rNumbers = new ArrayList<>(Arrays.asList(new RNumber(rNumber, date, this)));
        }


    }


    // toString
    @Override
    public String toString() {
        return "Parish{" +
                "id=" + id +
                ", code=" + code +
                ", name='" + name + '\'' +
                ", rNumbers=" + rNumbers +
                '}';
    }

    // !ManyToOne
    /*
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_entity1")
    private Entity1 entity1;
    * */


    // ! ManyToMany
    // én entity1 kan have mange entity2 + én entity2 kan have mange entity1
    /*
    @NotNull
    @ManyToMany(mappedBy = "entity1s") // navn på attribut i Entity2-klassen
    private Set<Entity2> entity2s;
     */

    // ! OneToMany
    // én entity1 kan have mange entity2 - én entity2 kan kun tilhøre én entity1
    /*
    @OneToMany(mappedBy = "entity1", cascade = CascadeType.ALL)
    private Set<Entity2> entity2s;
     */






}
