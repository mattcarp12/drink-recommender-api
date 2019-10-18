package org.matt.drinkrecommenderapi.repository;

import org.matt.drinkrecommenderapi.model.QuestionChoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionChoiceRepository extends JpaRepository<QuestionChoice, Integer> {
}
