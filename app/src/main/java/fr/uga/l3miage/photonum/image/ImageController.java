package fr.uga.l3miage.photonum.image;

import fr.uga.l3miage.photonum.data.domain.Image;
import fr.uga.l3miage.photonum.service.DeleteImageException;
import fr.uga.l3miage.photonum.service.EntityNotFoundException;
import fr.uga.l3miage.photonum.service.ImageService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class ImageController {
    //delete
    private final ImageService imageService;
    private final ImageMapper imageMapper;

    public ImageController(ImageService imageService, ImageMapper imageMapper) {
        this.imageService = imageService;
        this.imageMapper = imageMapper;
    }

    @DeleteMapping("/clients/{clientId}/images/{imageId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImage(@PathVariable("clientId") @NotNull Long clientId,
                            @PathVariable("imageId") @NotNull Long imageId) {
        try {
            this.imageService.delete(clientId, imageId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, null, e);
        } catch (DeleteImageException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Delete request failed", e);
        }
    }


    /******* Partie consultation des images partagees ************/
    @GetMapping("/clients/{clientId}/images")
    public Collection<ImageDTO> images() throws EntityNotFoundException {

        Collection<Image> images = null;
        images = imageService.getImagesPartagees();
        return imageMapper.entityToDTO(images);
    }
    /******* Partie consultation des images partagees fin **********/
}
