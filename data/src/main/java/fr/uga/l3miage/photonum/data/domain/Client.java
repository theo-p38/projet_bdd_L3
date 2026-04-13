package fr.uga.l3miage.photonum.data.domain;

import java.util.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;


@Data
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String nom;

    @NotNull
    @Column(nullable = false)
    private String prenom;

    @NotNull
    @Column(nullable = false)
    private String adresseMail;

    @NotNull
    @Column(nullable = false)
    private String adressePostale;

    @NotNull
    @Column(nullable = false)
    private String motDePasse;

    @OneToMany(mappedBy = "proprietaire", fetch = FetchType.EAGER)
    private Set<Image> images;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Commande> commandes;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Impression> impressions;

}
