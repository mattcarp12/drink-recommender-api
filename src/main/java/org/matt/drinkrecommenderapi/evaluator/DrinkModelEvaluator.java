package org.matt.drinkrecommenderapi.evaluator;

import java.io.ByteArrayInputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import org.dmg.pmml.FieldName;
import org.jpmml.evaluator.Evaluator;
import org.jpmml.evaluator.EvaluatorUtil;
import org.jpmml.evaluator.FieldValue;
import org.jpmml.evaluator.InputField;
import org.jpmml.evaluator.LoadingModelEvaluatorBuilder;
import org.jpmml.evaluator.visitors.DefaultVisitorBattery;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

public class DrinkModelEvaluator {

	private Evaluator evaluator;
	private final StringRedisTemplate redisTemplate;
	private final RedisMessageListenerContainer listenerContainer;
	private String model;

	public DrinkModelEvaluator(StringRedisTemplate redisTemplate, RedisMessageListenerContainer listenerContainer)
			throws Exception {
		this.redisTemplate = redisTemplate;
		this.listenerContainer = listenerContainer;
		trainModel();
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

	private void updateEvaluator() throws Exception {
		this.evaluator = new LoadingModelEvaluatorBuilder().setLocatable(false).setVisitors(new DefaultVisitorBattery())
				.load(new ByteArrayInputStream(model.getBytes())).build();
	}

	public void trainModel() {
		// Send a message to train the model
		redisTemplate.convertAndSend("trainModel", "trainModel");
	}

	public void setModel(String model) throws Exception {
		this.model = model;
		updateEvaluator();
	}
}
