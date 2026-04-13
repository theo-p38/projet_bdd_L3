package fr.uga.l3miage.photonum.service;

import fr.uga.l3miage.photonum.data.domain.Page;
import fr.uga.l3miage.photonum.data.domain.Photo;
import fr.uga.l3miage.photonum.data.repo.PageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class PageServiceImpl implements PageService {

    private final PageRepository pageRepository;

    private final PhotoService photoService;
    @Autowired
    public PageServiceImpl(PageRepository pageRepository, PhotoService photoService) {
        this.pageRepository = pageRepository;
        this.photoService = photoService;
    }

    @Override
    public Page get(Long id) throws EntityNotFoundException {
        return pageRepository.get(id);
    }

    @Override
    public Collection<Page> list() {
        return pageRepository.all();
    }

    @Override
    public Page update(Page page) throws EntityNotFoundException {
        return pageRepository.save(page);
    }

    @Override
    public Page addPhoto(Long idPage, Long idPhoto) throws EntityNotFoundException {
        Page page = pageRepository.get(idPage);
        List<Photo> photos = page.getPhotos();
        photos.add(photoService.get(idPhoto));
        return pageRepository.save(page);
    }
}
