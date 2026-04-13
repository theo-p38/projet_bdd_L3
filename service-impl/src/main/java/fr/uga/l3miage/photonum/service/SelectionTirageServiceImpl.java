package fr.uga.l3miage.photonum.service;

import fr.uga.l3miage.photonum.data.domain.SelectionTirage;
import fr.uga.l3miage.photonum.data.repo.SelectionTirageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Service
@Transactional
public class SelectionTirageServiceImpl implements SelectionTirageService{

    private final SelectionTirageRepository selectionTirageRepository;

    @Autowired
    public SelectionTirageServiceImpl(SelectionTirageRepository selectionTirageRepository) {
        this.selectionTirageRepository = selectionTirageRepository;
    }

    @Override
    public SelectionTirage save(Long tirageId, SelectionTirage selectionTirage) throws EntityNotFoundException {
        return selectionTirageRepository.save(selectionTirage);
    }


    public SelectionTirage get(Long selectionTirageId) throws EntityNotFoundException {
        return selectionTirageRepository.get(selectionTirageId);
    }

    @Override
    public SelectionTirage update(SelectionTirage selectionTirage) throws EntityNotFoundException {
        return null;
    }

    @Override
    public Set<SelectionTirage> list() {
        return null;
    }

}
