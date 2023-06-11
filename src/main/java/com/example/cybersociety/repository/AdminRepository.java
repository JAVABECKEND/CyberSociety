package com.example.cybersociety.repository;

import com.example.cybersociety.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin,Integer> {

    Optional<Admin> findByName(String username);
}
