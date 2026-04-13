package fr.uga.l3miage.photonum.data.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;
import java.util.Date;

@Data
@Entity
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private Date dateDeCommande;

    @Column(nullable = false)
    private float prixTotal;

    @Column(nullable = false)
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Impression> articles;

    @Column(nullable = false)
    private Statut statut;

    public enum Statut {
        ENATTENTE,
        ENCOURS,
        TERMINE
    }
}
