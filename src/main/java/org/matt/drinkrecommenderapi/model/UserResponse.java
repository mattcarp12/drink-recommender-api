package org.matt.drinkrecommenderapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user_responses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn
    Session session;

    @ManyToOne
    @JoinColumn(name = "drink_name")
    Drink drink;

    @ManyToOne
    @JoinColumn(name = "question_name")
    Question question;

    @ManyToOne
    @JoinColumn(name = "question_choice")
    QuestionChoice questionChoice;

}