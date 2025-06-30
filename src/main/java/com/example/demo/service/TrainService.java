package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Train;

public interface TrainService {

	public Train addTrain(Train train);
    public List<Train> getAllTrains();
    public Train getTrainById(long trainId);
    public Train updateTrain(Train train);
    public void deleteTrain(long trainId);
    public boolean isTrain_nameTaken(String train_name);
    public List<Train> findTrainBySourceAndDestination(String source, String destination);
   
}
