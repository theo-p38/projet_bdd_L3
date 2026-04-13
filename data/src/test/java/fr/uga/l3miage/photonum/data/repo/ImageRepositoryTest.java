package fr.uga.l3miage.photonum.data.repo;

import fr.uga.l3miage.photonum.data.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ImageRepositoryTest extends Base {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    CadreRepository cadreRepository;

    @Autowired
    CalendrierRepository calendrierRepository;

    @Autowired
    AlbumRepository albumRepository;

    @Test
    void isRelatedToPhotoWithOngoingImpressionFalse(){
        //given
        Photo photo = Fixtures.newPhoto();
        photoRepository.save(photo);

        // when
        boolean enCours = imageRepository.isRelatedToPhotoWithOngoingImpression(photo.getImageSource().getId());

        // then
        assertThat(enCours).isFalse();
    }

    @Test
    void isRelatedToPhotoWithOngoingImpressionTrueCadre(){
        //given
        List<Photo> photos = new ArrayList<>();
        Photo photo = Fixtures.newPhoto();
        photos.add(photo);
        photoRepository.save(photo);
        Cadre cadre = Fixtures.newCadre();
        cadre.setPhotographies(photos);
        cadre.setStatut(Cadre.Statut.ENCOURS);
        cadreRepository.save(cadre);

        // when
        boolean enCours = imageRepository.isRelatedToPhotoWithOngoingImpression(photo.getImageSource().getId());

        // then
        assertThat(enCours).isTrue();
    }

    @Test
    void isRelatedToPhotoWithOngoingImpressionTrueCalendrier(){
        //given
        Calendrier calendrier = Fixtures.newCalendrier();
        calendrier.setStatut(Calendrier.Statut.ENCOURS);
        calendrierRepository.save(calendrier);
        // when
        boolean enCours = false;
        for (Page page : calendrier.getPagesDeCalendrier()) {
            for (Photo photo : page.getPhotos()) {
                enCours = imageRepository.isRelatedToPhotoWithOngoingImpression(photo.getImageSource().getId());
                if(enCours) break;
            }
        }

        // then
        assertThat(enCours).isTrue();
    }

    @Test
    void isRelatedToPhotoWithOngoingImpressionTrueAlbum(){
        //given
        Album album = Fixtures.newAlbum();
        album.setStatut(Album.Statut.ENCOURS);
        albumRepository.save(album);

        // when
        boolean enCours = false;
        for (Page page : album.getPagesDAlbums()) {
            for (Photo photo : page.getPhotos()) {
                enCours = imageRepository.isRelatedToPhotoWithOngoingImpression(photo.getImageSource().getId());
                if(enCours) break;
            }
        }

        // then
        assertThat(enCours).isTrue();
    }

}
