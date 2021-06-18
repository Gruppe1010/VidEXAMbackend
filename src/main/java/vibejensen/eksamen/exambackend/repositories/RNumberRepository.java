package vibejensen.eksamen.exambackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vibejensen.eksamen.exambackend.models.entities.Lockdown;
import vibejensen.eksamen.exambackend.models.entities.RNumber;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RNumberRepository extends JpaRepository<RNumber, Integer> {


    @Query("SELECT rNumber.id FROM RNumber rNumber WHERE rNumber.parish.id = ?1")
    Optional<List<Integer>> findIdsByParishId(int parishId);

    @Transactional
    @Modifying
    @Query("DELETE FROM RNumber rNumber WHERE rNumber.id = ?1")
    void deleteRNumberById(int rNumberId);

    @Query("SELECT rNumber FROM RNumber rNumber  WHERE rNumber.parish.id = ?1")
    Optional<Set<RNumber>> findByParishId(int parishId);

}
