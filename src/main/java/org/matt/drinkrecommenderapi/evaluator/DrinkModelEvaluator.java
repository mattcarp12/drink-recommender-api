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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public class DrinkModelEvaluator implements MessageListener {

	private Evaluator evaluator;
	private final StringRedisTemplate redisTemplate;
	//private final RedisMessageListenerContainer listenerContainer;
	Logger logger = LoggerFactory.getLogger(this.getClass());

	public DrinkModelEvaluator(StringRedisTemplate redisTemplate, RedisMessageListenerContainer listenerContainer)
			throws Exception {
		this.redisTemplate = redisTemplate;
		//this.listenerContainer = listenerContainer;
		listenerContainer.addMessageListener(this, new ChannelTopic("transferModel"));
		updateModel();
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
	
	public void updateModel() throws Exception {
		String model = redisTemplate.opsForValue().get("MODEL");
		this.evaluator = new LoadingModelEvaluatorBuilder()
				.setLocatable(false)
				.setVisitors(new DefaultVisitorBattery())
				.load(new ByteArrayInputStream(model.getBytes()))
				.build();
	}

	@Override
	public void onMessage(Message message, byte[] pattern) {
		if (message.toString().equals("transferModel")) {
			try {
				updateModel();
			} catch (Exception e) {
				logger.error(e.getLocalizedMessage());
			}
		}

	}
}
