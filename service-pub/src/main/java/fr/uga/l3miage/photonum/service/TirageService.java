package fr.uga.l3miage.photonum.service;

import fr.uga.l3miage.photonum.data.domain.Cadre;
import fr.uga.l3miage.photonum.data.domain.Photo;
import fr.uga.l3miage.photonum.data.domain.SelectionTirage;
import fr.uga.l3miage.photonum.data.domain.Tirage;
import fr.uga.l3miage.photonum.service.base.BaseService;

import java.util.Collection;

public interface TirageService extends BaseService<Tirage, Long> {

    /**
     * Save a calendrier object
     * @param tirage to be saved
     * @return the calendrier with an id and 12 pages with default picture
     */
    Tirage save(Long clientId, Tirage tirage) throws EntityNotFoundException;

    /**
     *
     * @param clientId dont on veut trouver les calendriers
     * @return tous les calendriers que le client a crée
     */
    Collection<Tirage> getAllTiragesOfAClient(Long clientId) throws EntityNotFoundException;

    Tirage addSelection(Tirage tirage, SelectionTirage selectionTirage) throws EntityNotFoundException;
}