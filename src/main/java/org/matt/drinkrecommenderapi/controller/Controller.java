package org.matt.drinkrecommenderapi.controller;

import org.jpmml.evaluator.Evaluator;
import org.matt.drinkrecommenderapi.evaluator.DrinkModelEvaluator;
import org.matt.drinkrecommenderapi.model.Question;
import org.matt.drinkrecommenderapi.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class Controller {

    private final QuestionRepository questionRepository;
    private final DrinkModelEvaluator evaluator;

    public Controller(DrinkModelEvaluator evaluator, QuestionRepository questionRepository) throws Exception {
        this.evaluator = evaluator;
        this.questionRepository = questionRepository;
    }

    @GetMapping("/drink-recommender")
    public String getDrinkRecommendation(
            @RequestParam Map<String, String> allRequestParams
    ) {
        return evaluator.getPrediction(allRequestParams);
    }

    @GetMapping("/question")
    public List<Question> getQuestions() {
        return questionRepository.findAll();
    }

/*    @GetMapping("/question/{name}")
    public Optional<Question> getQuestion(@PathVariable String name) {
        return questionRepository.findById(name);
    }*/

    @PostMapping("/question")
    public Question createQuestion(@RequestBody Question question) {
        return questionRepository.save(question);
    }


}
