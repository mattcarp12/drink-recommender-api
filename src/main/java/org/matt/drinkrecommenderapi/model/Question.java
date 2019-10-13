package org.matt.drinkrecommenderapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "questions")
public class Question {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String questionName;
    private String questionText;
    @ElementCollection
    @CollectionTable(name = "question_choices", joinColumns = @JoinColumn(name = "question_id"))
    Set<String> questionChoices = new HashSet<>();
}
