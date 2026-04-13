package fr.uga.l3miage.photonum.service;

import fr.uga.l3miage.photonum.data.domain.*;
import fr.uga.l3miage.photonum.data.repo.ImageRepository;
import fr.uga.l3miage.photonum.data.repo.PhotoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Transactional
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;
    private final ImageService imageService;

    @Autowired
    public PhotoServiceImpl(PhotoRepository photoRepository, ImageService imageService) {
        this.photoRepository = photoRepository;
        this.imageService = imageService;
    }

    @Override
    public Photo get(Long id) throws EntityNotFoundException {
        Photo photo = photoRepository.get(id);
        if (photo == null) {
            throw new EntityNotFoundException("Photo not found");
        }
        return photo;
    }

    @Override
    public Collection<Photo> list() {
        return photoRepository.all();
    }

    @Override
    public Photo update(Photo photo) throws EntityNotFoundException {
        return photoRepository.save(photo);
    }

    @Override
    public Photo save(Photo photo, Long imageId) throws EntityNotFoundException {
        bindImageToPhoto(photo, imageId);
        photoRepository.save(photo);
        return photo;
    }

    private void bindImageToPhoto(Photo photo, Long imageId) throws EntityNotFoundException {
        Image image = imageService.get(imageId);
        if (image == null) {
            throw new EntityNotFoundException("Photo not found");
        }
        photo.setImageSource(image);
    }

    @Override
    public Collection<Photo> getAllPhotosOfAnImage(Long imageId) {
        return photoRepository.getAllPhotosOfAnImage(imageId);
    }



}
