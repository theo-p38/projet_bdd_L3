package fr.uga.l3miage.photonum.data.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Data;
    
@Entity
@Data
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String chemin;

    private String info;

    @Column(nullable = false)
    private int resolution;

    private boolean estPartage = false;

    @ManyToOne(fetch = FetchType.EAGER)
    private Client proprietaire;

}
