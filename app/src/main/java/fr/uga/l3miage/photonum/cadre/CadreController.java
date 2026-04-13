package fr.uga.l3miage.photonum.cadre;


import fr.uga.l3miage.photonum.data.domain.*;
import fr.uga.l3miage.photonum.photo.PhotoDTO;
import fr.uga.l3miage.photonum.photo.PhotoMapper;
import fr.uga.l3miage.photonum.service.*;
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
public class CadreController {

    private final CadreService cadreService;
    private final CadreMapper cadreMapper;
    private final PhotoService photoService;
    private final PhotoMapper photoMapper;
    private final ClientService clientService;
    private final ImageService imageService;

    @Autowired
    public CadreController(CadreService cadreService, CadreMapper cadreMapper, PhotoMapper photoMapper, PhotoService photoService,
                           ClientService clientService, ImageService imageService) {
        this.cadreService = cadreService;
        this.cadreMapper = cadreMapper;
        this.photoMapper = photoMapper;
        this.photoService = photoService;
        this.clientService = clientService;
        this.imageService = imageService;
    }

    @GetMapping("/clients/{clientId}/impressions/cadres")
    public Collection<CadreDTO> calendriers(@PathVariable("clientId") @NotNull Long id) {
        try {
            Collection<Cadre> cadres = cadreService.getAllCadresOfAClient(id);
            return cadreMapper.entityToDTO(cadres);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "client not found", e);
        }
    }

    /* Crée un nouveau calendrier pour un client */
    @PostMapping(value = "/clients/{clientId}/impressions/cadres", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CadreDTO newCadre(@PathVariable("clientId") @NotNull Long clientId, @RequestBody @Valid CadreDTO cadreDTO) {
        try {
            Cadre saved = cadreService.save(clientId, cadreMapper.dtoToEntity(cadreDTO));
            return cadreMapper.entityToDTO(saved);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "client not found", e);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    /* Update un cadre : NOTE: apparement on en a pas besoin dans le cahier des charges
    @PutMapping("/clients/{clientId}/impressions/cadres")
    public CadreDTO updateCadre(@PathVariable("clientId") @NotNull Long clientId, @RequestBody @Valid CadreDTO cadreDTO) {
        try {
            if (clientId.equals(cadreDTO.id)) {
                var updated = cadreService.update(cadreMapper.dtoToEntity(cadreDTO));
                return cadreMapper.entityToDTO(updated);
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

     */

    /**
     * Create a new photo for the cadre of a client
     */
    @PostMapping(value = "/client/{clientId}/impressions/cadres/{cadrId}/photos", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CadreDTO newCadrePhoto(@PathVariable("clientId") @NotNull Long clientId, @PathVariable("cadrId") @NotNull Long cadrId,
                                  @RequestBody @Valid PhotoDTO photoDTO) {

        Client client;
        try {
            client = clientService.get(clientId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "client not found", e);
        }

        Photo photo = photoMapper.dtoToEntity(photoDTO);
        try {
            photoService.get(photo.getId());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "photo not found");
        }

        Image imageSourcePhoto;
        try {
            imageSourcePhoto = imageService.get(photo.getImageSource().getId());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "image source de la photo not found");
        }

        System.out.println("bob 1 = " + imageSourcePhoto.getInfo());

        Cadre cadre;
        if (imageSourcePhoto.getProprietaire().getId().equals(client.getId())) {
            try {
                cadre = cadreService.get(cadrId);
            } catch (EntityNotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "cadre not found");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le propriétaire de l'image source de la photo n'est pas le client id");
        }

        if (client.getId().equals(cadre.getConsommateur().getId())) {
            //photo.setImageSource(imageSourcePhoto);
            try {
                cadre = cadreService.addPhoto(cadre, photo);
                return cadreMapper.entityToDTO(cadre);
            } catch (EntityNotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "this cadre does not belong to this client");
        }
    }

}
