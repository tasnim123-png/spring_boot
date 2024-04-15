package com.example.pfe.Formation;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name="Formations")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    @Column(nullable = false)
    private String Nom_Formation ;

    @Column(nullable = false)
    private Date Date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Niveau niveau;

    @Enumerated(EnumType.STRING)
    @Column (nullable = false)
    private Avancement avancement;






}
