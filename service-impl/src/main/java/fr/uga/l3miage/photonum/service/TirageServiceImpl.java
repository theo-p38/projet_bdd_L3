package fr.uga.l3miage.photonum.service;

import fr.uga.l3miage.photonum.data.domain.*;
import fr.uga.l3miage.photonum.data.repo.PhotoRepository;
import fr.uga.l3miage.photonum.data.repo.TirageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Service
@Transactional
public class TirageServiceImpl implements TirageService {

    private final TirageRepository tirageRepository;
    private final ClientService clientService;

    private final PhotoRepository photoRepository;

    private final SelectionTirageService selectionTirageService;

    @Autowired
    public TirageServiceImpl(TirageRepository tirageRepository, ClientService clientService, PhotoRepository photoRepository, SelectionTirageService selectionTirageService) {
        this.tirageRepository = tirageRepository;
        this.clientService = clientService;
        this.photoRepository = photoRepository;
        this.selectionTirageService = selectionTirageService;
    }
    @Override
    public Tirage save(Long clientId, Tirage tirage) throws EntityNotFoundException {
        bindTirageToClient(clientId, tirage);
        tirageRepository.save(tirage);
        return tirage;
    }

    private void bindTirageToClient(Long clientId, Tirage tirage) throws EntityNotFoundException {
        Client client = clientService.get(clientId);
        if (client == null) {
            throw new EntityNotFoundException("Client not found");
        }
        tirage.setConsommateur(client);
    }

    @Override
    public Collection<Tirage> getAllTiragesOfAClient(Long clientId) throws EntityNotFoundException {
        Client client = clientService.get(clientId);
        if (client == null) {
            throw new EntityNotFoundException("Client not found");
        }
        return tirageRepository.getAllTiragesOfAClient(clientId);
    }

    @Override
    public Tirage get(Long id) throws EntityNotFoundException {
        Tirage tirage = tirageRepository.get(id);
        if (tirage == null) {
            throw new EntityNotFoundException("Tirage not found");
        }
        return tirage;
    }

    @Override
    public Collection<Tirage> list() {
        return tirageRepository.all();
    }


    public Tirage addPhoto(Long tirageId, Long selectionId, Long photoId) throws EntityNotFoundException {
        Tirage tirage = get(tirageId);
        Set<SelectionTirage> selectionTirages = tirage.getSelections();
        for (SelectionTirage selectionTirage : selectionTirages) {
            if (selectionTirage.getId() == selectionId) {
                selectionTirage.setPhoto(photoRepository.get(photoId));
            }
        }
        tirage.setSelections(selectionTirages);
        return tirageRepository.save(tirage);
    }

    @Override
    public Tirage update(Tirage tirage) throws EntityNotFoundException {
        return tirageRepository.update(tirage);
    }

    @Override
    public Tirage addSelection(Tirage tirage, SelectionTirage selectionTirage) throws EntityNotFoundException {
        tirage.addSelection(selectionTirage);
        return update(tirage);
    }


}
