package fr.uga.l3miage.photonum.commande;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ch.qos.logback.core.net.server.Client;
import fr.uga.l3miage.photonum.data.domain.Commande;
import fr.uga.l3miage.photonum.data.domain.Commande.Statut;
import fr.uga.l3miage.photonum.service.ClientService;
import fr.uga.l3miage.photonum.service.CommandeService;
import fr.uga.l3miage.photonum.service.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class CommandeController {

    private final CommandeService commandeService;
    private final CommandeMapper commandeMapper;
    private final ClientService clientService;

    @Autowired
    public CommandeController(CommandeService commandeService, CommandeMapper commandeMapper, ClientService clientService) {
        this.commandeMapper = commandeMapper;
        this.commandeService = commandeService;
        this.clientService = clientService;
    }

    /* Renvoie la commande d'un client */
    @GetMapping("/clients/{clientId}/commandes/{commandeId}")
    public CommandeDTO commande(@PathVariable("clientId") @NotNull Long clientId, @PathVariable("commandeId") @NotNull Long commandeId) {
        Commande commande = clientService.getCommande(clientId, commandeId);
        return commandeMapper.entityToDTO(commande);
    }


}
