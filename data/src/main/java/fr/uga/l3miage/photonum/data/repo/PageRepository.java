package fr.uga.l3miage.photonum.data.repo;

import fr.uga.l3miage.photonum.data.domain.Page;
import fr.uga.l3miage.photonum.data.domain.Photo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Base64;
import java.util.List;

@Repository
public class PageRepository implements CRUDRepository<Long, Page>{

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public PageRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Page save(Page page) {
        entityManager.persist(page);
        return page;
    }

    public List<Page> all() {
        return entityManager.createQuery("from Page", Page.class).getResultList();
    }

    public Page get(Long id) {
        return entityManager.find(Page.class, id);
    }

    public void delete(Page page) {
        entityManager.remove(page);
    }

    public void update(Page page) {
        entityManager.merge(page);
    }

    public List<Photo> getPhotosFromPage(Long id){
        return entityManager.createQuery("select photos from Page where id = :id", Photo.class)
                .setParameter("id", id)
                .getResultList();
    }



}
