package fr.uga.l3miage.photonum.tirage;


import fr.uga.l3miage.photonum.data.domain.Impression;
import fr.uga.l3miage.photonum.data.domain.Tirage;
import org.mapstruct.*;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface TirageMapper {

    TirageDTO entityToDTO(Tirage tirage);
    Tirage dtoToEntity(TirageDTO tirageDTO);

    Collection<TirageDTO> entityToDTO(Iterable<Tirage> tirages);
    Collection<Tirage> dtoToEntity(Iterable<TirageDTO> tirageDTOS);

    @EnumMapping(nameTransformationStrategy = "case", configuration = "lower")
    String enumToString(Impression.Statut statut);

    @InheritInverseConfiguration
    @ValueMapping(source = MappingConstants.NULL, target = "ENCOURS")
    @ValueMapping(source = "", target = "ENCOURS")
    Impression.Statut stringToEnum(String statut);

}
