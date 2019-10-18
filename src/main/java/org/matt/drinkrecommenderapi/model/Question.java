package org.matt.drinkrecommenderapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "questions")
public class Question {

    @Id
    private String questionName;
    private String questionText;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<QuestionChoice> questionChoices;

    public List<String> getQuestionChoiceList() {
        List<String> questionChoiceList = new ArrayList<>();
        for (QuestionChoice questionChoice : questionChoices) {
            questionChoiceList.add(questionChoice.getChoice());
        }
        return questionChoiceList;
    }

    public QuestionChoice getQuestionChoiceByChoiceString(String choice) {
        for (QuestionChoice questionChoice : questionChoices) {
            if (questionChoice.getChoice().equals(choice)) return questionChoice;
        }
        return null;
    }
}
