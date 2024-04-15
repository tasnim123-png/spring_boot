package com.example.pfe.Formation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingServiceImpl implements TrainingService {
    @Autowired
    private TrainingRepository trainingRepository;

    @Override
    public Training insert(Training training) {
        return trainingRepository.save(training);

    }

    @Override
    public Training update(Training training) {
        return trainingRepository.save(training);
    }

    @Override
    public Object getbyId(Integer id) {
        Optional<Training> findbyId = trainingRepository.findById(id);
        return findbyId.get();
    }

    @Override
    public List<Training> getallTraining() {
        return trainingRepository.findAll();
    }

    @Override
    public String deletebyId(Integer id) {
        if (trainingRepository.existsById(id)) {
            trainingRepository.deleteById(id);
            return "Training successfully deleted";
        } else {
            return "Not found";
        }
    }
}

