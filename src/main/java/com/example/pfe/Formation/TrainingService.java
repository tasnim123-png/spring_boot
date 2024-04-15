package com.example.pfe.Formation;

import java.util.List;

public interface TrainingService {

    public Training insert(Training training);

    public Training update(Training training);

    public Object getbyId ( Integer id);

    public List<Training> getallTraining();

    public String deletebyId(Integer id);





}
