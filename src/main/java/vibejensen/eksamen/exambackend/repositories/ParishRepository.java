package vibejensen.eksamen.exambackend.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vibejensen.eksamen.exambackend.models.entities.Parish;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public interface ParishRepository extends JpaRepository<Parish, Integer> {

    Optional<Parish> findByCode(int code);

    Optional<Parish> findByName(String name);

    Optional<ArrayList<Parish>> findByOrderByCodeAsc();

    @Query("SELECT rNumber.number FROM RNumber rNumber JOIN rNumber.parish p WHERE rNumber.parish.id = ?1 and rNumber.date = ?2")
    Optional<Double> findCurrentRNumber(int parishId, Date date);

    @Transactional
    @Modifying
    @Query("DELETE FROM Parish parish WHERE parish.id = ?1")
    void deleteById(int rNumberId);

}
