package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Booking;
import com.example.demo.model.Register;

public interface BookingService {

	public Booking save(Booking booking);
	public Booking findByBookingId(String bookingId);
	List<Booking> getBookingsByUser(Register user);
	public void updateAllCompletedBookingsOnStartup();
	public void cancelBooking(String bookingId);
}
