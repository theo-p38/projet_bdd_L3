package fr.uga.l3miage.photonum.data.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Album extends Impression {

    @Column(nullable = false)
    private String titre;

    @OneToOne()
    @PrimaryKeyJoinColumn
    private Photo photoDeCouverture = null;

    @OneToMany()
    @Column(nullable = false)
    private Set<Page> pagesDAlbums;

    public void addPage(Album album, Page page) {
        if (this.pagesDAlbums == null) {
            this.pagesDAlbums = new HashSet<>();
        }
        page.setNumero(this.pagesDAlbums.size() + 1);
        this.pagesDAlbums.add(page);
    }


    public void addPhoto(Photo photo, int numero) {
        List<Page> pagesArray = new ArrayList<>(this.pagesDAlbums);
        pagesArray.get(numero).getPhotos().add(photo);
    }

}
