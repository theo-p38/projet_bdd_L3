package fr.uga.l3miage.photonum.service;

import fr.uga.l3miage.photonum.data.domain.SelectionTirage;
import fr.uga.l3miage.photonum.service.base.BaseService;

import java.util.Set;

public interface SelectionTirageService extends BaseService<SelectionTirage, Long> {

    SelectionTirage save(Long tirageId, SelectionTirage selectionTirage) throws EntityNotFoundException;

    SelectionTirage get(Long selectionTirageId) throws EntityNotFoundException;

    SelectionTirage update(SelectionTirage selectionTirage) throws EntityNotFoundException;

    Set<SelectionTirage> list();
}
