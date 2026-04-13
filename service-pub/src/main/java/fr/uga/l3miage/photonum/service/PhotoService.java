package fr.uga.l3miage.photonum.service;

import fr.uga.l3miage.photonum.data.domain.Image;
import fr.uga.l3miage.photonum.data.domain.Photo;
import fr.uga.l3miage.photonum.service.base.BaseService;

import java.util.Collection;

public interface PhotoService extends BaseService<Photo, Long> {

    Photo save(Photo photo, Long imageId) throws EntityNotFoundException;

    Collection<Photo> getAllPhotosOfAnImage(Long imageId);
}
