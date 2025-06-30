package com.example.demo.controller;

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
import com.example.demo.model.Register;
import com.example.demo.model.Train;
import com.example.demo.service.RegisterServiceImpl;
import com.example.demo.service.TrainServiceImpl;
import com.example.demo.model.Admin;
import com.example.demo.service.AdminServiceImpl;


import jakarta.servlet.http.HttpSession;

import jakarta.validation.Valid;

@Controller
public class AdminController {

	 @Autowired
	    private AdminServiceImpl adminService;
	 @Autowired
	    private RegisterServiceImpl userService;
	 @Autowired
	    private TrainServiceImpl trainService;
	 
	  @GetMapping("/admin/AdminLogin")
	    public ModelAndView showLoginForm() {
	        return new ModelAndView("admin/AdminLogin");
	    }
	 
	  @PostMapping("/admin/AdminLogin")
	    public ModelAndView loginUser(@RequestParam("username") String username,
	                                   @RequestParam("password") String password, 
	                                   HttpSession session) {
	        ModelAndView modelAndView = new ModelAndView("admin/AdminLogin");

	        // Find the user from the database or service
	        Admin getuser = adminService.findByUsername(username);

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
	        modelAndView.setViewName("redirect:/admin/AdminHome");
	        return modelAndView;
	    }
	  
	  @GetMapping("/admin/logout")
	    public ModelAndView showlogout() {
	        return new ModelAndView("admin/AdminLogin");
	    }
	  
	  @GetMapping("/admin/edit/{id}")
	  public String editUserForm(@PathVariable("id") Long id, Model model) {
		
	        
	            Register user = userService.getUserById(id);
	            model.addAttribute("user", user); // Add the user from the database to the model
	            return "editProfile";
	       
	  }

	  @GetMapping("/admin/AdminHome")
	    public ModelAndView showAdminHome() {
	        return new ModelAndView("admin/AdminHome");
	    }
	  
	  @GetMapping("/admin/UserDetails")
	    public ModelAndView showUserdetails() {
		     ModelAndView modelAndView = new ModelAndView("admin/UserDetails");
	        List<Register> users = userService.getAllUsers();
	        modelAndView.addObject("users", users); // Pass the list of users to the view
	        return modelAndView;
	    }
	  @GetMapping("/admin/deleteUser")
	    public ModelAndView deleteUser(@RequestParam("id") Long id) {
		  ModelAndView modelAndView = new ModelAndView("admin/UserDetails");
		  
	        userService.deleteUser(id);
	        List<Register> users= userService.getAllUsers();
	        modelAndView.addObject("message", "User deleted successfully");
	        modelAndView.addObject("users", users);
	        return modelAndView; // After deleting, redirect to the user list
	    }
	  
	
	  @GetMapping("/admin/ViewTrain")
	  public ModelAndView getAllTrains() {
	      List<Train> trains = trainService.getAllTrains();
	      ModelAndView modelAndView = new ModelAndView("admin/ViewTrain");
	     
	      modelAndView.addObject("trains", trains); 
	      return modelAndView;
	  }

	  
	  @GetMapping("/admin/AdminSearchTrain")
	    public ModelAndView showTrainEnquiry() {
	        return new ModelAndView("admin/AdminSearchTrain");
	    }
	  
	  @PostMapping("/admin/searchtrain")
	    public ModelAndView searchTrainByNumber(@RequestParam("trainId") String trainIdStr ) {
		  ModelAndView modelAndView = new ModelAndView("admin/AdminSearchTrain");
		  try {
		        Long trainId = Long.parseLong(trainIdStr); // ← this will succeed if input is valid number

		        Train train = trainService.getTrainById(trainId);
		        if (train == null) {
		            modelAndView.addObject("error", "Train Number does not exist.");
		        } else {
		            modelAndView.addObject("train", train);
		        }

		    } catch (NumberFormatException e) {
		        modelAndView.addObject("error", "Invalid Train Number format.");
		    }

		    return modelAndView;
	    }
	  
