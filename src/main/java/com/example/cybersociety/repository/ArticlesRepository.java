package com.example.cybersociety.repository;

import com.example.cybersociety.entity.Articles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticlesRepository extends JpaRepository<Articles,Integer> {
}
