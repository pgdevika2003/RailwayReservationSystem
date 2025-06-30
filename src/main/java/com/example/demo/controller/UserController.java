package com.example.demo.controller;


import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Booking;
import com.example.demo.model.Register;
import com.example.demo.model.Train;
import com.example.demo.service.BookingServiceImpl;
import com.example.demo.service.RegisterServiceImpl;
import com.example.demo.service.TrainServiceImpl;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;



@Controller
public class UserController {

    @Autowired
    private RegisterServiceImpl registerService;

    @Autowired
    private TrainServiceImpl trainService;
    
    @Autowired
    private BookingServiceImpl bookingService;
    
   
    @GetMapping("/index")
    public String showUserForm() {
    	
        return "index";
    }
    
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
    	model.addAttribute("user", new Register());
        return "register"; // name of the Thymeleaf template without .html extension
    }
    
    @GetMapping("/Book-ticket")
    public String showBookingPage(HttpSession session, Model model) {
        Register getuser = (Register) session.getAttribute("getuser");
        if (getuser != null) {
            model.addAttribute("getuser", getuser);
        }
        return "Book-ticket"; // This will render your booking page view
    }
   
    @GetMapping("/search-trains")
    public ModelAndView searchTrains(@RequestParam String source, 
                                     @RequestParam String destination, 
                                     @RequestParam String date, 
                                     @RequestParam(required = false) String classType) {
        
        ModelAndView modelAndView = new ModelAndView();

        // Validate the date
        if (date != null) {
            LocalDate today = LocalDate.now();
            LocalDate inputDate = LocalDate.parse(date);
            if (inputDate.isBefore(today)) {
                modelAndView.addObject("errorMessage", "Date cannot be in the past.");
                modelAndView.setViewName("Book-ticket");
                return modelAndView;
            }
        }

        // Fetch the trains based on source and destination
        List<Train> trains = trainService.findTrainBySourceAndDestination(source, destination);

        // Check if no trains are available
        if (trains.isEmpty()) {
            modelAndView.addObject("errorMessage", "No trains available on the route.");
            modelAndView.setViewName("Book-ticket"); // or any other view where you want to display the error
            return modelAndView;
        }

        // Add the list of trains to the model
        modelAndView.addObject("trains", trains);

        // Set the name of the view (this will render search-result.html or your preferred view)
        modelAndView.setViewName("search-result");

        // Return the ModelAndView object containing the view and model data
        return modelAndView;
    }
    
    @GetMapping("/search-result")
    public ModelAndView showSearchResult( @RequestParam("source") String source,
            @RequestParam("destination") String destination, HttpSession session) {
    	ModelAndView modelAndView = new ModelAndView("search-result");
				 Register getuser = (Register) session.getAttribute("user");

	        if (getuser != null) {
	        	 
	             modelAndView.addObject("user", getuser);
	              modelAndView.setViewName("search-result");

	         }
	        
        return new ModelAndView("Book-ticket");
    }
    
    @GetMapping("/Booking/{train_id}")
    public ModelAndView showBooking( @PathVariable("train_id") Train train_id, HttpSession session) {
    	ModelAndView modelAndView = new ModelAndView("Booking");
				 Register getuser = (Register) session.getAttribute("user");

	        if (getuser != null) {
	        	modelAndView.addObject("user", getuser);
	        	modelAndView.addObject("trains", train_id);

	            return modelAndView; // Display user profile information
	        }
        return new ModelAndView("search-result");
    }
    
 // Display Dashboard (User Profile)
    @GetMapping("/Dashboard")
    public String showDashboard(HttpSession session, Model model) {
        Register getuser = (Register) session.getAttribute("user");

        if (getuser != null) {
            model.addAttribute("user", getuser);
            List<Booking> bookings = bookingService.getBookingsByUser(getuser);
            model.addAttribute("bookings", bookings);
            return "Dashboard"; // Display user profile information
            

        }

        return "redirect:/login"; // If no user found in session, redirect to login
    }
    
    @GetMapping("/cancelTicket")
    public ModelAndView showTicketcancelpage(HttpSession session) {
    	ModelAndView modelAndView = new ModelAndView("TicketCancellation");
    	 Register getuser = (Register) session.getAttribute("user");
         if(getuser!=null) { 
          return modelAndView;
         }
         return new ModelAndView("Dashboard");
    }
    
    @PostMapping("/cancelTicket")
    public  ModelAndView cancelticket(@RequestParam("bookingId") String bookingId, HttpSession session) {
    	ModelAndView modelAndView = new ModelAndView("TicketCancellation");
        try {
	        Booking book = bookingService.findByBookingId(bookingId);
	        if (book != null)  {
	            modelAndView.addObject("book", book);
	        }

	    } catch (Exception e) {
	        modelAndView.addObject("error", "BookingID does not exist");
	    }
         	return modelAndView;

    }
    
    @GetMapping("/confirmcancel/{id}")
    public ModelAndView confirmCancel(@PathVariable("id") String bookingId) {
        ModelAndView modelAndView = new ModelAndView("TicketCancellation");

        try {
            bookingService.cancelBooking(bookingId);
            Booking updatedBooking = bookingService.findByBookingId(bookingId);
            modelAndView.addObject("book", updatedBooking);
            modelAndView.setViewName("TicketCancellation");
            modelAndView.addObject("message", "Ticket successfully cancelled.");
        } catch (Exception e) {
            modelAndView.addObject("error", "Cancellation failed: " + e.getMessage());
        }

        return modelAndView;
    }



   
    @GetMapping("/logout")
    public String logout() {
        return "index.html";  // Redirect to login page after logging out
    }
    
    @GetMapping("/forget-password")
    public ModelAndView showResetForm() {
        return new ModelAndView("forget-password", "register", new Register());
    }
    
    @GetMapping("/user/edit/{id}")
    public String editUserForm(@PathVariable Long id, HttpSession session, Model model) {
        Register loggedInUser = (Register) session.getAttribute("user");
        
        // Check if the user is logged in
        if (loggedInUser != null) {
            Register user = registerService.getUserById(id);
            model.addAttribute("user", user); // Add the user from the database to the model
            return "editProfile";
        }
        
        return "redirect:/login"; // Redirect to login if not logged in
    }

  
 // Handle the registration process and validation
    @PostMapping("/save")
    public ModelAndView registerUser(@Valid @ModelAttribute("user") Register user, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView("register"); // corrected to "register" to match the Thymeleaf template

        // Check for validation errors
        if (result.hasErrors()) {
            // Pass the error result back to the model
            modelAndView.addObject("user", user); 
            modelAndView.setViewName("register");  
           
            return modelAndView; // Return the registration form with error messages
        }

        // Check if username is taken
        if (registerService.isUsernameTaken(user.getUsername())) {
            result.rejectValue("username", "error.username", "Username is already taken");
            modelAndView.addObject("user", user);  // Re-add the user object to maintain form state
            return modelAndView;
        }

        // Check if email is taken
        if (registerService.isEmailTaken(user.getEmail())) {
            result.rejectValue("email", "error.email", "Email is already registered");
            modelAndView.addObject("user", user);  // Re-add the user object to maintain form state
            return modelAndView;
        }
        
        if (user.getDob()== null) {
            result.rejectValue("dob", "error.dob", "Date of birth is required");
            modelAndView.addObject("user", user);
            return modelAndView;
        }
        if (user.getDob().isAfter(LocalDate.now().minusYears(18))) {
            result.rejectValue("dob", "error.dob", "You must be at least 18 years old");
            modelAndView.addObject("user", user);
            return modelAndView;
        }
        
        
        if (user.getPassword().length() < 6 ||user.getPassword().length() > 15) {
            result.rejectValue("password", "error.password", "Password must be between 6 and 15 characters");
            modelAndView.addObject("user", user);  // Re-add the user object with error messages
            return modelAndView;  // Stay on the registration page
        }

      
       
        if (user.getPhone() == null || !user.getPhone().matches("\\d{10}")) {
            result.rejectValue("phone", "error.phone", "Phone number must be 10 digits");
            modelAndView.addObject("user", user);  // Re-add the user object with error messages
            return modelAndView;  // Stay on the registration page
        }


        // Save user to the database
        registerService.saveRegister(user);

        // Add a success message
        modelAndView.addObject("message", "Registration successful!");
        modelAndView.setViewName("register"); // Redirect to the success page
        return modelAndView; // You can redirect to a login page or another success page
    }

    @GetMapping("/login")
    public ModelAndView showLoginForm() {
        return new ModelAndView("index");
    }


    @PostMapping("/login")
    public ModelAndView loginUser(@RequestParam("username") String username,
                                   @RequestParam("password") String password, 
                                   HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("index");

        // Find the user from the database or service
        Register getuser = registerService.findByUsername(username);

        // Check if the user exists
        if (getuser == null) {
            modelAndView.addObject("error", "Username does not exist.");
            modelAndView.addObject("username", username);  // Pass username to retain in the form
            return modelAndView;
        }

        // Check if password matches
        if (!getuser.getPassword().equals(password)) {
            modelAndView.addObject("error1", "Invalid username or password.");
            modelAndView.addObject("username", username);  // Pass username to retain in the form
            return modelAndView;
        }

        // Set user in session
        session.setAttribute("user", getuser);
        modelAndView.addObject("getuser", getuser); 

        // Successful login, redirect to the booking page
        modelAndView.setViewName("redirect:/Book-ticket");
        return modelAndView;
    }

    @PostMapping("/forget-password")
    public ModelAndView resetPassword(@RequestParam("email") String email,
                                      @RequestParam("new-password") String newPassword,
                                      @RequestParam("confirm-password") String confirmPassword,
                                      Model model) {
        ModelAndView modelAndView = new ModelAndView("forget-password");

        // Check if passwords match
        if (!newPassword.equals(confirmPassword)) {
            modelAndView.addObject("error", "Passwords do not match.");
            return modelAndView;
        }

        // Find user by email
        Register user = registerService.findByEmail(email);

        if (user == null) {
            modelAndView.addObject("error", "No user found with that email address.");
            return modelAndView;
        }

        // Update the password
        user.setPassword(newPassword);

        if (newPassword.length() < 6) {
            modelAndView.addObject("error", "Password must be at least 6 characters.");
            return modelAndView;
        }
        registerService.UpdateUser(user);

        modelAndView.addObject("success", "Password reset successfully.");
        return modelAndView;
    }
    
    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable Long id, 
    		@ModelAttribute("update") Register update, HttpSession session )
    {
    	Register user=registerService.getUserById(id);
    
    	user.setUsername(update.getUsername());
    	user.setEmail(update.getEmail());
    	user.setDob(update.getDob());
    	user.setPassword(update.getPassword());
    	user.setPhone(update.getPhone());
    	user.setGender(update.getGender());
    	
    	session.setAttribute("user",user);
    	
    	registerService.UpdateUser(user);
    	return "redirect:/Dashboard";
    }
    
   
  
}
