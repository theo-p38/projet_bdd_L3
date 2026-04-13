package fr.uga.l3miage.photonum.selectionTirage;



import fr.uga.l3miage.photonum.data.domain.SelectionTirage;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface SelectionTirageMapper {
    SelectionTirageDTO entityToDTO(SelectionTirage selectiontirage);
    SelectionTirage dtoToEntity(SelectionTirageDTO selectiontirageDTO);

    Collection<SelectionTirageDTO> entityToDTO(Iterable<SelectionTirage> selectiontirages);
    Collection<SelectionTirage> dtoToEntity(Iterable<SelectionTirageDTO> selectiontirageDTOS);
}
