package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Booking;
import com.example.demo.model.Booking.BookingStatus;
import com.example.demo.model.Register;

public interface BookingRepository extends JpaRepository<Booking, String> {
	
	List<Booking> findByPassengerid(Register user);

	List<Booking> findByStatus(BookingStatus booked);

}
