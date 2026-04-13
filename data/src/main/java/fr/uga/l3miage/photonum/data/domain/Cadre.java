package fr.uga.l3miage.photonum.data.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Cadre extends Impression {

    @Column(nullable = false)
    private String taille;

    @OneToMany(fetch = FetchType.EAGER)
    @Column(nullable = false)
    private List<Photo> photographies;

    public void addPhoto(Photo photo) {
        if (this.photographies == null) {
            this.photographies = new ArrayList<>();
        }
        this.photographies.add(photo);
    }

}
