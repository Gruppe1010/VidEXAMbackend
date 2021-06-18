package vibejensen.eksamen.exambackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vibejensen.eksamen.exambackend.models.dto.CreateParishDTO;
import vibejensen.eksamen.exambackend.models.dto.ShowParishDTO;
import vibejensen.eksamen.exambackend.models.dto.StringResponse;
import vibejensen.eksamen.exambackend.models.dto.UpdateParishDTO;
import vibejensen.eksamen.exambackend.models.entities.Lockdown;
import vibejensen.eksamen.exambackend.models.entities.Municipality;
import vibejensen.eksamen.exambackend.models.entities.Parish;
import vibejensen.eksamen.exambackend.services.LockdownService;
import vibejensen.eksamen.exambackend.services.MunicipalityService;
import vibejensen.eksamen.exambackend.services.ParishService;
import vibejensen.eksamen.exambackend.services.RNumberService;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(value = "*")
@RequestMapping("/api/parishes")
public class ParishRestController {

    @Autowired
    ParishService parishService;
    @Autowired
    LockdownService lockdownService;
    @Autowired
    RNumberService rNumberService;
    @Autowired
    MunicipalityService municipalityService;

    // ! Post
    //For at Spring kan deserialisere JSON vi får fra front, SKAL vi have getters og setters (OG de SKAL have de rigtige navne)
    @PostMapping("/")
    public ResponseEntity<StringResponse> createParish(@RequestBody CreateParishDTO parishDTO) {

        return parishService.createParish(parishDTO);
    }

    // ! Get alle
    /**
     * GetMapping til at hente alle sogne op til visning
     *
     * localhost:8082/api/parishes
     * */
    @GetMapping("/")
    public ResponseEntity<ArrayList<ShowParishDTO>> retrieveAllParishes(){

        try {

            ArrayList<ShowParishDTO> parishes = parishService.findAllParishes();


            if(parishes != null){

                return new ResponseEntity<>(parishes, HttpStatus.OK);
            }

            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ! Delete
    @DeleteMapping("/{parishId}")
    public ResponseEntity<HttpStatus> deleteParish(@PathVariable int parishId){
        try{
            return new ResponseEntity<>(parishService.deleteParishById(parishId));
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





    // ! Put
    //For at Spring kan deserialisere JSON vi får fra front, SKAL vi have getters og setters (OG de SKAL have de rigtige navne)
    @PutMapping("/{parishId}")
    public ResponseEntity<StringResponse> updateParish(@RequestBody CreateParishDTO parishDTO,
                                                       @PathVariable int parishId) {

        System.out.println(parishDTO);

        return parishService.updateParish(parishDTO, parishId);
    }





}
