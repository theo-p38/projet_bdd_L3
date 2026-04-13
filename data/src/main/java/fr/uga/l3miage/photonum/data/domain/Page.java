package fr.uga.l3miage.photonum.data.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private int numero;

    private String description;

    @OneToMany(fetch = FetchType.EAGER)
    @Column(nullable = false)
    private List<Photo> photos;
}
