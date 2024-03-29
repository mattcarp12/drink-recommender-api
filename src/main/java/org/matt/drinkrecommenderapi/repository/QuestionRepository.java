package org.matt.drinkrecommenderapi.repository;

import org.matt.drinkrecommenderapi.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, String> {
	Question findByQuestionName(String questionName);
}

