package org.matt.drinkrecommenderapi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.matt.drinkrecommenderapi.evaluator.DrinkModelEvaluator;
import org.matt.drinkrecommenderapi.model.Question;
import org.matt.drinkrecommenderapi.model.Session;
import org.matt.drinkrecommenderapi.model.UserResponse;
import org.matt.drinkrecommenderapi.model.dto.QuestionDTO;
import org.matt.drinkrecommenderapi.model.dto.UserResponseDTO;
import org.matt.drinkrecommenderapi.repository.DrinkRepository;
import org.matt.drinkrecommenderapi.repository.QuestionRepository;
import org.matt.drinkrecommenderapi.repository.SessionRepository;
import org.matt.drinkrecommenderapi.repository.UserResponseRepository;
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
	UserResponseRepository userResponseRepository;
	DrinkRepository drinkRepository;
	SessionRepository sessionRepository;
	DrinkModelEvaluator evaluator;
	StringRedisTemplate redisTemplate;
	int counter;
	Logger logger = LoggerFactory.getLogger(this.getClass());

	public Controller(QuestionRepository questionRepository, UserResponseRepository userResponseRepository,
			DrinkRepository drinkRepository, SessionRepository sessionRepository, DrinkModelEvaluator evaluator,
			StringRedisTemplate redisTemplate) {
		this.questionRepository = questionRepository;
		this.userResponseRepository = userResponseRepository;
		this.drinkRepository = drinkRepository;
		this.sessionRepository = sessionRepository;
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
			questionDTOList.add(new QuestionDTO(question.getQuestionName(), question.getQuestionText(),
					question.getQuestionChoiceList()));
		});
		return questionDTOList;
	}

	@PostMapping("/user-response")
	public String createUserResponse(@RequestBody UserResponseDTO userResponseDTO) {
		List<UserResponse> userResponseList = new ArrayList<>();
		Session session = sessionRepository.save(new Session());
		userResponseDTO.getAnswers().forEach((question, choice) -> {
			UserResponse userResponse = new UserResponse();
			userResponse.setSession(session);
			userResponse.setDrink(drinkRepository.getOne(userResponseDTO.getDrink()));
			userResponse.setQuestion(questionRepository.getOne(question));
			userResponse.setQuestionChoice(questionRepository.getOne(question).getQuestionChoiceByChoiceString(choice));
			userResponseList.add(userResponse);
		});

		logger.info("Counter = %d", counter++);
		
		if (counter > MODEL_TRAIN_LIMIT) {
			redisTemplate.convertAndSend("trainModel", "trainModel");
			counter = 0;
		}
		
		userResponseRepository.saveAll(userResponseList);
		return "User data saved to database";
		
	}
}
