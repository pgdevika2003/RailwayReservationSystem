package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Register;

public interface RegisterRepo extends JpaRepository<Register, Long> {

    Register findByUsername(String username);
   
    Register findByEmail(String email);

	Optional<Register> findById(Long id);
   
}
