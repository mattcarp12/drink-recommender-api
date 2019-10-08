package org.matt.drinkrecommenderapi.evaluator;

import org.dmg.pmml.FieldName;
import org.jpmml.evaluator.*;
import org.jpmml.evaluator.visitors.DefaultVisitorBattery;
import org.matt.drinkrecommenderapi.model.FlowerFeatures;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

public class IrisEvaluator {

    Evaluator evaluator;

    public IrisEvaluator() throws Exception {
        evaluator = new LoadingModelEvaluatorBuilder()
                .setLocatable(false)
                .setVisitors(new DefaultVisitorBattery())
                //.load(new File(getClass().getClassLoader().getResource("python/model.pmml").getFile()))
                .load(new ClassPathResource("python/model.pmml").getInputStream())
                .build();
        System.out.println("evaluator initialized");
    }


    public String getPrediction(FlowerFeatures flowerFeatures) {
        Map<FieldName, FieldValue> arguments = new LinkedHashMap<>();
        FieldName fn;
        FieldValue fv;
        for (InputField inputField : evaluator.getInputFields()) {
            fn = inputField.getName();
            fv = inputField.prepare(flowerFeatures.features.get(fn.getValue()));
            arguments.put(fn, fv);
        }
        Map<String, ?> results = EvaluatorUtil.decodeAll(evaluator.evaluate(arguments));
        String prediction = (String) results.get("Species");
        System.out.println("prediction is " + prediction);
        return prediction;
    }
}
