package org.matt.drinkrecommenderapi.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuestionDTO {
    String questionName;
    String questionText;
    List<String> choiceList;

    public QuestionDTO(String questionName, String questionText, List<String> choiceList) {
        this.questionName = questionName;
        this.questionText = questionText;
        this.choiceList = choiceList;
    }
}
