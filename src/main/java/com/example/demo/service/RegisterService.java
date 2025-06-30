package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Register;

public interface RegisterService {

	Register saveRegister(Register register);
	Register getUserById(Long id);
    public Register findByUsername(String username);
    Register findByEmail(String email);
    boolean isUsernameTaken(String username);
    boolean isEmailTaken(String email);
    Register UpdateUser(Register user);
    public void deleteUser(Long userId);
    public List<Register> getAllUsers();
}
