package org.matt.drinkrecommenderapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "survey_responses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveyResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn
    Survey survey;

    @ManyToOne
    @JoinColumn(name = "question_name")
    Question question;

    @ManyToOne
    @JoinColumn(name = "question_choice")
    QuestionChoice questionChoice;

}
