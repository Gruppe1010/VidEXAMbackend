package vibejensen.eksamen.exambackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vibejensen.eksamen.exambackend.models.dto.ShowMunicipalityDTO;
import vibejensen.eksamen.exambackend.services.MunicipalityService;

import java.util.ArrayList;

@RestController
@CrossOrigin(value = "*")
@RequestMapping("/api")
public class MunicipalityRestController {

    @Autowired
    MunicipalityService municipalityService;

    @GetMapping("/municipalities")
    public ResponseEntity<ArrayList<ShowMunicipalityDTO>> retrieveAllMunicipalities(){

        try {
            ArrayList<ShowMunicipalityDTO> municipalities = municipalityService.findAllMunicipalities();

            if(municipalities != null){
                return new ResponseEntity<>(municipalities, HttpStatus.OK);
            }

            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }






}
