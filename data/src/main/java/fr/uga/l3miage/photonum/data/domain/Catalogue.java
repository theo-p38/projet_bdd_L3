package fr.uga.l3miage.photonum.data.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Catalogue {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private float tarifs;

    @Column(nullable = false)
    private String reference;

    @Column(nullable = false)
    private String qualite;

    @Column(nullable = false)
    private String format;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private TypeDImpression typeDImpression;

    public enum TypeDImpression {
        ALBUM,
        CADRE,
        CALENDRIER,
        TIRAGE
    }
}
