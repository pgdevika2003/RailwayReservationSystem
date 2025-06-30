package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.demo.model.Register;
import com.example.demo.repository.RegisterRepo;



@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RegisterRepo regrepo;
   

    @Override
    public Register saveRegister(Register register) {
       
        return regrepo.save(register);
        
    }

    @Override
    public boolean isUsernameTaken(String username) {
        return regrepo.findByUsername(username) != null;
    }

    @Override
    public boolean isEmailTaken(String email) {
        return regrepo.findByEmail(email) != null;
    }

	
	
	@Override
	public Register findByUsername(String username) {
		// TODO Auto-generated method stub
		return regrepo.findByUsername(username);
	}

	@Override
	public Register UpdateUser(Register user) {
		// TODO Auto-generated method stub
		return regrepo.save(user);
	}

	
	@Override
	public Register getUserById(Long id) {
		// TODO Auto-generated method stub
		return regrepo.findById(id).get();
	}

	@Override
	public Register findByEmail(String email) {
		// TODO Auto-generated method stub
		return regrepo.findByEmail(email);
	}

	@Override
	public java.util.List<Register> getAllUsers() {
		// TODO Auto-generated method stub
		return regrepo.findAll();
	}

	@Override
	public void deleteUser(Long userId) {
		// TODO Auto-generated method stub
		Register user = regrepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		regrepo.delete(user);
	}

	
		
}

	

   

