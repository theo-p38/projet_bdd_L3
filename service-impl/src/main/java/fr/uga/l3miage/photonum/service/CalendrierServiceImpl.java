package fr.uga.l3miage.photonum.service;

import fr.uga.l3miage.photonum.data.domain.*;
import fr.uga.l3miage.photonum.data.repo.CalendrierRepository;
import fr.uga.l3miage.photonum.data.repo.PageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class CalendrierServiceImpl implements CalendrierService {

    private final CalendrierRepository calendrierRepository;
    private final ClientService clientService;
    private final PageService pageService;

    private final PageRepository pageRepository;


    @Autowired
    public CalendrierServiceImpl(CalendrierRepository calendrierRepository, ClientService clientService, PageService pageService, PageRepository pageRepository) {
        this.calendrierRepository = calendrierRepository;
        this.clientService = clientService;
        this.pageService = pageService;
        this.pageRepository = pageRepository;
    }

    @Override
    public Calendrier save(Long clientId, Calendrier calendrier) throws EntityNotFoundException {
        bindCalendrierToClient(clientId, calendrier);
        calendrier.initPages();
        calendrierRepository.save(calendrier);
        return calendrier;
    }

    private void bindCalendrierToClient(Long clientId, Calendrier calendrier) throws EntityNotFoundException {
        Client client = clientService.get(clientId);
        if (client == null) {
            throw new EntityNotFoundException("Client not found");
        }
        calendrier.setConsommateur(client);
    }

    @Override
    public Collection<Calendrier> getAllCalendriersOfAClient(Long clientId) throws EntityNotFoundException {
        Client client = clientService.get(clientId);
        if (client == null) {
            throw new EntityNotFoundException("Client not found");
        }
        return calendrierRepository.getAllCalendriersOfAClient(clientId);
    }

    @Override
    public Calendrier get(Long id) throws EntityNotFoundException {
        Calendrier calendrier = calendrierRepository.get(id);
        if (calendrier == null) {
            throw new EntityNotFoundException("Calendrier not found");
        }
        return calendrier;
    }

    @Override
    public Collection<Calendrier> list() {
        return calendrierRepository.all();
    }


    public Calendrier addPhoto(Long calendrierId, int numero, Long photoId) throws EntityNotFoundException {
        Calendrier calendrier = get(calendrierId);
        Set<Page> pages = calendrier.getPagesDeCalendrier();
        for (Page page : pages) {
            if (page.getNumero() == numero) {
                pageService.addPhoto(page.getId(), photoId);
            }
        }
        calendrier.setPagesDeCalendrier(pages);
        return calendrierRepository.save(calendrier);
    }

    /*
    public Calendrier addPage(Long calendrierId, Long pageId, int numero) throws EntityNotFoundException {
        Calendrier calendrier = get(calendrierId);
        Set<Page> pages = calendrier.getPagesDeCalendrier();
        pages.add(pageRepository.get(pageId));
        calendrier.setPagesDeCalendrier(pages);
        return calendrierRepository.save(calendrier);
    }
    */

    @Override
    public Calendrier update(Calendrier calendrier) throws EntityNotFoundException {
        return calendrierRepository.update(calendrier);
    }


    @Override
    public Calendrier updateAndAddPage(Calendrier calendrier, Page page, Collection<Photo> photos) throws EntityNotFoundException{
        calendrier.addPhotoAndDescription(page, photos);
        return update(calendrier);
    }
}
