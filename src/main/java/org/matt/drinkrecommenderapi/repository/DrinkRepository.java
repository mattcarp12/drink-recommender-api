package com.example.demo.repository;

import com.example.demo.entities.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrinkRepository extends JpaRepository<Drink, String> {
}
