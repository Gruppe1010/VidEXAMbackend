package vibejensen.eksamen.exambackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vibejensen.eksamen.exambackend.models.entities.Lockdown;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface LockdownRepository extends JpaRepository<Lockdown, Integer> {


    @Query("SELECT lockdown.id FROM Lockdown lockdown WHERE lockdown.lockedDownParish.id = ?1")
    Optional<List<Integer>> findIdsByParishId(int parishId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Lockdown lockdown WHERE lockdown.id = ?1")
    void deleteLockdownById(int lockdownId);

    @Query("SELECT lockdown FROM Lockdown lockdown WHERE lockdown.lockedDownParish.id = ?1")
    Optional<Set<Lockdown>> findByParishId(int parishId);

}
