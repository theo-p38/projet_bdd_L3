package fr.uga.l3miage.photonum.service;

import fr.uga.l3miage.photonum.data.domain.Cadre;
import fr.uga.l3miage.photonum.data.domain.Client;
import fr.uga.l3miage.photonum.data.domain.Photo;
import fr.uga.l3miage.photonum.data.repo.CadreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class CadreServiceImpl implements CadreService {

    private final CadreRepository cadreRepository;
    private final ClientService clientService;

    private final PhotoService photoService;


    @Autowired
    public CadreServiceImpl(CadreRepository cadreRepository, ClientService clientService, PhotoService photoService) {
        this.cadreRepository = cadreRepository;
        this.clientService = clientService;
        this.photoService = photoService;
    }
    @Override
    public Cadre save(Long clientId, Cadre cadre) throws EntityNotFoundException {
        bindCalendrierToClient(clientId, cadre);
        cadreRepository.save(cadre);
        return cadre;
    }

    private void bindCalendrierToClient(Long clientId, Cadre cadre) throws EntityNotFoundException {
        Client client = clientService.get(clientId);
        if (client == null) {
            throw new EntityNotFoundException("Client not found");
        }
        cadre.setConsommateur(client);

    }

    @Override
    public Collection<Cadre> getAllCadresOfAClient(Long clientId) throws EntityNotFoundException {
        Client client = clientService.get(clientId);
        if (client == null) {
            throw new EntityNotFoundException("Client not found");
        }
        return cadreRepository.getAllCadresOfAClient(clientId);
    }


    @Override
    public Cadre get(Long id) throws EntityNotFoundException {
        Cadre cadre = cadreRepository.get(id);
        if (cadre == null) {
            throw new EntityNotFoundException("Cadre not found");
        }
        return cadre;
    }

    @Override
    public Collection<Cadre> list() {
        return cadreRepository.all();
    }

    @Override
    public Cadre update(Cadre cadre) throws EntityNotFoundException {
        return cadreRepository.update(cadre);
    }

    @Override
    public Cadre addPhoto(Cadre cadre, Photo photo) throws EntityNotFoundException {
        cadre.addPhoto(photo);
        return update(cadre);
    }
}
