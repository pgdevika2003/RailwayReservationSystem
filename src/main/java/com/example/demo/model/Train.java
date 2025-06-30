package com.example.demo.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "trains")
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "Train id is required")
    @Column(name = "train_id")
    private long train_id;

   
    public long getTrain_id() {
        return train_id;
    }

    public void setTrain_id(long train_id) {
        this.train_id = train_id;
    }

    @NotBlank(message = "Train name is required")
    @Size(min = 3, max = 50, message = "Train name must be between 3 and 50 characters")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Train name must contain only letters and spaces")
    @Column(name = "train_name", nullable = false)
    private String trainName;

    @NotBlank(message = "Train type is required")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Train type must contain only letters and spaces")
    @Column(name = "train_type")
    private String train_type;

    @NotBlank(message = "Source station is required")
    @Column(name = "source_station", nullable = false)
    private String source_station;

    @NotBlank(message = "Destination station is required")
    @Column(name = "destination_station", nullable = false)
    private String destination_station;

    @NotNull(message = "Departure time is required")
    @Column(name = "departure_time")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime departure_time;

    @NotNull(message = "Arrival time is required")
    @DateTimeFormat(pattern = "HH:mm")
    @Column(name = "arrival_time")
    private LocalTime arrival_time;

    @Min(value = 10, message = "Total seats must be at least 10")
    @Column(name = "total_seats")
    private int total_seats;
    

    @NotNull(message = "Fare is required")
    @Positive(message = "Fare must be a positive number. Please enter a valid amount.")  
    @Column(name = "fare", precision=10, scale=2)
    private BigDecimal fare;

    private String fareFormatted;

    public String getFareFormatted() {
    	if (fare == null) {
            return "N/A"; // default value if fare is null
        }
        DecimalFormat df = new DecimalFormat("₹#,##0.00");
        fareFormatted = df.format(fare);
        return fareFormatted;
    }

    public void setFareFormatted(String fareFormatted) {
        this.fareFormatted = fareFormatted;
    }

    @Version
    @Column(name = "version")
    private Long version;

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    

    public String getTrainName() {
		return trainName;
	}

	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}

	public String getTrain_type() {
        return train_type;
    }

    public void setTrain_type(String train_type) {
        this.train_type = train_type;
    }

    public String getSource_station() {
        return source_station;
    }

    public void setSource_station(String source_station) {
        this.source_station = source_station;
    }

    public String getDestination_station() {
        return destination_station;
    }

    public void setDestination_station(String destination_station) {
        this.destination_station = destination_station;
    }

    public LocalTime getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(LocalTime departure_time) {
        this.departure_time = departure_time;
    }

    public LocalTime getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(LocalTime arrival_time) {
        this.arrival_time = arrival_time;
    }

    public int getTotal_seats() {
        return total_seats;
    }

    public void setTotal_seats(int total_seats) {
        this.total_seats = total_seats;
    }

    public BigDecimal getFare() {
        return fare;
    }

    public void setFare(BigDecimal fare) {
        this.fare = fare;
    }
}
