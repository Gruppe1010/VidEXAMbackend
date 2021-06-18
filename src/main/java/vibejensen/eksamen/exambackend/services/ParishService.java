package vibejensen.eksamen.exambackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vibejensen.eksamen.exambackend.models.dto.CreateParishDTO;
import vibejensen.eksamen.exambackend.models.dto.ShowParishDTO;
import vibejensen.eksamen.exambackend.models.dto.StringResponse;
import vibejensen.eksamen.exambackend.models.entities.Lockdown;
import vibejensen.eksamen.exambackend.models.entities.Municipality;
import vibejensen.eksamen.exambackend.models.entities.Parish;
import vibejensen.eksamen.exambackend.models.entities.RNumber;
import vibejensen.eksamen.exambackend.repositories.LockdownRepository;
import vibejensen.eksamen.exambackend.repositories.MunicipalityRepository;
import vibejensen.eksamen.exambackend.repositories.ParishRepository;
import vibejensen.eksamen.exambackend.repositories.RNumberRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ParishService {

    @Autowired
    ParishRepository parishRepository;
    @Autowired
    MunicipalityRepository municipalityRepository;
    @Autowired
    LockdownRepository lockdownRepository;
    @Autowired
    RNumberRepository rNumberRepository;



    public ResponseEntity<StringResponse> createParish(CreateParishDTO parishDTO){

        // + mange if's for forskellige fejlmeddelelser
        // hvis koden er ledig
        if(isCodeAvailable(parishDTO.getCode())) {
            // hvis navnet også er ledigt
            if (isNameAvailable(parishDTO.getName())){
                // hent kommune

                Optional<Municipality> optMunicipality = municipalityRepository.findByCode(parishDTO.getMunicipalityCode());

                Municipality municipality = optMunicipality.orElse(null);

                // hvis municipality-code findes
                if(municipality != null){
                    // omdan DTO til entity
                    Parish newParish = convertToParish(parishDTO, municipality);
                    // Gem nyt sogn
                    saveParish(newParish);
                    //Gem lockdowns
                    saveLockdown(newParish);
                    //Gem rNumbers
                    saveRNumber(newParish);

                    return new ResponseEntity<>(new StringResponse("Sogn oprettet"), HttpStatus.OK); // 200
                }
                return new ResponseEntity<>(new StringResponse("Kommunen findes ikke"), HttpStatus.BAD_REQUEST); // 400
            }
            return new ResponseEntity<>(new StringResponse("Sognenavn optaget"), HttpStatus.CONFLICT); // 409
        }
        return new ResponseEntity<>(new StringResponse("Sognekode optaget"), HttpStatus.CONFLICT); // 409

    }


    public ResponseEntity<StringResponse> updateParish(CreateParishDTO parishDTO, int parishId){

        Optional<Parish> optParish = parishRepository.findById(parishId);

        if(optParish.isPresent()){
            Parish parish = optParish.get();

            //hvis code er det samme ELLER available
            if((parishDTO.getCode() == parish.getCode()) ||
                    isCodeAvailable(parishDTO.getCode())){

                //hvis name er det samme ELLER available
                if((parishDTO.getName().equals(parish.getName())) ||
                        isNameAvailable(parishDTO.getName())){

                    // hent kommune
                    Optional<Municipality> optMunicipality = municipalityRepository.findByCode(parishDTO.getMunicipalityCode());

                    Municipality municipality = optMunicipality.orElse(null);

                    // hvis kommunen er den samme ELLER findes
                    if((parishDTO.getMunicipalityCode() == parish.getMunicipality().getCode()) ||
                            municipality != null){

                        // hvis lockdown er ændret
                        if(parishDTO.isOnLockdown() != parish.isOnLockdown()){
                            // hvis den er blevet tilføjet
                            if(parishDTO.isOnLockdown()){
                                // tilføj nyt lockdown-obj til parish
                                List<Lockdown> lockdowns = parish.getLockdowns();
                                if(lockdowns == null){
                                    lockdowns = new ArrayList<>();
                                }
                                lockdowns.add(new Lockdown(parish));
                                parish.setOnLockdown(true);

                            } else{ // hvis den er blevet fjernet
                                // sæt isOnLockdown til false
                                parish.setOnLockdown(false);
                                System.out.println("YAY");

                                // OG tilføj sluttidspunkt til seneste lockdown-obj
                                parish.setLastLockdownsEndDate();
                            }
                        }

                        // hvis rNumbers IKKE har denne dato - skal den oprettes
                        parish.addRNumber(parishDTO.getCurrentRNumber());

                        // sæt alle andre attributter
                        parish.setCode(parishDTO.getCode());
                        parish.setName(parishDTO.getName());
                        parish.setMunicipality(municipality);

                        // gem sogn
                        parishRepository.save(parish);
                        //Gem lockdowns
                        lockdownRepository.saveAll(parish.getLockdowns());
                        //Gem rNumbers
                        rNumberRepository.saveAll(parish.getRNumbers());

                        return new ResponseEntity<>(new StringResponse("Sogn gemt"), HttpStatus.OK); // 200
                    }
                    return new ResponseEntity<>(new StringResponse("Kommunen findes ikke"), HttpStatus.BAD_REQUEST); // 400
                }
                return new ResponseEntity<>(new StringResponse("Sognenavn optaget"), HttpStatus.CONFLICT); // 409
            }
            return new ResponseEntity<>(new StringResponse("Sognekode optaget"), HttpStatus.CONFLICT); // 409
        }
        return new ResponseEntity<>(new StringResponse("Kunne ikke finde sognet"), HttpStatus.NOT_FOUND); // 409
    }


    /**
     * Tjekker om code er optaget ved at se om den allerede findes i db
     * @param code code som tjekkes er lagret i db
     * @return bool om den er optaget eller ej
     * */
    public boolean isCodeAvailable(int code){
        Optional<Parish> optParish = parishRepository.findByCode(code);

        // fordi hvis isEmpty == true --> codeAvailable == true
        return optParish.isEmpty();
    }
    /**
     * Tjekker om name er optaget ved at se om den allerede findes i db
     * @param name name som tjekkes er lagret i db
     * @return bool om den er optaget eller ej
     * */
    public boolean isNameAvailable(String name){
        Optional<Parish> optParish = parishRepository.findByName(name);

        // fordi hvis isEmpty == true --> nameAvailable == true
        return optParish.isEmpty();
    }

    public Parish convertToParish(CreateParishDTO parishDTO, Municipality municipality){

        return parishDTO.convertToParish(municipality);
    }

    // gemmer et sogn i db
    public void saveParish(Parish parish){
        parishRepository.save(parish);
    }
    public void saveLockdown(Parish parish){

        List<Lockdown> lockdowns = parish.getLockdowns();

        if(lockdowns != null){
            Lockdown lockdown = lockdowns.get(lockdowns.size() - 1);

            lockdown.setLockedDownParish(parish);

            lockdownRepository.save(lockdown);
        }
    }
    public void saveRNumber(Parish parish){

        List<RNumber> rNumbers = parish.getRNumbers();

        if(rNumbers != null){
            RNumber rNumber = rNumbers.get(rNumbers.size() - 1);

            rNumber.setParish(parish);

            rNumberRepository.save(rNumber);
        }
    }

    // henter alle parishes
    public ArrayList<ShowParishDTO> findAllParishes(){

        Optional<ArrayList<Parish>> optParishes = parishRepository.findByOrderByCodeAsc();

        // alternativ
        ArrayList<ShowParishDTO> parishDTOs = null;
        if(optParishes.isPresent()){
            parishDTOs = new ArrayList<>();


            ArrayList<Parish> parishes = optParishes.get();

            //
            for(Parish p : parishes){
                double currentRNumber = 0.0;
                Optional<Double> optRNumber = parishRepository.findCurrentRNumber(p.getId(), new Date());

                if(optRNumber.isPresent()){
                    currentRNumber = optRNumber.get();
                }

                ShowParishDTO parishDTO = p.convertToShowParishDTO(currentRNumber);

                parishDTOs.add(parishDTO);
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
        return parishDTOs;
    }


    public HttpStatus deleteParishById(int parishId){

        // vi finder alle lockdowns id'er som er tilknyttet parishets id
        Optional<List<Integer>> lockdownIds = lockdownRepository.findIdsByParishId(parishId);

        //hvis der ER nogle id'er - slet dem fra rep
        lockdownIds.ifPresent(integers -> integers.forEach(integer -> lockdownRepository.deleteLockdownById(integer)));

        // vi finder alle rNumbers id'er som er tilknyttet parishets id
        Optional<List<Integer>> rNumberIds = rNumberRepository.findIdsByParishId(parishId);

        //hvis der ER nogle id'er - slet dem fra rep
        rNumberIds.ifPresent(integers -> integers.forEach(integer -> rNumberRepository.deleteRNumberById(integer)));


        // vi sletter parish-obj
        parishRepository.deleteById(parishId);

        // vi henter dem op igen og ser om de er der - for at bekræfte at de er slettet
        Optional<Parish> optParish = parishRepository.findById(parishId);
        Optional<Set<Lockdown>> optLockdowns = lockdownRepository.findByParishId(parishId);
        Optional<Set<RNumber>> optRNumbers = rNumberRepository.findByParishId(parishId);

        if(optParish.isEmpty() && optLockdowns.get().size() == 0 && optRNumbers.get().size() == 0){
            return  HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }


    /*
    public static double findCurrentRNumber(Parish parish){

        Optional<Double> optRNumber = parishRepository.findCurrentRNumber(parish.getId(), new Date());

        if(optRNumber.isPresent()){
            return optRNumber.get();
        }
        return 0.0;
    }

     */






}
