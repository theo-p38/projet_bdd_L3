package fr.uga.l3miage.photonum.service;

import fr.uga.l3miage.photonum.data.domain.Cadre;
import fr.uga.l3miage.photonum.data.domain.Client;
import fr.uga.l3miage.photonum.data.domain.Image;
import fr.uga.l3miage.photonum.data.domain.Photo;
import fr.uga.l3miage.photonum.data.repo.CalendrierRepository;
import fr.uga.l3miage.photonum.data.repo.ClientRepository;
import fr.uga.l3miage.photonum.data.repo.ImageRepository;
import fr.uga.l3miage.photonum.data.repo.PhotoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ClientRepository clientRepository;
    private final PhotoRepository photoRepository;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository, ClientRepository clientRepository,
                            PhotoRepository photoRepository) {

        this.imageRepository = imageRepository;
        this.clientRepository = clientRepository;
        this.photoRepository = photoRepository;
    }

    @Override
    public Image get(Long id) throws EntityNotFoundException {
        Image image = imageRepository.get(id);
        if (image == null) {
            throw new EntityNotFoundException("Image not found");
        }
        return image;
    }

    @Override
    public Collection<Image> list() {
        return imageRepository.all();
    }

    @Override
    public Image update(Image image) throws EntityNotFoundException {
        return imageRepository.save(image);
    }

    public Image searchAnImageOfAClient(Long clientId, Long imageId) throws EntityNotFoundException {
        Image image = imageRepository.searchAnImageOfAClient(clientId, imageId);
        if (image == null) {
            throw new EntityNotFoundException("Le client ne possède pas cette image");
        }
        return image;
    }

    @Override
    public void delete(Long clientId, Long imageId) throws EntityNotFoundException, DeleteImageException {
        Client client = clientRepository.get(clientId);

        if (client == null) {
            throw new EntityNotFoundException("Client with id=%d not found".formatted(imageId));
        }

        Image image = get(imageId);

        if (image == null) {
            throw new EntityNotFoundException("Image with id=%d not found".formatted(imageId));
        } else if (image.getProprietaire().getId() != clientId) {
            throw new DeleteImageException("cannot delete image " + imageId + " because client " + clientId + " does not own it");
        }
        else if (image.isEstPartage()) {
            throw new DeleteImageException("cannot delete shared image with id %d");
        } else if (this.imageRepository.isRelatedToPhotoWithOngoingImpression(imageId)) {
            throw new DeleteImageException("cannot delete image, one or several pictures " +
                    " are involved in prints in progress");
        }

        List<Photo> photos = this.photoRepository.getAllPhotosOfAnImage(imageId);

        this.imageRepository.delete(image);

        for (Photo photo : photos) {
            this.photoRepository.delete(photo);
        }

    }
    @Override
    public List<Image> getImagesPartagees() {
        return imageRepository.getImagesPartagees();
    }

}
