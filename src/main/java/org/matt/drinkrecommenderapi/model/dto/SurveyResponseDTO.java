package org.matt.drinkrecommenderapi.model.dto;

import lombok.Data;

import java.util.Map;

@Data
public class SurveyResponseDTO {
    Map<String, String> answers;
}
