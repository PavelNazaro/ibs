package com.pavelnazaro.ibs.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "documents")
@Data
@AllArgsConstructor
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_side")
    private String firstSide;

    @Column(name = "second_side")
    private String secondSide;

    public Document() {
        this.firstSide = "Not signed";
        this.secondSide = "Not signed";
    }
}
