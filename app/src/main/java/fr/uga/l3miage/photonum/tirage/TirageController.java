package fr.uga.l3miage.photonum.tirage;


import fr.uga.l3miage.photonum.cadre.CadreDTO;
import fr.uga.l3miage.photonum.data.domain.*;
import fr.uga.l3miage.photonum.photo.PhotoDTO;
import fr.uga.l3miage.photonum.selectionTirage.SelectionTirageDTO;
import fr.uga.l3miage.photonum.selectionTirage.SelectionTirageMapper;
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
public class TirageController {

    private final TirageService tirageService;
    private final TirageMapper tirageMapper;
    private final PhotoService photoService;
    private final ClientService clientService;
    private final ImageService imageService;
    private final SelectionTirageMapper selectionTirageMapper;
    @Autowired
    public TirageController(TirageService tirageService, TirageMapper tirageMapper, SelectionTirageMapper selectionTirageMapper,
                            PhotoService photoService, ClientService clientService, ImageService imageService) {
        this.tirageService = tirageService;
        this.tirageMapper = tirageMapper;
        this.photoService = photoService;
        this.clientService = clientService;
        this.imageService = imageService;
        this.selectionTirageMapper = selectionTirageMapper;

    }

    @GetMapping("/clients/{clientId}/impressions/tirages")
    public Collection<TirageDTO> tirages(@PathVariable("clientId") @NotNull Long id) {

        try {
            Collection<Tirage> tirages = tirageService.getAllTiragesOfAClient(id);
            return tirageMapper.entityToDTO(tirages);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "client not found");
        }

    }

    /* Crée un nouveau tirage pour un client */
    @PostMapping(value = "/clients/{clientId}/impressions/tirages", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public TirageDTO newTirage(@PathVariable("clientId") @NotNull Long clientId, @RequestBody @Valid TirageDTO tirageDTO) {
        try {
            Tirage saved = tirageService.save(clientId, tirageMapper.dtoToEntity(tirageDTO));
            return tirageMapper.entityToDTO(saved);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "client not found");
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }


    /* update un tirage : NOTE: apparement on en a pas besoin dans le cahier des charges
    @PutMapping("/clients/{clientId}/impressions/tirages")
    public TirageDTO updateTirage(@PathVariable("clientId") @NotNull Long clientId, @RequestBody @Valid TirageDTO tirageDTO) {
        try {
            if (clientId.equals(tirageDTO.id)) {
                var updated = tirageService.update(tirageMapper.dtoToEntity(tirageDTO));
                return tirageMapper.entityToDTO(updated);
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
     */


    /**
     * Create a new tirage selection
     */
    @PostMapping(value = "/client/{clientId}/impressions/tirages/{tirId}/selections", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public TirageDTO newTiragePage(@PathVariable("cliendId") @NotNull Long clientId, @PathVariable("tirId") @NotNull Long tirId,
                                   @RequestBody @Valid SelectionTirageDTO selectionTirageDTO) {

        Client client;
        try {
            client = clientService.get(clientId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "client not found", e);
        }

        Tirage tirage = null;
        try {
            tirage = tirageService.get(tirId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "tirage not found", e);
        }

        Photo photo;
        try {
            photo = photoService.get(selectionTirageDTO.getPhoto().id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "photo not found");
        }

        Image imageSourcePhoto;
        try {
            imageSourcePhoto = imageService.get(photo.getImageSource().getId());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "image source de la photo not found");
        }


        if (client.getId().equals(tirage.getConsommateur().getId()) && client.getId().equals(imageSourcePhoto.getProprietaire().getId())) {
            try {
                tirage = tirageService.addSelection(tirage, selectionTirageMapper.dtoToEntity(selectionTirageDTO));
                return tirageMapper.entityToDTO(tirage);
            } catch (EntityNotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "tirage not found", e);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "this tirage does not belong to this client");

        }
    }


}
