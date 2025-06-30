package com.example.demo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BookingAspect {

	@Before("execution(* com.example.demo.service.BookingServiceImpl.cancelBooking(..)) && args(bookingId)")
    public void beforeCancel(String bookingId) {
        System.out.println("Permission check before cancelling booking ID: " + bookingId);
        
    }
}
