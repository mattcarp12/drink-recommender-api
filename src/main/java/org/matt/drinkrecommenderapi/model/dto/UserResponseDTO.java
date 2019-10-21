package org.matt.drinkrecommenderapi.model.dto;

import lombok.Data;

import java.util.Map;

@Data
public class UserResponseDTO {
    //int surveyId;
    String user;
    String drink;
    Map<String, String> answers;
}
