package org.matt.drinkrecommenderapi.Model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.matt.drinkrecommenderapi.model.FlowerFeatures;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@JsonTest
@RunWith(SpringRunner.class)
public class FlowerFeaturesTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void TestFlowerFeaturesCreation() {
        Map<String, Double> features = new HashMap<>();
        features.put("Petal Length", 5.1);
        features.put("Petal Width", 3.5);
        features.put("Sepal Length", 1.4);
        features.put("Sepal Width", 0.7);

        FlowerFeatures flowerFeatures = new FlowerFeatures(features);
    }

    @Test
    public void TestFlowerFeaturesDeserialization() throws IOException {
        String featuresJson = "{" +
                "  \"petalLength\":5.1," +
                "  \"petalWidth\":3.5," +
                "  \"sepalLength\":1.4," +
                "  \"sepalWidth\":2" + "}";
        FlowerFeatures flowerFeatures = objectMapper.readValue(featuresJson, FlowerFeatures.class);
        System.out.println(flowerFeatures);

    }

}
