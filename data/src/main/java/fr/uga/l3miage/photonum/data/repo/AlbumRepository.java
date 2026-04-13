package fr.uga.l3miage.photonum.data.repo;

import fr.uga.l3miage.photonum.data.domain.Album;

import fr.uga.l3miage.photonum.data.domain.Cadre;
import fr.uga.l3miage.photonum.data.domain.Calendrier;
import fr.uga.l3miage.photonum.data.domain.Photo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AlbumRepository implements CRUDRepository<Long, Album> {

    @PersistenceContext
    private EntityManager entityManager; 

    @Autowired
    public AlbumRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Album save(Album album) {
        entityManager.persist(album);
        return album;
    }

    @Override
    public Album get(Long id) {
        return entityManager.find(Album.class, id);
    }

    @Override
    public void delete(Album album) {
        entityManager.remove(album);
    }

    @Override
    public List<Album> all() {
        return entityManager.createQuery("from Album", Album.class).getResultList();
    }

    public List<Album> getAllAlbumsOfAClient(Long id){
        return entityManager.createQuery("select a from Album a join a.consommateur conso where conso.id = :id", Album.class)
                .setParameter("id", id)
                .getResultList();
    }

    public Album update(Album album) {
        return entityManager.merge(album);
    }

}
