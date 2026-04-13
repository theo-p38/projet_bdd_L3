package fr.uga.l3miage.photonum.service;

import fr.uga.l3miage.photonum.data.domain.Client;
import fr.uga.l3miage.photonum.data.domain.Commande;
import fr.uga.l3miage.photonum.data.domain.Image;
import fr.uga.l3miage.photonum.data.domain.Impression;
import fr.uga.l3miage.photonum.data.repo.ClientRepository;
import fr.uga.l3miage.photonum.data.repo.CommandeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final CommandeRepository commandeRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, CommandeRepository commandeRepository) {
        this.clientRepository = clientRepository;
        this.commandeRepository = commandeRepository;
    }

    @Override
    public Client get(Long clientId) throws EntityNotFoundException {
        Client client = clientRepository.get(clientId);
        if (client == null) {
            throw new EntityNotFoundException("Photo not found");
        }
        return client;
    }

    @Override
    public Collection<Client> list() {
        return clientRepository.all();
    }

    @Override
    public Client update(Client client) throws EntityNotFoundException {
        return clientRepository.save(client);
    }

    @Override
    public List<Commande> retrieveCommandes(Long clientId) {
        return clientRepository.retrieveCommandes(clientId);
    }

    @Override
    public Commande getCommande(Long clientId, Long commandeId) {
        return clientRepository.getCommande(clientId, commandeId);
    }

    @Override
    public List<Impression> getImpressions(Long id) {
        return clientRepository.getImpressions(id);
    }
    
    @Override
    public List<Image> getImages(Long id) {
        return clientRepository.getImages(id);
    }

    public Client addCommande (Long clientId, Long CommandeId) throws EntityNotFoundException {
        Client client = clientRepository.get(clientId);
        Commande commande = commandeRepository.get(CommandeId);
        client.getCommandes().add(commande);
        return clientRepository.save(client);
    }
}

