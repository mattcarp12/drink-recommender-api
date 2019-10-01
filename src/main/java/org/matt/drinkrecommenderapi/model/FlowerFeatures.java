package org.matt.drinkrecommenderapi.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@JsonDeserialize(using = FlowerFeaturesDeserializer.class)
public class FlowerFeatures {
    public Map<String, Double> features;

    public FlowerFeatures(Map<String, Double> features) {
        this.features = features;
    }
}

class FlowerFeaturesDeserializer extends JsonDeserializer<FlowerFeatures> {
    @Override
    public FlowerFeatures deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Map<String, Double> features = new HashMap<>();
        while (!p.isClosed()) {
            JsonToken jt = p.nextToken();
            if (JsonToken.FIELD_NAME.equals(jt)) {
                String fieldName = p.getCurrentName();
                p.nextToken();
                Double value = p.getValueAsDouble();
                features.put(fieldName, value);
            }
        }
        return new FlowerFeatures(features);
    }
}