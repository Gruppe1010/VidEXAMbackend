package vibejensen.eksamen.exambackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vibejensen.eksamen.exambackend.models.entities.Lockdown;
import vibejensen.eksamen.exambackend.models.entities.Parish;
import vibejensen.eksamen.exambackend.repositories.LockdownRepository;

import java.util.ArrayList;
import java.util.List;


@Service
public class LockdownService {

    @Autowired
    LockdownRepository lockdownRepository;

    public void saveLockdown(Parish parish){

        List<Lockdown> lockdowns = parish.getLockdowns();

        if(lockdowns != null){
            Lockdown lockdown = lockdowns.get(0);

            lockdown.setLockedDownParish(parish);

            lockdownRepository.save(lockdown);
        }
    }







}
