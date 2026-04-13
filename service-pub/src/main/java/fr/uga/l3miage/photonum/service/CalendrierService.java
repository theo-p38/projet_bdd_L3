package fr.uga.l3miage.photonum.service;

import fr.uga.l3miage.photonum.data.domain.Calendrier;
import fr.uga.l3miage.photonum.data.domain.Page;
import fr.uga.l3miage.photonum.data.domain.Photo;
import fr.uga.l3miage.photonum.service.base.BaseService;

import java.util.Collection;


public interface CalendrierService extends BaseService<Calendrier, Long> {

    /**
     * Save a calendrier object
     * @param calendrier to be saved
     * @return the calendrier with an id and 12 pages with default picture
     */
    Calendrier save(Long clientId, Calendrier calendrier) throws EntityNotFoundException;

    /**
     *
     * @param clientId dont on veut trouver les calendriers
     * @return tous les calendriers que le client a crée
     */
    Collection<Calendrier> getAllCalendriersOfAClient(Long clientId) throws EntityNotFoundException;

    Calendrier updateAndAddPage(Calendrier calendrier, Page page, Collection<Photo> photos)
            throws EntityNotFoundException;

}
