package fr.uga.l3miage.photonum.data.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class SelectionTirage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private int quantite;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Photo photo;

}
