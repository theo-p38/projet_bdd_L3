package fr.uga.l3miage.photonum.data.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.boot.model.source.spi.FetchCharacteristics;

@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public abstract class Impression {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private float prix;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Formats format;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Qualite qualite;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Statut statut = Statut.ENCOURS;

    @ManyToOne(fetch = FetchType.EAGER)
    private Client consommateur;

    public enum Statut {
        ENATTENTE,
        ENCOURS,
        TERMINE
    }
    public enum Formats {
        PETIT,
        MOYEN,
        GRAND
    }
    public enum Qualite {
        FAIBLE,
        MOYENNE,
        BONNE
    }
}
