package fr.uga.l3miage.photonum.service;

import fr.uga.l3miage.photonum.data.domain.*;
import fr.uga.l3miage.photonum.data.repo.AlbumRepository;
import fr.uga.l3miage.photonum.data.repo.PageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Set;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;

    private final ClientService clientService;

    private final PageService pageService;

    private final PageRepository pageRepository;

    @Autowired
    public AlbumServiceImpl(AlbumRepository albumRepository, ClientService clientService, PageService pageService, PageRepository pageRepository) {
        this.albumRepository = albumRepository;
        this.clientService = clientService;
        this.pageService = pageService;
        this.pageRepository = pageRepository;
    }
    @Override
    public Album save(Long clientId, Album album) throws EntityNotFoundException {
        bindAlbumToClient(clientId, album);
        albumRepository.save(album);
        return album;
    }

    private void bindAlbumToClient(Long clientId, Album album) throws EntityNotFoundException {
        Client client = clientService.get(clientId);
        if (client == null) {
            throw new EntityNotFoundException("Client not found");
        }
        album.setConsommateur(client);
    }

    public Collection<Album> getAllAlbumsOfAClient(Long clientId) throws EntityNotFoundException {
        Client client = clientService.get(clientId);
        if (client == null) {
            throw new EntityNotFoundException("Client not found");
        }
        return albumRepository.getAllAlbumsOfAClient(clientId);
    }

    @Override
    public Album get(Long id) throws EntityNotFoundException {
        Album album = albumRepository.get(id);
        if (album == null) {
            throw new EntityNotFoundException("Album not found");
        }
        return album;
    }

    @Override
    public Collection<Album> list() {
        return albumRepository.all();
    }


    @Override
    public Album addPhoto(Album album, Photo photo, int numero) throws EntityNotFoundException {
        album.addPhoto(photo, numero);
        return update(album);
    }

    @Override
    public Album addPage(Album album, Page page) throws EntityNotFoundException {
        album.addPage(album, page);
        return update(album);
    }

    @Override
    public Album update(Album album) throws EntityNotFoundException {
        return albumRepository.update(album);
    }

}
