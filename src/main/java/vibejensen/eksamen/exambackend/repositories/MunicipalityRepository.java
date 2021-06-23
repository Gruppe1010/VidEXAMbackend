package vibejensen.eksamen.exambackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vibejensen.eksamen.exambackend.models.entities.Municipality;
import vibejensen.eksamen.exambackend.models.entities.Parish;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MunicipalityRepository extends JpaRepository<Municipality, Integer> {


    Optional<Municipality> findByCode(int code);

    /*@Query("SELECT rNumber.number FROM RNumber rNumber JOIN rNumber.parish p WHERE rNumber.parish.id = ?1 and rNumber.date = ?2 " +
           "JOIN p.municipality on")
    Optional<List<Double>> findCurrentRNumber(int munId, Date date);

     */

    /*
    *
    * "SELECT DISTINCT " +
                        "id_project, project_title, project_description, project_deadline, project_manhours " +
                        "FROM teams_users " +
                        "RIGHT JOIN teams_projects ON teams_users.f_id_team = teams_projects.f_id_team " +
                        "RIGHT JOIN projects ON teams_projects.f_id_project = projects.id_project " +
                        "WHERE f_id_user = ?";
    *
    * */

    /* ParisRepo
    @Query("SELECT rNumber.number FROM RNumber rNumber JOIN rNumber.parish p WHERE rNumber.parish.id = ?1 and rNumber.date = ?2")
    Optional<Double> findCurrentRNumber(int parishId, Date date);

     */
}
