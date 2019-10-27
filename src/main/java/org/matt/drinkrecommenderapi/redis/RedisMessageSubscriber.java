package org.matt.drinkrecommenderapi.redis;

import org.matt.drinkrecommenderapi.evaluator.DrinkModelEvaluator;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class RedisMessageSubscriber implements MessageListener {

	private final DrinkModelEvaluator evaluator;

	public RedisMessageSubscriber(DrinkModelEvaluator evaluator) {
		this.evaluator = evaluator;
	}

	@Override
	public void onMessage(Message message, byte[] pattern) {
		try {
			evaluator.setModel(message.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
