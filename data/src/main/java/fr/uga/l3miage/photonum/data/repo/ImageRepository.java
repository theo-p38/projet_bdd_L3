package fr.uga.l3miage.photonum.data.repo;

import fr.uga.l3miage.photonum.data.domain.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import fr.uga.l3miage.photonum.data.domain.Impression.Statut;

@Transactional
@Repository
public class ImageRepository implements CRUDRepository<Long, Image> {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ImageRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Image save(Image image) {
        entityManager.persist(image);
        return image;
    }

    @Override
    public Image get(Long id) {
        return entityManager.find(Image.class, id);
    }

    @Override
    public void delete(Image image) {
        entityManager.remove(image);
    }

    // liste de toutes les images
    @Override
    public List<Image> all() {
        return entityManager.createQuery("from Image", Image.class).getResultList();
    }

    // liste de toutes les images partagées
    public List<Image> getImagesPartagees() {
        return entityManager.createQuery("from Image where estPartage = true", Image.class).getResultList();
    }

    public Image searchAnImageOfAClient(Long clientId, Long imageId) {
        return entityManager.createQuery("select i from Client c join c.images i where c.id = :clientId and i.id = :imageId", Image.class)
                .setParameter("clientId", clientId)
                .setParameter("imageId", clientId)
                .getSingleResult();
    }

    public boolean isRelatedToPhotoWithOngoingImpression(Long id) {
        boolean encours = false;

        List<Calendrier> calendriers = entityManager.createQuery("from Calendrier cal " +
                "join cal.pagesDeCalendrier pages " +
                        "join pages.photos pho " +
                            "where cal.statut = :statut " +
                                "and pho.imageSource.id = :id", Calendrier.class)
                .setParameter("id", id)
                .setParameter("statut", Statut.ENCOURS)
                .getResultList();
        List<Album> albums = entityManager.createQuery("from Album alb " +
                        "join alb.pagesDAlbums pages " +
                        "join pages.photos pho " +
                        "where alb.statut = :statut " +
                        "and pho.imageSource.id = :id", Album.class)
                .setParameter("id", id)
                .setParameter("statut", Statut.ENCOURS)
                .getResultList();
        /*
        List<Tirage> tirages = entityManager.createQuery("from Tirage tir " +
                        "join tir.selections sel " +
                        "join sel.photo pho " +
                        "where tir.statut = Impression.Statut.ENCOURS " +
                        "and pho.imageSource.id = :id", Tirage.class)
                .setParameter("id", id)
                .getResultList();

         */
        List<Cadre> cadres = entityManager.createQuery("from Cadre cad " +
                        "join cad.photographies pho " +
                        "where cad.statut = :statut" +
                        " and pho.imageSource.id = :id", Cadre.class)
                .setParameter("id", id)
                .setParameter("statut", Statut.ENCOURS)
                .getResultList();
        /*
        if (calendriers.size() > 0 || albums.size() > 0 || tirages.size() > 0 || cadres.size() > 0) {
            encours = true;
        }
         */
        if(cadres.size() > 0 || calendriers.size() > 0 || albums.size() > 0) {
            encours = true;
        }
        return encours;
    }
}
