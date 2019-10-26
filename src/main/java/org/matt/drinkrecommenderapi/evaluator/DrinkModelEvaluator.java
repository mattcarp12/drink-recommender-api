package org.matt.drinkrecommenderapi.evaluator;

import org.dmg.pmml.FieldName;
import org.jpmml.evaluator.*;
import org.jpmml.evaluator.visitors.DefaultVisitorBattery;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.ByteArrayInputStream;
import java.util.LinkedHashMap;
import java.util.Map;

public class DrinkModelEvaluator {

    private final WebClient modelClient;
    private Evaluator evaluator;


    public DrinkModelEvaluator() throws Exception {
        modelClient = WebClient.create("https://localhost:5000");
        getModel();
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

    private void loadEvaluator(String model) throws Exception {
        this.evaluator = new LoadingModelEvaluatorBuilder()
                .setLocatable(false)
                .setVisitors(new DefaultVisitorBattery())
                .load(new ByteArrayInputStream(model.getBytes()))
                .build();
    }

    public void getModel() throws Exception {
        modelClient
                .get()
                .uri("/trainer")
                .retrieve()
                .bodyToFlux(String.class)
                .subscribe(model -> {
                    try {
                        loadEvaluator(model);
                    } catch (Exception e) {
                        e.getLocalizedMessage();
                    }
                });
    }


}
