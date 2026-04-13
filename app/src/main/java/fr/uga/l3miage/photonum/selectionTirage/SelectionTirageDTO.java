package fr.uga.l3miage.photonum.selectionTirage;

import fr.uga.l3miage.photonum.data.domain.Photo;
import fr.uga.l3miage.photonum.photo.PhotoDTO;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SelectionTirageDTO{
        public Long id;
        @NotEmpty
        public int quantite;
        public PhotoDTO photo;
}
