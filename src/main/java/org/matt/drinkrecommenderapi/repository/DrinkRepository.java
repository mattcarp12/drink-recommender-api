package org.matt.drinkrecommenderapi.repository;

import org.matt.drinkrecommenderapi.model.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrinkRepository extends JpaRepository<Drink, String> {
}
