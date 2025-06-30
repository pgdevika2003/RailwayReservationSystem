package com.example.demo.controller;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Booking;
import com.example.demo.model.Booking.BookingStatus;
import com.example.demo.model.Payment;
import com.example.demo.model.Register;
import com.example.demo.model.Train;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.TrainRepository;
import com.example.demo.service.BookingServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PaymentController {

	@Autowired
	BookingRepository bookingRepository;
	
	@Autowired
	BookingServiceImpl bookingServiceImpl;
	
	@Autowired
	TrainRepository trainRepository;
	
	
	
	@GetMapping("/payments/make-payment")
	public String showPaymentPage(@RequestParam("bookingId") String bookingId, 
	                              Model model,
	                              HttpServletRequest request) {
		model.addAttribute("bookingId", bookingId);
	    model.addAttribute("payment", new Payment());

	    // bookingData will be injected on the client side via JS
	    // so no need to fetch anything from DB unless you're verifying bookingId
	    return "Payment";  // this should match your HTML page name
	}



	@PostMapping("/payments/make-payment")
	public String processPayment(@ModelAttribute Payment payment, HttpSession session,  @RequestParam("bookingData") String bookingDataJson,
          
            Model model) {
			try {
			Register user = (Register) session.getAttribute("user");
			if (user == null) {
			return "redirect:/login";
			}
			

			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(bookingDataJson);
			
			// Add null checks for all required fields
			if (!root.has("bookingId") || !root.has("travelDate") || 
			!root.has("fromStation") || !root.has("toStation") ||
			!root.has("trainName") || !root.has("trainNumber") ||
			!root.has("totalFare") || !root.has("seatsBooked")|| !root.has("time")){
			throw new RuntimeException("Invalid booking data - missing required fields");
			}
			
			String bookingId = root.path("bookingId").asText();
			String travelDateStr = root.path("travelDate").asText();
			String sourceStation = root.path("fromStation").asText();
			String destinationStation = root.path("toStation").asText();
			String departureTime = root.path("time").asText();
			String trainName = root.path("trainName").asText();
			Long trainId = root.path("trainNumber").asLong();

	        Train train = trainRepository.findById(trainId)
	            .orElseThrow(() -> new RuntimeException("Train not found with ID: " + trainId));

	        BigDecimal totalAmount = new BigDecimal(root.path("totalFare").asText());

	        int seats = root.get("seatsBooked").asInt();
	        

	        LocalDate travelDate = LocalDate.parse(travelDateStr, DateTimeFormatter.ISO_DATE);
	        
	        System.out.println("Received bookingData: " + bookingDataJson);
	        Booking booking = new Booking();
	        booking.setBookingId(bookingId);
	        booking.setTravelDate(travelDate);
	        booking.setBookingDate(LocalDate.now());
	        booking.setStatus(BookingStatus.Booked);
	        booking.setSourceStation(sourceStation);
	        booking.setDestinationStation(destinationStation);
	        booking.setDeparture_time(departureTime);
	        booking.setTrain(train);
	        booking.setTrainName(trainName);
	        booking.setTotalAmount(totalAmount);
	        booking.setSeats(seats);
	        booking.setPassengerid(user);
	       

	        bookingServiceImpl.save(booking);
	        session.removeAttribute("pendingBooking");


	        System.out.println("Payment details: " + payment);
	        session.setAttribute("confirmedBooking", booking);
	        return "redirect:/payment-success";
	    } catch (Exception e) {
	        e.printStackTrace();
	        model.addAttribute("message", "Payment failed. Please try again.");
	        return "Payment";
	    }
	}

	@GetMapping("/payment-success")
	public ModelAndView showpaymentsucess(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("PaymentSuccess");
		 Register getuser = (Register) session.getAttribute("user");
		    Booking booking = (Booking) session.getAttribute("confirmedBooking");

		    if (getuser != null && booking != null) {
		        modelAndView.addObject("user", getuser);
		        modelAndView.addObject("booking", booking);
		        session.removeAttribute("confirmedBooking"); // optional cleanup
		        return modelAndView;
		    }

		    return new ModelAndView("Payment");
}
} 
