package org.matt.drinkrecommenderapi;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.dbcp.BasicDataSource;
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

	/*
	 * @Bean public BasicDataSource dataSource() throws URISyntaxException { String
	 * database_url = System.getenv("DATABASE_URL") == null ?
	 * "postgres://postgres:postgres@localhost:5432/postgres" :
	 * System.getenv("DATABASE_URL"); URI dbUri = new URI(database_url);
	 * 
	 * String username = dbUri.getUserInfo().split(":")[0]; String password =
	 * dbUri.getUserInfo().split(":")[1]; String dbUrl = "jdbc:postgresql://" +
	 * dbUri.getHost() + dbUri.getPath();
	 * 
	 * BasicDataSource basicDataSource = new BasicDataSource();
	 * basicDataSource.setUrl(dbUrl); basicDataSource.setUsername(username);
	 * basicDataSource.setPassword(password);
	 * 
	 * return basicDataSource; }
	 */

}
