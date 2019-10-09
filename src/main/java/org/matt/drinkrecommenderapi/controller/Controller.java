package org.matt.drinkrecommenderapi.controller;

import org.matt.drinkrecommenderapi.evaluator.DrinkModelEvaluator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class Controller {

    private DrinkModelEvaluator evaluator;

    public Controller() throws Exception{
        evaluator = new DrinkModelEvaluator();
    }

    @GetMapping("/drink-recommender")
    public String getDrinkRecommendation(
            @RequestParam Map<String, String> allRequestParams
    ) {
        return evaluator.getPrediction(allRequestParams);
    }

}
