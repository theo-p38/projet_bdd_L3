package fr.uga.l3miage.photonum.data.repo;

import fr.uga.l3miage.photonum.data.domain.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional
@Repository
public class ClientRepository implements CRUDRepository<Long, Client> {

    @PersistenceContext
    private EntityManager entityManager; 

    @Autowired
    public ClientRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Client save(Client client) {
        entityManager.persist(client);
        return client;
    }

    @Override
    public Client get(Long id) {
        return entityManager.find(Client.class, id);
    }

    @Override
    public void delete(Client client) {
        entityManager.remove(client);
    }

    @Override
    // liste tous les clients
    public List<Client> all() {
        return entityManager.createQuery("from Client", Client.class).getResultList();
    }

    // liste toutes les commandes d'un client donné (id)
    public List<Commande> retrieveCommandes(Long id) {
        return entityManager.createQuery("select com from Client cli join cli.commandes com where cli.id = :id", Commande.class).setParameter("id", id).getResultList();
    }

    // récupère une commande donnée (commandeId) d'un client donné (clientId)
    public Commande getCommande(Long clientId, Long commandeId){
        return entityManager.createQuery("select com from Client cli join cli.commandes com where cli.id = :clientId and com.id = :commandeId", Commande.class).setParameter("clientId", clientId).setParameter("commandeId", commandeId).getSingleResult();
    }

    // récupère la dernière commande d'un client donné (id)
    public Commande getDerniereCommande(Long id) {
        return entityManager.createQuery("select com from Client cli join cli.commandes com where cli.id = :id and com.dateDeCommande = (select max(c.dateDeCommande) from Commande c)", Commande.class).setParameter("id", id).getSingleResult();
    }

    // récupère les impressions d'un client donné (id)
    public List<Impression> getImpressions(Long id) {
        return entityManager.createQuery("select impressions from Client where id = :id", Impression.class).setParameter("id", id).getResultList();
    }

    // récupère les images dont le client donné (id) est le propriétaire
    public List<Image> getImages(Long id) {
        return entityManager.createQuery("select images from Client where id = :id", Image.class).setParameter("id", id).getResultList();
    }

}
