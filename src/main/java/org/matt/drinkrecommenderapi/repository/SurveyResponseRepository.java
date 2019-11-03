package org.matt.drinkrecommenderapi.repository;

import org.matt.drinkrecommenderapi.model.SurveyResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyResponseRepository extends JpaRepository<SurveyResponse, Integer> {
}
