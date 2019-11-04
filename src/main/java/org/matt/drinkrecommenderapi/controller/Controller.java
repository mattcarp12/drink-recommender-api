package org.matt.drinkrecommenderapi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.matt.drinkrecommenderapi.evaluator.DrinkModelEvaluator;
import org.matt.drinkrecommenderapi.model.Question;
import org.matt.drinkrecommenderapi.model.Survey;
import org.matt.drinkrecommenderapi.model.SurveyResponse;
import org.matt.drinkrecommenderapi.model.dto.QuestionDTO;
import org.matt.drinkrecommenderapi.model.dto.SurveyResponseDTO;
import org.matt.drinkrecommenderapi.repository.QuestionRepository;
import org.matt.drinkrecommenderapi.repository.SurveyRepository;
import org.matt.drinkrecommenderapi.repository.SurveyResponseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	private final int MODEL_TRAIN_LIMIT = 10;

	QuestionRepository questionRepository;
	SurveyResponseRepository surveyResponseRepository;
	SurveyRepository surveyRepository;
	DrinkModelEvaluator evaluator;
	StringRedisTemplate redisTemplate;
	int counter;
	Logger logger = LoggerFactory.getLogger(this.getClass());

	public Controller(QuestionRepository questionRepository, SurveyResponseRepository surveyResponseRepository,
			SurveyRepository surveyRepository, DrinkModelEvaluator evaluator, StringRedisTemplate redisTemplate) {
		this.questionRepository = questionRepository;
		this.surveyResponseRepository = surveyResponseRepository;
		this.surveyRepository = surveyRepository;
		this.evaluator = evaluator;
		this.redisTemplate = redisTemplate;
		counter = 0;
	}

	@GetMapping("/drink-recommender")
	public String getDrinkRecommendation(@RequestParam Map<String, String> allRequestParams) {
		return evaluator.getPrediction(allRequestParams);
	}

	@GetMapping("/question")
	public List<QuestionDTO> getQuestions() {
		List<Question> questions = questionRepository.findAll();
		List<QuestionDTO> questionDTOList = new ArrayList<>();
		questions.forEach(question -> {
			if (!question.getQuestionName().equals("drink") && !question.getQuestionName().equals("dayofweek")) {
				questionDTOList.add(new QuestionDTO(question.getQuestionName(), question.getQuestionText(),
						question.getQuestionChoiceStringList()));
			}
		});
		return questionDTOList;
	}

	@PostMapping("/survey-response")
	public String createSurveyResponse(@RequestBody SurveyResponseDTO surveyResponseDTO) {
		List<SurveyResponse> surveyResponseList = new ArrayList<>();
		Survey survey = surveyRepository.save(new Survey());
		surveyResponseDTO.getAnswers().forEach((question, choice) -> {
			SurveyResponse surveyResponse = new SurveyResponse();
			surveyResponse.setSurvey(survey);
			surveyResponse.setQuestion(questionRepository.findByQuestionName(question));
			surveyResponse.setQuestionChoice(questionRepository.findByQuestionName(question).getQuestionChoiceByChoiceString(choice));
			surveyResponseList.add(surveyResponse);
		});

		logger.info("Counter = " + ++counter);
		
		if (counter > MODEL_TRAIN_LIMIT) {
			redisTemplate.convertAndSend("trainModel", "trainModel");
			counter = 0;
		}
		
		surveyResponseRepository.saveAll(surveyResponseList);
		return "Survey response saved to database";
		
	}
}
