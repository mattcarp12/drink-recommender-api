package org.matt.drinkrecommenderapi;

import org.matt.drinkrecommenderapi.evaluator.DrinkModelEvaluator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DrinkRecommenderApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DrinkRecommenderApiApplication.class, args);
    }

    @Bean
    static DrinkModelEvaluator getDrinkModelEvaluator() throws Exception {
        return new DrinkModelEvaluator();
    }

}
