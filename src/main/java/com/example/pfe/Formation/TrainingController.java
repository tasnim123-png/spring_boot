package com.example.pfe.Formation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/training")
public class TrainingController {

    @Autowired
    private TrainingService trainingService;


    @PostMapping
    public ResponseEntity<Training> createTraining(@RequestBody Training training){
        Training savedTraining = trainingService.insert(training);
        return new ResponseEntity<>(savedTraining, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Training> updateTraining(@PathVariable Integer id, @RequestBody Training training){
        training.setId(id);
        Training updatedTraining = trainingService.update(training);
        return new ResponseEntity<>(updatedTraining,HttpStatus.OK);
    }

    @GetMapping("{id}")

    public ResponseEntity<Training> getTraining(@PathVariable Integer id){
        Training training= (Training) trainingService.getbyId(id);
        return new ResponseEntity<>(training,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Training>> getAllTraining(){
        List<Training> allTraining = trainingService.getallTraining();
        return  new ResponseEntity<>(allTraining,HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteTraining(@PathVariable Integer id){
        String status = trainingService.deletebyId(id);
        return new ResponseEntity<>(status,HttpStatus.OK);
    }



}
