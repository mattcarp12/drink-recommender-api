package org.matt.drinkrecommenderapi;

import java.net.URI;

import org.matt.drinkrecommenderapi.evaluator.DrinkModelEvaluator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@SpringBootApplication
public class DrinkRecommenderApiApplication {
	
	@Value("${spring.redis.url}")
	URI redisURI;

	public static void main(String[] args) {
		SpringApplication.run(DrinkRecommenderApiApplication.class, args);
	}

	/*
	 * @Bean static DrinkModelEvaluator getDrinkModelEvaluator(StringRedisTemplate
	 * redisTemplate, RedisMessageListenerContainer container) throws Exception {
	 * return new DrinkModelEvaluator(redisTemplate, container); }
	 */
	
	@Bean
	RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		return container;
	}

	@Bean
	StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
		return new StringRedisTemplate(connectionFactory);
	}

	@Bean
	public RedisConnectionFactory jedisConnectionFactory() {
		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
		config.setHostName(redisURI.getHost());
		config.setPort(redisURI.getPort());
		if (!redisURI.getHost().equals("localhost"))
			config.setPassword(redisURI.getUserInfo().substring(2));
		return new JedisConnectionFactory(config);
	}

}
