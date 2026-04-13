package fr.uga.l3miage.photonum.data.repo;

import fr.uga.l3miage.photonum.data.domain.Album;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import fr.uga.l3miage.photonum.data.domain.Cadre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CadreRepository implements CRUDRepository<Long, Cadre> {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired
    public CadreRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Cadre save(Cadre cadre) {
        entityManager.persist(cadre);
        return cadre;
    }

    /*
    public Cadre update(Cadre cadre) {
        return entityManager.merge(cadre);
    }
    */

    @Override
    public Cadre get(Long id) {
        return entityManager.find(Cadre.class, id);
    }

    public void delete(Cadre cadre) {
        entityManager.remove(cadre);
    }

    @Override
    public List<Cadre> all() {
        return entityManager.createQuery("from Cadre", Cadre.class).getResultList();
    }

    public List<Cadre> getAllCadresOfAClient(Long id){
        return entityManager.createQuery("select c from Cadre c join c.consommateur conso where conso.id = :id", Cadre.class)
                .setParameter("id", id)
                .getResultList();
    }
    public Cadre update(Cadre cadre) {
       return entityManager.merge(cadre);
    }

}
