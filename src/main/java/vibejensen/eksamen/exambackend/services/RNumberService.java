package vibejensen.eksamen.exambackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vibejensen.eksamen.exambackend.models.entities.Lockdown;
import vibejensen.eksamen.exambackend.models.entities.Parish;
import vibejensen.eksamen.exambackend.models.entities.RNumber;
import vibejensen.eksamen.exambackend.repositories.LockdownRepository;
import vibejensen.eksamen.exambackend.repositories.RNumberRepository;

import java.util.ArrayList;
import java.util.List;


@Service
public class RNumberService {

    @Autowired
    RNumberRepository rNumberRepository;

    public void saveRNumber(Parish parish){

        List<RNumber> rNumbers = parish.getRNumbers();

        if(rNumbers != null){
            RNumber rNumber = rNumbers.get(0);

            rNumber.setParish(parish);

            rNumberRepository.save(rNumber);
        }
    }







}
