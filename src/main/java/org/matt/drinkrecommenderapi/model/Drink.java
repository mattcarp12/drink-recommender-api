package org.matt.drinkrecommenderapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "drinks")
public class Drink {
    @Id
    int id;
    String drink_name;
    String drink_image_url;
}
