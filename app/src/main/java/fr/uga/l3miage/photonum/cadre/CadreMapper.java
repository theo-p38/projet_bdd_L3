package fr.uga.l3miage.photonum.cadre;


import fr.uga.l3miage.photonum.data.domain.Cadre;
import fr.uga.l3miage.photonum.data.domain.Impression;
import org.mapstruct.*;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface CadreMapper {

    CadreDTO entityToDTO(Cadre cadre);
    Cadre dtoToEntity(CadreDTO cadreDTO);

    Collection<CadreDTO> entityToDTO(Iterable<Cadre> cadres);
    Collection<Cadre> dtoToEntity(Iterable<CadreDTO> cadreDTOS);

    @EnumMapping(nameTransformationStrategy = "case", configuration = "lower")
    String enumToString(Impression.Statut statut);

    @InheritInverseConfiguration
    @ValueMapping(source = MappingConstants.NULL, target = "ENCOURS")
    @ValueMapping(source = "", target = "ENCOURS")
    Impression.Statut stringToEnum(String statut);

}
