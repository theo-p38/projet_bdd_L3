package fr.uga.l3miage.photonum.service;

import fr.uga.l3miage.photonum.data.domain.Calendrier;
import fr.uga.l3miage.photonum.data.domain.Client;
import fr.uga.l3miage.photonum.data.domain.Commande;
import fr.uga.l3miage.photonum.data.domain.Image;
import fr.uga.l3miage.photonum.data.domain.Impression;
import fr.uga.l3miage.photonum.data.domain.Commande.Statut;
import fr.uga.l3miage.photonum.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;


public interface ClientService extends BaseService<Client, Long> {

    /**
     *
     * @param id dont on veut trouver les commandes
     * @return toutes les commandes du client
     */
    List<Commande> retrieveCommandes(Long id);

    /**
     *
     * @param clientId dont on veut trouver la commande
     * @param commandeId trouver une commande en particulier
     * @return la commande du client
     */
    Commande getCommande(Long clientId, Long commandeId);

    /**
     *
     * @param id du client dont on veut trouver les impressions
     * @return toutes les impressions du client
     */
    List<Impression> getImpressions(Long id);

    /**
     *
     * @param id du client dont on veut trouver les images où il est le propriétaire
     * @return toutes les images qui sont la propiété du client
     */
    List<Image> getImages(Long id);

}
