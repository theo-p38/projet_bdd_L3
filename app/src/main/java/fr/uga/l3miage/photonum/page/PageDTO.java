package fr.uga.l3miage.photonum.page;

import fr.uga.l3miage.photonum.data.domain.Photo;
import fr.uga.l3miage.photonum.photo.PhotoDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class PageDTO{
        public Long id;
        @NotNull
        public int numero;
        public String description;
        public List<PhotoDTO> photos;
}