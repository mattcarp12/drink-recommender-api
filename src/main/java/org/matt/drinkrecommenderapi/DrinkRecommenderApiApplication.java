package org.matt.drinkrecommenderapi;

import java.net.URI;
import java.net.URISyntaxException;

import org.matt.drinkrecommenderapi.evaluator.DrinkModelEvaluator;
import org.matt.drinkrecommenderapi.redis.RedisMessageSubscriber;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@SpringBootApplication
public class DrinkRecommenderApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DrinkRecommenderApiApplication.class, args);
	}

	@Bean
	static DrinkModelEvaluator getDrinkModelEvaluator(StringRedisTemplate redisTemplate, 
			RedisMessageListenerContainer container) throws Exception {
		return new DrinkModelEvaluator(redisTemplate, container);
	}

	@Bean
	public RedisConnectionFactory jedisConnectionFactory() {
		/*
		 * JedisPoolConfig poolConfig = new JedisPoolConfig();
		 * poolConfig.setMaxTotal(10); poolConfig.setMaxIdle(5);
		 * poolConfig.setMinIdle(1); poolConfig.setTestOnBorrow(true);
		 * poolConfig.setTestOnReturn(true); poolConfig.setTestWhileIdle(true);
		 * JedisConnectionFactory jedisConFactory = new
		 * JedisConnectionFactory(poolConfig);
		 */
		return new JedisConnectionFactory();
	}

	public static JedisPool getPool() throws URISyntaxException {
		URI redisURI = new URI(System.getenv("REDIS_URL"));
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(10);
		poolConfig.setMaxIdle(5);
		poolConfig.setMinIdle(1);
		poolConfig.setTestOnBorrow(true);
		poolConfig.setTestOnReturn(true);
		poolConfig.setTestWhileIdle(true);
		return new JedisPool(poolConfig, redisURI);
	}

	@Bean
	MessageListenerAdapter messageListener(DrinkModelEvaluator evaluator) {
		return new MessageListenerAdapter(new RedisMessageSubscriber(evaluator));
	}

	@Bean
	RedisMessageListenerContainer redisContainer(RedisConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(listenerAdapter, new PatternTopic("updateModel"));
		return container;
	}

	@Bean
	StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
		return new StringRedisTemplate(connectionFactory);
	}

}
