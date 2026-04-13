package fr.uga.l3miage.photonum.service;

import fr.uga.l3miage.photonum.data.domain.Album;
import fr.uga.l3miage.photonum.data.domain.Page;
import fr.uga.l3miage.photonum.data.domain.Photo;
import fr.uga.l3miage.photonum.service.base.BaseService;

import java.util.Collection;
import java.util.List;

public interface AlbumService extends BaseService<Album, Long> {

    /**
     * Save a calendrier object
     * @param album to be saved
     * @return the album with an id
     */
    Album save(Long clientId, Album album) throws EntityNotFoundException;

    Collection<Album> getAllAlbumsOfAClient(Long clientId) throws EntityNotFoundException;

    Album addPhoto(Album album, Photo photo, int numero) throws EntityNotFoundException;

    Album addPage(Album album, Page page) throws EntityNotFoundException;
}
