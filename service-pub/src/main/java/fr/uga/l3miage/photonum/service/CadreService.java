package fr.uga.l3miage.photonum.service;


import fr.uga.l3miage.photonum.data.domain.Cadre;
import fr.uga.l3miage.photonum.data.domain.Photo;
import fr.uga.l3miage.photonum.service.base.BaseService;

import java.util.Collection;
import java.util.List;

public interface CadreService extends BaseService<Cadre, Long> {

    /**
     * Save a calendrier object
     * @param cadre to be saved
     * @return the calendrier with an id and 12 pages with default picture
     */
    Cadre save(Long clientId, Cadre cadre) throws EntityNotFoundException;

    /**
     *
     * @param clientId dont on veut trouver les calendriers
     * @return tous les calendriers que le client a crée
     */
    Collection<Cadre> getAllCadresOfAClient(Long clientId) throws EntityNotFoundException;

    Cadre addPhoto(Cadre cadre, Photo photo) throws EntityNotFoundException;

}
