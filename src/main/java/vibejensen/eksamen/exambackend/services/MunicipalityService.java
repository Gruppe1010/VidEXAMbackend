package vibejensen.eksamen.exambackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vibejensen.eksamen.exambackend.models.dto.CreateParishDTO;
import vibejensen.eksamen.exambackend.models.dto.ShowMunicipalityDTO;
import vibejensen.eksamen.exambackend.models.dto.ShowParishDTO;
import vibejensen.eksamen.exambackend.models.entities.Lockdown;
import vibejensen.eksamen.exambackend.models.entities.Municipality;
import vibejensen.eksamen.exambackend.models.entities.Parish;
import vibejensen.eksamen.exambackend.models.entities.RNumber;
import vibejensen.eksamen.exambackend.repositories.MunicipalityRepository;
import vibejensen.eksamen.exambackend.repositories.ParishRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MunicipalityService {

    @Autowired
    MunicipalityRepository municipalityRepository;


    public Municipality findByCode(int code){
        Optional<Municipality> optMunicipality = municipalityRepository.findByCode(code);

        return optMunicipality.orElse(null);
    }





    // henter alle parishes
    /*
    public ArrayList<ShowMunicipalityDTO> findAllMunicipalities(){

        Optional<List<Municipality>> optMunicipalities = Optional.of(municipalityRepository.findAll());

        if(optMunicipalities.isPresent()){
            // omdan til DTO
            ArrayList<ShowMunicipalityDTO> municipalityDTOs = (ArrayList<ShowMunicipalityDTO>)
                    optMunicipalities.get()
                            .stream()
                            .map(municipality -> municipality
                                    .convertToShowMunicipalitiesDTO(findCurrentRNumber(municipality)))
                            .collect(Collectors.toList());

            return municipalityDTOs;
        }
        return null;
    }

     */


    // henter alle kommuner
    public ArrayList<ShowMunicipalityDTO> findAllMunicipalities(){
        ArrayList<ShowMunicipalityDTO> municipalityDTOs = null;

        Optional<List<Municipality>> optMunicipalities = Optional.of(municipalityRepository.findAll());

        // hvis der er nogen kommuner i db
        if(optMunicipalities.isPresent()){
            municipalityDTOs = new ArrayList<>();

            // hente dem ud
            ArrayList<Municipality> municipalities = (ArrayList<Municipality>) optMunicipalities.get();

            //for hver kommune
            for(Municipality m : municipalities){
                // find gennemsnitlige smittetal
                double averageRNumber = findCurrentRNumber(m);

                // omdan til DTO som sendes til front
                ShowMunicipalityDTO municipalityDTO = m.convertToShowMunicipalitiesDTO(averageRNumber);

                // tilføj dto til listen som returneres
                municipalityDTOs.add(municipalityDTO);
            }
        }


        /*
        if(optParishes.isPresent()){
            // omdan til DTO
            ArrayList<ShowParishDTO> parishDTOs = (ArrayList<ShowParishDTO>)
                    optParishes.get()
                    .stream()
                    .map(parish -> parish.convertToShowParishDTO(findCurrentRNumber(parish)))

                    //.map(parish -> parish.convertToShowParishDTO(parishRepository.findCurrentRNumber(parish.getId(), new Date()).get()))
                    .collect(Collectors.toList());

            System.out.println(parishDTOs);

            return parishDTOs;
        }

         */
        return municipalityDTOs;
    }

    /**
     * Finder det gennemsnitlige smittetryk for en kommune ud fra dets sogners smittetryk
     *
     * @param m kommunen som man vil finde gennemsnitligt smittryk for
     * */
    public static double findCurrentRNumber(Municipality m){
        double averageRNumber = 0;

        //henter alle sogne tilknyttet kommunen
        List<Parish> parishes = m.getParishes();
        // hvis der er nogen
        if(parishes != null){
            double rNumbersSum = 0;

            // for hvert sogn
            for(Parish p : parishes){
                // find alle rNumber-obj
                List<RNumber> rNumbers = p.getRNumbers();
                // for hvert rNumber-obj
                for(RNumber l : rNumbers){
                    // læg rNumber-værdi til rNumbersSum
                    rNumbersSum = rNumbersSum + l.getNumber();
                }
            }

            if(rNumbersSum > 0){
                averageRNumber = rNumbersSum/parishes.size();
            }
        }

        return averageRNumber;

    }



}
