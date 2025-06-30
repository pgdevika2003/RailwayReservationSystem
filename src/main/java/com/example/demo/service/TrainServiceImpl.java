package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Train;
import com.example.demo.repository.TrainRepository;

import jakarta.transaction.Transactional;

import java.util.List;


@Service
@Transactional
public class TrainServiceImpl implements TrainService {

    @Autowired    
    private TrainRepository trainRepository;

	@Override
	public Train addTrain(Train train) {
		// TODO Auto-generated method stub
		return trainRepository.save(train);
	}

	@Override
	public List<Train> getAllTrains() {
		// TODO Auto-generated method stub
		return trainRepository.findAll();
	}

	@Override
	public Train getTrainById(long train_id) {
		// TODO Auto-generated method stub
		return trainRepository.findById(train_id).orElse(null);
	}

	@Override
	public Train updateTrain(Train train) {
		// TODO Auto-generated method stub
		 return trainRepository.save(train);
	}

	@Override
	public void deleteTrain(long train_id) {
		// TODO Auto-generated method stub
		 trainRepository.deleteById(train_id);
	}

	@Override
	public boolean isTrain_nameTaken(String train_name) {
		// TODO Auto-generated method stub
		return trainRepository.existsByTrainName(train_name);
	}

	@Override
	public List<Train> findTrainBySourceAndDestination(String source, String destination) {
		 return trainRepository.findBySourceAndDestination(source, destination);
	    }

	
	}

	

   

