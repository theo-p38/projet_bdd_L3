package fr.uga.l3miage.photonum.calendrier;

import fr.uga.l3miage.photonum.data.domain.Calendrier;
import fr.uga.l3miage.photonum.data.domain.Impression;
import org.mapstruct.*;

import java.util.Collection;


@Mapper(componentModel = "spring")
public interface CalendrierMapper {

    CalendrierDTO entityToDTO(Calendrier calendrier);
    Calendrier dtoToEntity(CalendrierDTO calendrierDTO);

    Collection<CalendrierDTO> entityToDTO(Iterable<Calendrier> calendriers);
    Collection<Calendrier> dtoToEntity(Iterable<CalendrierDTO> calendrierDTOS);

    @EnumMapping(nameTransformationStrategy = "case", configuration = "lower")
    String enumToString(Impression.Statut statut);

    @InheritInverseConfiguration
    @ValueMapping(source = MappingConstants.NULL, target = "ENCOURS")
    @ValueMapping(source = "", target = "ENCOURS")
    Impression.Statut stringToEnum(String statut);


}
