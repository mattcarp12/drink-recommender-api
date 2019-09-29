package org.matt.drinkrecommenderapi.evaluator;

import org.dmg.pmml.FieldName;
import org.jpmml.evaluator.*;
import org.jpmml.evaluator.visitors.DefaultVisitorBattery;
import org.matt.drinkrecommenderapi.model.FlowerFeatures;
import org.springframework.core.io.ClassPathResource;

import java.util.LinkedHashMap;
import java.util.Map;

public class IrisEvaluator {

    Evaluator evaluator;

    public IrisEvaluator() throws Exception {
        evaluator = new LoadingModelEvaluatorBuilder()
                .setLocatable(false)
                .setVisitors(new DefaultVisitorBattery())
                .load(new ClassPathResource("python/model.pmml").getFile())
                .build();
        System.out.println("evaluator initialized");
    }


    public String getPrediction(FlowerFeatures flowerFeatures) {
        Map<FieldName, FieldValue> arguments = new LinkedHashMap<>();
        for (InputField inputField : evaluator.getInputFields()) {
            FieldName inputName = inputField.getName();
            switch (inputName.getValue()) {
                case "x1": // Petal Length
                    arguments.put(inputName, inputField.prepare(flowerFeatures.petalLength));
                    break;
                case "x2": // Petal Width
                    arguments.put(inputName, inputField.prepare(flowerFeatures.petalWidth));
                    break;
                case "x3": // Sepal Length
                    arguments.put(inputName, inputField.prepare(flowerFeatures.sepalLength));
                    break;
                case "x4": // Sepal Width
                    arguments.put(inputName, inputField.prepare(flowerFeatures.sepalWidth));
                    break;
            }
        }
        //Map<FieldName, ?> results = evaluator.evaluate(arguments);
        Map<String, ?> results = EvaluatorUtil.decodeAll(evaluator.evaluate(arguments));
        String prediction = (String) results.get("y");
        System.out.println("prediction is " + prediction);
        return prediction;
    }


}
