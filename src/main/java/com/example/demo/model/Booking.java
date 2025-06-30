package com.example.demo.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "bookings")
public class Booking {
	
	    @Id
	    @Column(name = "booking_id", length = 36)
	    private String bookingId;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "passenger_id", referencedColumnName = "id", nullable = false)
	    private Register passengerid;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "train_id", referencedColumnName = "train_id", nullable = false)
	    private Train train;

	    @Column(name = "train_name", nullable = false)
	    private String trainName;

	    public String getBookingId() {
			return bookingId;
		}

		public void setBookingId(String bookingId) {
			this.bookingId = bookingId;
		}

		public Register getPassengerid() {
			return passengerid;
		}

		public void setPassengerid(Register passengerid) {
			this.passengerid = passengerid;
		}

		public Train getTrain() {
			return train;
		}

		public void setTrain(Train train) {
			this.train = train;
		}

		public String getTrainName() {
			return trainName;
		}

		public void setTrainName(String trainName) {
			this.trainName = trainName;
		}

		public LocalDate getBookingDate() {
			return bookingDate;
		}

		public void setBookingDate(LocalDate bookingDate) {
			this.bookingDate = bookingDate;
		}

		public LocalDate getTravelDate() {
			return travelDate;
		}

		public void setTravelDate(LocalDate travelDate) {
			this.travelDate = travelDate;
		}

		public String getSourceStation() {
			return sourceStation;
		}

		public void setSourceStation(String sourceStation) {
			this.sourceStation = sourceStation;
		}

		public String getDestinationStation() {
			return destinationStation;
		}

		public void setDestinationStation(String destinationStation) {
			this.destinationStation = destinationStation;
		}

		public int getSeats() {
			return seats;
		}

		public void setSeats(int seats) {
			this.seats = seats;
		}

		public BigDecimal getTotalAmount() {
			return totalAmount;
		}

		public void setTotalAmount(BigDecimal totalAmount) {
			this.totalAmount = totalAmount;
		}

		public BookingStatus getStatus() {
			 if (this.status == BookingStatus.Booked && this.travelDate.isBefore(LocalDate.now())) {
		            this.status = BookingStatus.Completed;
		        }
		        return status;
		}

		public void setStatus(BookingStatus status) {
			this.status = status;
		}

	
		@Column(name = "booking_date")
		@JsonFormat(pattern = "yyyy-MM-dd")
	    private LocalDate bookingDate;

	    @Column(name = "travel_date")
	    @JsonFormat(pattern = "yyyy-MM-dd")
	    private LocalDate travelDate;

	    @Column(name = "source_station", nullable = false)
	    private String sourceStation;

	    @Column(name = "destination_station", nullable = false)
	    private String destinationStation;
	    
	    @Column(name = "departure_time")
	    private String departure_time;

	    public String getDeparture_time() {
			return departure_time;
		}

		public void setDeparture_time(String departure_time) {
			this.departure_time = departure_time;
		}

		@Column(name = "seats", nullable = false)
	    private int seats;

	    @Column(name = "total_amount", precision = 10, scale = 2, nullable = false)
	    private BigDecimal totalAmount;

	    @Enumerated(EnumType.STRING)
	    @Column(name = "status", columnDefinition = "ENUM('Pending,', 'Booked', 'Cancelled', 'Completed')")
	    private BookingStatus status = BookingStatus.Booked;
	    

	    public enum BookingStatus {
	    	Pending,
	        Booked,
	        Cancelled,
	        Completed
	    }
	    
	    
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
