package com.pavelnazaro.ibs.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "documents")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_side")
    private String firstSide;

    @Column(name = "second_side")
    private String secondSide;

    public Document(String firstSide, String secondSide) {
        this.firstSide = firstSide;
        this.secondSide = secondSide;
    }
}
