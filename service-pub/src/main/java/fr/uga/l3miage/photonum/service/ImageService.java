package fr.uga.l3miage.photonum.service;

import fr.uga.l3miage.photonum.data.domain.Image;
import fr.uga.l3miage.photonum.service.base.BaseService;
import java.util.List;

public interface ImageService extends BaseService<Image, Long> {

    /**
     * supprimer une image
     *
     * @param imageId de l'image à supprimer
     * @param idClient id du client de l'image à supprimer
     * @throws EntityNotFoundException quand l'image n'existe pas
     * @throws DeleteImageException  quand l'image possède des photos qui sont liés à des impressions en cours
     */
    void delete(Long idClient, Long imageId) throws EntityNotFoundException, DeleteImageException;


    Image searchAnImageOfAClient(Long clientId, Long imageId) throws EntityNotFoundException;
    
        /**
     *
     * @return la liste des images partagees
     */
    List<Image> getImagesPartagees();
}
