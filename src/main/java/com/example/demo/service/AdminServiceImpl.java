package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Admin;

import com.example.demo.repository.AdminRepo;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminRepo adminrepo;

	@Override
	public Admin findByUsername(String username) {
		return adminrepo.findByUsername(username);
	}

	
}
