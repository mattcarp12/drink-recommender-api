package org.matt.drinkrecommenderapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import org.hibernate.validator.constraints.UniqueElements;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "questions")
public class Question {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
	
	@Column(unique = true)
    private String questionName;
    private String questionText;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<QuestionChoice> questionChoices;

    public List<String> getQuestionChoiceStringList() {
        List<String> questionChoiceList = new ArrayList<>();
        for (QuestionChoice questionChoice : this.questionChoices) {
            questionChoiceList.add(questionChoice.getChoice());
        }
        return questionChoiceList;
    }

    public QuestionChoice getQuestionChoiceByChoiceString(String choice) {
        for (QuestionChoice questionChoice : this.questionChoices) {
            if (questionChoice.getChoice().equals(choice)) return questionChoice;
        }
        return null;
    }
}
