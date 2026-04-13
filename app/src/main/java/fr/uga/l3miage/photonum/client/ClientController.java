package fr.uga.l3miage.photonum.client;


import fr.uga.l3miage.photonum.commande.CommandeDTO;
import fr.uga.l3miage.photonum.commande.CommandeMapper;
import fr.uga.l3miage.photonum.data.domain.Client;
import fr.uga.l3miage.photonum.data.domain.Commande;
import fr.uga.l3miage.photonum.data.domain.Image;
import fr.uga.l3miage.photonum.data.domain.Impression;
import fr.uga.l3miage.photonum.image.ImageDTO;
import fr.uga.l3miage.photonum.image.ImageMapper;
import fr.uga.l3miage.photonum.impression.ImpressionDTO;
import fr.uga.l3miage.photonum.impression.ImpressionMapper;
import fr.uga.l3miage.photonum.service.ClientService;
import fr.uga.l3miage.photonum.service.EntityNotFoundException;
import fr.uga.l3miage.photonum.service.ImageService;
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
public class ClientController {
    private final ClientService clientService;
    private final ClientMapper clientMapper;
    private final ImpressionMapper impressionMapper;
    private final ImageService imageService;
    private final ImageMapper imageMapper;
    private final CommandeMapper commandeMapper;

    @Autowired
    public ClientController(ClientService clientService, ClientMapper clientMapper, ImpressionMapper impressionMapper,
                            ImageMapper imageMapper, CommandeMapper commandeMapper, ImageService imageService) {
        this.clientService = clientService;
        this.clientMapper = clientMapper;
        this.impressionMapper = impressionMapper;
        this.imageService = imageService;
        this.imageMapper = imageMapper;
        this.commandeMapper = commandeMapper;
    }

    /* Renvoie toutes les commandes d'un client */
    @GetMapping("/clients/{clientId}/commandes")
    public Collection<CommandeDTO> commandes(@PathVariable("clientId") @NotNull Long clientId) {
        Collection<Commande> commandes = clientService.retrieveCommandes(clientId);
        return commandeMapper.entityToDTO(commandes);
    }

    /******** Partie informations d'un compte client  **********/
    @GetMapping("/clients/{clientId}")
    public ClientDTO clientInfo(@PathVariable("clientId") @NotNull Long clientId) {

        Client client = null;
        try {
            client = clientService.get(clientId);
            return clientMapper.entityToDTO(client);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    /********* Partie informations d'un compte client fin ************/





}