	  @GetMapping("/admin/AddTrain")
	    public ModelAndView showaddtrain() {
		  ModelAndView modelAndView = new ModelAndView("admin/AddTrain");
		  modelAndView.addObject("train", new Train());
          return modelAndView;
	    }
	  @PostMapping("/admin/addtrain")
	  public ModelAndView addTrain(@Valid Train train, BindingResult bindingResult) {
	      ModelAndView mav = new ModelAndView("admin/AddTrain");

	      // Check if the train name already exists
	      if (trainService.isTrain_nameTaken(train.getTrainName())) {
	          bindingResult.rejectValue("trainName", "error.trainName", "Train name is already taken");
	          mav.addObject("train", train);  // Re-add the train object to maintain form state
	          return mav;
	      }

	      // Check for validation errors
	      if (bindingResult.hasErrors()) {
	          mav.addObject("train", train);
	          mav.addObject("errors", bindingResult.getAllErrors());  // All errors will be available for the view
	          return mav;
	      }

	    
	      // Proceed to add the train if no issues
	      trainService.addTrain(train);
	      mav.addObject("message", "Train added successfully!");
	      // Set the view name for the success page
	      mav.setViewName("/admin/AddTrain");
      
	      return mav;
	  }

	// This method will show the form to enter the train number for deletion
	// GET method to show the form for deleting the train
	  @GetMapping("/admin/DeleteTrain")
	  public ModelAndView showRemoveTrainForm() {
	      ModelAndView modelAndView = new ModelAndView("admin/DeleteTrain");
	      return modelAndView;
	  }

	  // POST method to handle the deletion after the form submission
	  @PostMapping("/admin/DeleteTrain")
	  public ModelAndView deleteTrain(@RequestParam("train_id") Long train_id) {
	      ModelAndView modelAndView = new ModelAndView();

	      // Attempt to find the train by its train_id
	      Train train = trainService.getTrainById(train_id); // Look up by train_id

	      if (train != null) {
	          // If train exists, delete it
	          trainService.deleteTrain(train.getTrain_id()); // Delete using train_id
	          modelAndView.addObject("message", "Train cancelled successfully!");
	      } else {
	          // If train does not exist, show an error
	          modelAndView.addObject("error", "Train ID does not exist.");
	      }

	      // After performing the action, return the user to the delete train page with feedback
	      modelAndView.setViewName("admin/DeleteTrain");
	      return modelAndView;
	  }


	  @GetMapping("/admin/trainupdation/{train_id}")
	  public ModelAndView showUpdateForm(@PathVariable Long train_id) {
		    Train train = trainService.getTrainById(train_id);  // Fetch the train object using train_id
		    ModelAndView modelAndView = new ModelAndView("admin/UpdateTrain");
		    
		    if (train != null) {
		        modelAndView.addObject("train", train);  // Add the train to the model
		        modelAndView.setViewName("admin/UpdateTrain");  // The name of the Thymeleaf template
		    } else {
		        modelAndView.addObject("error", "Train not found");
		        modelAndView.setViewName("admin/ViewTrain");  // Set error view if train not found
		    }

		    return modelAndView;
		}

	  @PostMapping("/admin/trainupdation/{train_id}")
	  public ModelAndView updateTrain(@PathVariable Long train_id, @ModelAttribute Train update, BindingResult bindingResult) {
	      ModelAndView modelAndView = new ModelAndView("admin/UpdateTrain");
	      
	      // Fetch the existing train record by ID
	      Train train = trainService.getTrainById(train_id);
	      if (train == null) {
	          modelAndView.addObject("message", "Train not found.");
	          modelAndView.setViewName("admin/UpdateTrain");
	          return modelAndView;
	      }

	      // Check for validation errors
	      if (bindingResult.hasErrors()) {
	          return new ModelAndView("admin/UpdateTrain");
	      }

	      // Process update (your existing logic here)
	      if (train.getTrain_id() == train_id) {
	          train.setTrainName(update.getTrainName());
	          train.setTrain_type(update.getTrain_type());  // Ensure this field is set
	          train.setSource_station(update.getSource_station());
	          train.setDestination_station(update.getDestination_station());
	          train.setDeparture_time(update.getDeparture_time());
	          train.setArrival_time(update.getArrival_time());
	          train.setTotal_seats(update.getTotal_seats());
	          train.setFare(update.getFare()); 
	          trainService.updateTrain(train);

	          modelAndView.addObject("message", "Train updated successfully!");
	          modelAndView.setViewName("admin/UpdateTrain");
	      }

	      return modelAndView;
	  }

	 
}
