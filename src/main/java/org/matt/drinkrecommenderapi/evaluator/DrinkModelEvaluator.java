package org.matt.drinkrecommenderapi.evaluator;

import org.dmg.pmml.FieldName;
import org.jpmml.evaluator.*;
import org.jpmml.evaluator.visitors.DefaultVisitorBattery;
import org.springframework.core.io.ClassPathResource;

import java.util.LinkedHashMap;
import java.util.Map;

public class DrinkModelEvaluator {

    Evaluator evaluator;

    public DrinkModelEvaluator() throws Exception {
        evaluator = new LoadingModelEvaluatorBuilder()
                .setLocatable(false)
                .setVisitors(new DefaultVisitorBattery())
                .load(new ClassPathResource("python/drinkModel.pmml").getInputStream())
                .build();
    }


    public String getPrediction(Map<String, String> parameters) {
        Map<FieldName, FieldValue> arguments = new LinkedHashMap<>();
        FieldName fn;
        FieldValue fv;
        for (InputField inputField : evaluator.getInputFields()) {
            fn = inputField.getName();
            fv = inputField.prepare(parameters.get(fn.getValue()));
            arguments.put(fn, fv);
        }
        Map<String, ?> results = EvaluatorUtil.decodeAll(evaluator.evaluate(arguments));
        return (String) results.get("drink");
    }
}
