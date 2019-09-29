package org.matt.drinkrecommenderapi.controller;

import org.matt.drinkrecommenderapi.evaluator.IrisEvaluator;
import org.matt.drinkrecommenderapi.model.FlowerFeatures;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IrisController {


    private IrisEvaluator modelEvaluator;

    public IrisController() throws Exception {
        modelEvaluator = new IrisEvaluator();
    }

    @GetMapping("/iris")
    public String getSpeciesPrediction(@RequestBody FlowerFeatures flowerFeatures) {
        return modelEvaluator.getPrediction(flowerFeatures);
    }

    @PostMapping("/iris")
    public String postSpeciesPrediction(@RequestBody FlowerFeatures flowerFeatures) {
        return modelEvaluator.getPrediction(flowerFeatures);
    }
}
