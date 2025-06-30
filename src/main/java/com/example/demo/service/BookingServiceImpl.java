package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Booking;
import com.example.demo.model.Booking.BookingStatus;
import com.example.demo.model.Register;
import com.example.demo.repository.BookingRepository;

import jakarta.annotation.PostConstruct;

@Service
public class BookingServiceImpl implements BookingService{

	@Autowired
	BookingRepository bookingRepo;

	@Override
	public Booking save(Booking booking) {
		// TODO Auto-generated method stub
		return bookingRepo.save(booking);
	}

	@Override
	public Booking findByBookingId(String bookingId) {
		// TODO Auto-generated method stub
		return bookingRepo.findById(bookingId)
	            .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + bookingId));
	}

	@Override
	public List<Booking> getBookingsByUser(Register user) {
		// TODO Auto-generated method stub
		return bookingRepo.findByPassengerid(user);
	}

	  @PostConstruct
	  @Override
	  public void updateAllCompletedBookingsOnStartup() {
	        updateAllCompletedBookings();
	    }

	    public void updateAllCompletedBookings() {
	        List<Booking> bookings = bookingRepo.findByStatus(BookingStatus.Booked);
	        for (Booking b : bookings) {
	            if (b.getTravelDate().isBefore(LocalDate.now())) {
	                b.setStatus(BookingStatus.Completed);
	                bookingRepo.save(b);
	            }
	        }
	    }

		@Override
		public void cancelBooking(String bookingId) {
		    Booking booking = findByBookingId(bookingId);

		    if (booking == null) {
		        throw new RuntimeException("Booking not found.");
		    }

		    // Prevent cancellation if already cancelled
		    if (booking.getStatus() == BookingStatus.Cancelled) {
		        throw new RuntimeException("Ticket is already cancelled.");
		    }

		    // Prevent cancellation if travel date is today or in the past
		    if (!booking.getTravelDate().isAfter(LocalDate.now())) {
		        throw new RuntimeException("Cannot cancel ticket: travel date has passed or is today.");
		    }

		    // Update status
		    booking.setStatus(BookingStatus.Cancelled);
		    bookingRepo.save(booking);
		}

}



