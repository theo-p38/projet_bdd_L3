package fr.uga.l3miage.photonum.data.repo;

import fr.uga.l3miage.photonum.data.domain.Cadre;
import fr.uga.l3miage.photonum.data.domain.Tirage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TirageRepository implements CRUDRepository<Long, Tirage> {

    @PersistenceContext
    private EntityManager entityManager; 

    @Autowired
    public TirageRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Tirage save(Tirage tirage) {
        entityManager.persist(tirage);
        return tirage;
    }

    @Override
    public Tirage get(Long id) {
        return entityManager.find(Tirage.class, id);
    }

    @Override
    public void delete(Tirage tirage) {
        entityManager.remove(tirage);
    }

    @Override
    public List<Tirage> all() {
        return entityManager.createQuery("from Tirage", Tirage.class).getResultList();
    }

    public List<Tirage> getAllTiragesOfAClient(Long id){
        return entityManager.createQuery("select t from Tirage t join t.consommateur conso where conso.id = :id", Tirage.class)
                .setParameter("id", id)
                .getResultList();
    }

    public Tirage update(Tirage tirage) {
        return entityManager.merge(tirage);
    }



}
