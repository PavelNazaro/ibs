package com.pavelnazaro.ibs.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_company")
    private String firstCompany;

    @Column(name = "second_company")
    private String secondCompany;

    @Column(name = "first_signature")
    private Boolean firstSignature;

    @Column(name = "second_signature")
    private Boolean secondSignature;

    public Document(String firstCompany, String secondCompany) {
        this.firstCompany = firstCompany;
        this.secondCompany = secondCompany;
        this.firstSignature = false;
        this.secondSignature = false;
    }

    @Override
    public String toString() {
        return "[id: " + id + "; firstCompany:'" + firstCompany + "'" +
                "; secondCompany:'" + secondCompany + "']";
    }
}
