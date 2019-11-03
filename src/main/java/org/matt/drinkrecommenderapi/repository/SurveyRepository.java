package org.matt.drinkrecommenderapi.repository;

import org.matt.drinkrecommenderapi.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey, Integer> {
}
