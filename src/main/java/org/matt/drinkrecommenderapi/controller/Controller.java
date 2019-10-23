package org.matt.drinkrecommenderapi.controller;

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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class Controller {

    QuestionRepository questionRepository;
    UserResponseRepository userResponseRepository;
    DrinkRepository drinkRepository;
    SessionRepository sessionRepository;
    DrinkModelEvaluator evaluator;

    public Controller(QuestionRepository questionRepository,
                      UserResponseRepository userResponseRepository,
                      DrinkRepository drinkRepository,
                      SessionRepository sessionRepository,
                      DrinkModelEvaluator evaluator) {
        this.questionRepository = questionRepository;
        this.userResponseRepository = userResponseRepository;
        this.drinkRepository = drinkRepository;
        this.sessionRepository = sessionRepository;
        this.evaluator = evaluator;
    }

    @GetMapping("/drink-recommender")
    public String getDrinkRecommendation(
            @RequestParam Map<String, String> allRequestParams
    ) {
        return evaluator.getPrediction(allRequestParams);
    }

    @GetMapping("/question")
    public List<QuestionDTO> getQuestions() {
        List<Question> questions = questionRepository.findAll();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        questions.forEach(question -> {
            questionDTOList.add(new QuestionDTO(question.getQuestionName(), question.getQuestionText(), question.getQuestionChoiceList()));
        });
        return questionDTOList;
    }

    @PostMapping("/user-response")
    public List<UserResponse> createUserResponse(@RequestBody UserResponseDTO userResponseDTO) {
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
        return userResponseRepository.saveAll(userResponseList);
    }
}

