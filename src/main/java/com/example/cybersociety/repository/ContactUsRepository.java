package com.example.cybersociety.repository;

import com.example.cybersociety.entity.ContactUs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactUsRepository  extends JpaRepository<ContactUs , Long> {
}
