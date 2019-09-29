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
            //FieldValue fieldValue = inputField.prepare(flowerFeatures.features.get(inputName.getValue()));
            //arguments.put(inputName, fieldValue);


            switch (inputName.getValue()) {
                case "Petal Length": // Petal Length
                    arguments.put(inputName, inputField.prepare(flowerFeatures.petalLength));
                    break;
                case "Petal Width": // Petal Width
                    arguments.put(inputName, inputField.prepare(flowerFeatures.petalWidth));
                    break;
                case "Sepal Length": // Sepal Length
                    arguments.put(inputName, inputField.prepare(flowerFeatures.sepalLength));
                    break;
                case "Sepal Width": // Sepal Width
                    arguments.put(inputName, inputField.prepare(flowerFeatures.sepalWidth));
                    break;
            }


        }
        Map<String, ?> results = EvaluatorUtil.decodeAll(evaluator.evaluate(arguments));
        String prediction = (String) results.get("Species");
        System.out.println("prediction is " + prediction);
        return prediction;
    }


}
