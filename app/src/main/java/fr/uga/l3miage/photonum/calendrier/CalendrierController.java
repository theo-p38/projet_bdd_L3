package fr.uga.l3miage.photonum.calendrier;

import fr.uga.l3miage.photonum.data.domain.*;

import fr.uga.l3miage.photonum.page.PageDTO;
import fr.uga.l3miage.photonum.page.PageMapper;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class CalendrierController {

    private final CalendrierService calendrierService;
    private final CalendrierMapper calendrierMapper;
    private final PhotoService photoService;
    private final PhotoMapper photoMapper;
    private final PageService pageService;
    private final ClientService clientService;
    private final PageMapper pageMapper;
    private final ImageService imageService;

    @Autowired
    public CalendrierController(CalendrierService calendrierService, CalendrierMapper calendrierMapper,
                                PhotoService photoService, PhotoMapper photoMapper, PageService pageService,
                                ClientService clientService, PageMapper pageMapper, ImageService imageService) {
        this.calendrierService = calendrierService;
        this.calendrierMapper = calendrierMapper;
        this.photoService = photoService;
        this.photoMapper = photoMapper;
        this.pageService = pageService;
        this.clientService = clientService;
        this.imageService = imageService;
        this.pageMapper = pageMapper;
    }

    /* Renvoie tous les calendriers d'un client */
    @GetMapping("/clients/{clientId}/impressions/calendriers")
    public Collection<CalendrierDTO> calendriers(@PathVariable("clientId") @NotNull Long clientId) {
        try {
            Collection<Calendrier> calendriers = calendrierService.getAllCalendriersOfAClient(clientId);
            return calendrierMapper.entityToDTO(calendriers);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    /* Crée un nouveau calendrier pour un client */
    @PostMapping(value = "/clients/{clientId}/impressions/calendriers", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CalendrierDTO newCalendrier(@PathVariable("clientId") @NotNull Long clientId,
                                       @RequestBody @Valid CalendrierDTO calendrierDTO) {

        try {
            Calendrier saved = calendrierService.save(clientId, calendrierMapper.dtoToEntity(calendrierDTO));
            return calendrierMapper.entityToDTO(saved);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "client id not found", e);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }


    /* Update a calendrier : Note : Pas besoin dans le cahier des charges
    @PutMapping("/clients/{clientId}/impressions/calendriers")
    public CalendrierDTO updateCalendrier(@PathVariable("clientId") @NotNull Long clientId, @RequestBody @Valid CalendrierDTO calendrierDTO) {

        try {
            if (clientId.equals(calendrierDTO.id)){
                var updated = calendrierService.update(calendrierMapper.dtoToEntity(calendrierDTO));
                return calendrierMapper.entityToDTO(updated);
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

     */


    /**
     * Pour un gain de temps on fait une requête 2 en 1:
     * On passe une page avec un numéro n et sans id, un description ou non et une liste de photos (peut être vide)
     * Cette requête permet d'actualiser la description de la page n et/ou d'ajouter une ou plusieurs photos dedans
     */
    @PostMapping(value = "/clients/{clientId}/impressions/calendriers/{calId}/pages", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CalendrierDTO newCalendrierPhoto(@PathVariable("clientId") @NotNull Long clientId, @PathVariable("calId") @NotNull Long calId,
                                            @RequestBody @Valid PageDTO pageDTO) {

        Page page = pageMapper.dtoToEntity(pageDTO);

        if (page.getNumero() < 1 | page.getNumero() > 12) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "numero must be an integer between 1 and 12");
        }

        Client client;
        try {
            client = clientService.get(clientId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "client not found", e);
        }

        Collection<Photo> photos = new ArrayList<>();
        for (Photo p: page.getPhotos()) {
            try {
                photos.add(photoService.get(p.getId()));
                Image imageSource = imageService.get(p.getImageSource().getId());
                if (!(imageSource.getProprietaire().getId().equals(client.getId()))) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "the image source of the photo does not belong to this client or does not exist");
                }
            } catch (EntityNotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "calendrier not found");
            }
        }

        Calendrier calendrier = null;
        try {
            calendrier = calendrierService.get(calId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "calendrier not found", e);
        }

        try {
            if (client.getId().equals(calendrier.getConsommateur().getId())) {
                calendrier = calendrierService.updateAndAddPage(calendrier, page, photos);
                return calendrierMapper.entityToDTO(calendrier);
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "this calendrier does not belong to this client");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
