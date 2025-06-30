package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Train;

public interface TrainRepository extends JpaRepository<Train, Long> {
   
	boolean existsByTrainName(String trainName);

	@Query("SELECT t FROM Train t WHERE LOWER(t.source_station) = LOWER(:source) AND LOWER(t.destination_station) = LOWER(:destination)")
    List<Train> findBySourceAndDestination(String source, String destination);
}
