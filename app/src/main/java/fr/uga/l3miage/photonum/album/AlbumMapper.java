package fr.uga.l3miage.photonum.album;


import fr.uga.l3miage.photonum.data.domain.Album;
import fr.uga.l3miage.photonum.data.domain.Impression;
import org.mapstruct.*;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface AlbumMapper {

    AlbumDTO entityToDTO(Album album);
    Album dtoToEntity(AlbumDTO albumDTO);

    Collection<AlbumDTO> entityToDTO(Iterable<Album> albums);
    Collection<Album> dtoToEntity(Iterable<AlbumDTO> albumDTOS);

    @EnumMapping(nameTransformationStrategy = "case", configuration = "lower")
    String enumToString(Impression.Statut statut);

    @InheritInverseConfiguration
    @ValueMapping(source = MappingConstants.NULL, target = "ENCOURS")
    @ValueMapping(source = "", target = "ENCOURS")
    Impression.Statut stringToEnum(String statut);

}
