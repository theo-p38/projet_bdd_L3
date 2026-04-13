package fr.uga.l3miage.photonum.data.repo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import fr.uga.l3miage.photonum.data.domain.Image;
import fr.uga.l3miage.photonum.data.domain.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PhotoRepository implements CRUDRepository<Long, Photo> {

    @PersistenceContext
    private EntityManager entityManager; 

    @Autowired
    public PhotoRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Photo save(Photo photo) {
        entityManager.persist(photo);
        return photo;
    }

    @Override
    public Photo get(Long id) {
        return entityManager.find(Photo.class, id);
    }

    @Override
    public void delete(Photo photo) {
        entityManager.remove(photo);
    }

    @Override
    public List<Photo> all() {
        return entityManager.createQuery("from Photo", Photo.class).getResultList();
    }

    public Image getImageSource(Long id) {
        return entityManager.createQuery("select imageSource from Photo where id = :id", Image.class).setParameter("id", id).getSingleResult();
    }

    public List<Photo> getAllPhotosOfAnImage(Long imageId) {
        return entityManager.createQuery("select p from Photo p join p.imageSource i where i.id = :imageId", Photo.class)
                .setParameter("imageId", imageId).getResultList();
    }

}
