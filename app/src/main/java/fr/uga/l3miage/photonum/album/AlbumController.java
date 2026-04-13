package fr.uga.l3miage.photonum.album;


import fr.uga.l3miage.photonum.cadre.CadreDTO;
import fr.uga.l3miage.photonum.calendrier.CalendrierDTO;
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
public class AlbumController {
    private final AlbumService albumService;
    private final AlbumMapper albumMapper;
    private final PageMapper pageMapper;
    private final PhotoService photoService;
    private final PhotoMapper photoMapper;
    private final PageService pageService;
    private final ClientService clientService;
    private final ImageService imageService;

    @Autowired
    public AlbumController(AlbumService albumService, AlbumMapper albumMapper, PageMapper pageMapper,
                           PhotoService photoService, PhotoMapper photoMapper, PageService pageService,
                           ClientService clientService, ImageService imageService) {
        this.albumService = albumService;
        this.albumMapper = albumMapper;
        this.pageMapper = pageMapper;
        this.photoService = photoService;
        this.photoMapper = photoMapper;
        this.pageService = pageService;
        this.clientService = clientService;
        this.imageService = imageService;
    }

    @GetMapping("/clients/{clientId}/impressions/albums")
    public Collection<AlbumDTO> albums(@PathVariable("clientId") @NotNull Long id) {
        try {
            Collection<Album> albums = albumService.getAllAlbumsOfAClient(id);
            return albumMapper.entityToDTO(albums);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "client not found");
        }
    }

    /* Crée un nouvel album pour un client */
    @PostMapping(value = "/clients/{clientId}/impressions/albums", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumDTO newAlbum(@PathVariable("clientId") @NotNull Long clientId, @RequestBody @Valid AlbumDTO albumDTO) {
        try {
            Album saved = albumService.save(clientId, albumMapper.dtoToEntity(albumDTO));
            return albumMapper.entityToDTO(saved);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "client not found");
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    /* Update an album : NOTE : pas dans le cahier des charges
    @PutMapping("/clients/{clientId}/impressions/albums")
    public AlbumDTO updateAlbum(@PathVariable("clientId") @NotNull Long clientId, @RequestBody @Valid AlbumDTO albumDTO) {
        try {
            if (clientId.equals(albumDTO.id)) {
                var updated = albumService.update(albumMapper.dtoToEntity(albumDTO));
                return albumMapper.entityToDTO(updated);
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

     */

    /**
     * Create a new album page
     */
    @PostMapping(value = "/clients/{clientId}/impressions/albums/{albId}/pages", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumDTO newAlbumPage(@PathVariable("clientId") @NotNull Long clientId, @PathVariable("albId") @NotNull Long albId,
                                 @RequestBody @Valid PageDTO pageDTO) {

        Page page = pageMapper.dtoToEntity(pageDTO);

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
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "L'une des photos n'est pas dérivé d'une image appartenant à ce client");
                }
            } catch (EntityNotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Photo not found");
            }
        }

        Album album = null;
        try {
            album = albumService.get(albId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "album not found", e);
        }


        try {
            if (client.getId().equals(album.getConsommateur().getId())) {
                album = albumService.addPage(album, page);
                return albumMapper.entityToDTO(album);
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "this calendrier does not belong to this client");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }


    /**
     * Create a new photo in the page of an album of a client
     */
    @PostMapping(value = "/clients/{clientId}/impressions/albums/{albId}/pages/{numero}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumDTO newAlbumPhoto(@PathVariable("clientId") @NotNull Long clientId, @PathVariable("albId") @NotNull Long albId,
                                  @PathVariable("numero") @NotNull int numero, @RequestBody @Valid PhotoDTO photoDTO) {
        if (numero < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "numero must be an integer superior or equal 1");
        }

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

        Album album = null;
        if (imageSourcePhoto.getProprietaire().getId().equals(client.getId())) {
            try {
                album = albumService.get(albId);
            } catch (EntityNotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "cadre not found");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le propriétaire de l'image source de la photo n'est pas le client id");
        }

        if (album.getPagesDAlbums().size() < numero ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (client.getId().equals(album.getConsommateur().getId())) {
            try {
                album = albumService.addPhoto(album, photo, numero);
                return albumMapper.entityToDTO(album);
            } catch (EntityNotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "this cadre does not belong to this client");
        }
    }
}
