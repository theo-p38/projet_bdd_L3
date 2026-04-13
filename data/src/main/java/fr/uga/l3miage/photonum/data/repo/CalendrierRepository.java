package fr.uga.l3miage.photonum.data.repo;

import fr.uga.l3miage.photonum.data.domain.Calendrier;
import fr.uga.l3miage.photonum.data.domain.Page;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

// bind calendrier avec pages : voir le tp précédent save du serviceimpl
@Repository
public class CalendrierRepository implements CRUDRepository<Long, Calendrier> {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired
    public CalendrierRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Calendrier save(Calendrier calendrier) {
        entityManager.persist(calendrier);
        return calendrier;
    }

    public Calendrier update(Calendrier calendrier) {
        return entityManager.merge(calendrier);
    }

    @Override
    public Calendrier get(Long id) {
        return entityManager.find(Calendrier.class, id);
    }
    
    @Override
    public void delete(Calendrier calendrier) {
        entityManager.remove(calendrier);
    }
    
    /**
     * Retourne la liste de tous les calendriers
     * @return List<Calendrier>
     */
    @Override
    public List<Calendrier> all() {
        return entityManager.createQuery("from Calendrier", Calendrier.class).getResultList();
    }

    public List<Calendrier> getAllCalendriersOfAClient(Long id){
        return entityManager.createQuery("select c from Calendrier c join c.consommateur conso where conso.id = :id", Calendrier.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<Page> getPagesFromCalendrier(Long id){
        return entityManager.createQuery("select pagesDeCalendrier from Calendrier where id = :id", Page.class)
                .setParameter("id", id)
                .getResultList();
    }

    public Page getPageNumberNFromCalendrier(Long id, int n){
        return entityManager.createQuery("select p  from Calendrier c join c.pagesDeCalendrier p where c.id = :id and p.numero = :n ", Page.class)
                .setParameter("id", id)
                .setParameter("n", n)
                .getSingleResult();
    }
}
