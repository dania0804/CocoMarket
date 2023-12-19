package com.example.pidevcocomarket.repositories;

import com.example.pidevcocomarket.entities.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
}