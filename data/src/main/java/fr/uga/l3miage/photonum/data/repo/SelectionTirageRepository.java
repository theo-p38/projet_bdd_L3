package fr.uga.l3miage.photonum.data.repo;

import fr.uga.l3miage.photonum.data.domain.SelectionTirage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SelectionTirageRepository implements CRUDRepository<Long, SelectionTirage> {

    @PersistenceContext
    private EntityManager entityManager; 

    @Autowired
    public SelectionTirageRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public SelectionTirage save(SelectionTirage selectionTirage) {
        entityManager.persist(selectionTirage);
        return selectionTirage;
    }

    @Override
    public SelectionTirage get(Long id) {
        return entityManager.find(SelectionTirage.class, id);
    }

    @Override
    public void delete(SelectionTirage selectionTirage) {
        entityManager.remove(selectionTirage);
    }

    @Override
    public List<SelectionTirage> all() {
        return entityManager.createQuery("from SelectionTirage", SelectionTirage.class).getResultList();
    }
}
