package org.matt.drinkrecommenderapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    @Id
    int id;

    @ManyToOne(targetEntity = Question.class)
    int question_id;

    int question_choice_id;


    int drink_id;
}
