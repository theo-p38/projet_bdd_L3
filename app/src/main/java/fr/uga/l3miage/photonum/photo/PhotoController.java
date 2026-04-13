package fr.uga.l3miage.photonum.photo;

import fr.uga.l3miage.photonum.data.domain.Client;
import fr.uga.l3miage.photonum.data.domain.Image;
import fr.uga.l3miage.photonum.data.domain.Photo;
import fr.uga.l3miage.photonum.service.ClientService;
import fr.uga.l3miage.photonum.service.EntityNotFoundException;
import fr.uga.l3miage.photonum.service.ImageService;
import fr.uga.l3miage.photonum.service.PhotoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class PhotoController {

    private final PhotoService photoService;
    private final PhotoMapper photoMapper;
    private final ClientService clientService;
    private final ImageService imageService;

    @Autowired
    public PhotoController(PhotoService photoService, PhotoMapper photoMapper, ClientService clientService,
                           ImageService imageService) {
        this.photoService = photoService;
        this.photoMapper = photoMapper;
        this.clientService = clientService;
        this.imageService = imageService;
    }

    /* Renvoie toutes les photos dérivées d'une image d'un client */
    @GetMapping("/clients/{clientId}/images/{imageId}/photos")
    @Transactional
    public Collection<PhotoDTO> photos(@PathVariable("clientId") @NotNull Long clientId, @PathVariable("imageId") @NotNull Long imageId) {

        Client client = null;
        try {
            client = clientService.get(clientId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "client not found");
        }

        Image image = null;
        try {
            image = imageService.get(imageId);
        } catch (EntityNotFoundException e) {
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "image not found");
        }

        if(client.getId().equals(image.getProprietaire().getId())) {
            Collection<Photo> photos = photoService.getAllPhotosOfAnImage(imageId);
            return photoMapper.entityToDTO(photos);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "this image does not belong to this user");
        }
    }

    /* Crée une nouvelle photo */
    @PostMapping(value = "/clients/{clientId}/images/{imageId}/photos", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public PhotoDTO newPhoto(@PathVariable("clientId") @NotNull Long clientId, @PathVariable("imageId") @NotNull Long imageId,
                                       @RequestBody @Valid PhotoDTO photoDTO) {

        Client client = null;
        try {
            client = clientService.get(clientId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "client id not found");
        }

        Image image = null;
        try {
            image = imageService.get(imageId);
        } catch (EntityNotFoundException e) {
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "image id not found");
        }

        Photo photo = photoMapper.dtoToEntity(photoDTO);
        try {
            if(client.getId().equals(image.getProprietaire().getId()) && imageId.equals(photo.getImageSource().getId())) {
                Photo saved = photoService.save(photo, imageId);
                return photoMapper.entityToDTO(saved);
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "the image does not belong to this client");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
