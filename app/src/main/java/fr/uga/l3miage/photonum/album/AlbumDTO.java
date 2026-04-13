
package fr.uga.l3miage.photonum.album;

import fr.uga.l3miage.photonum.client.ClientDTO;
import fr.uga.l3miage.photonum.data.domain.Client;
import fr.uga.l3miage.photonum.data.domain.Impression;
import fr.uga.l3miage.photonum.data.domain.Page;
import fr.uga.l3miage.photonum.data.domain.Photo;
import fr.uga.l3miage.photonum.page.PageDTO;
import fr.uga.l3miage.photonum.photo.PhotoDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Data
@Getter
@Setter
public class AlbumDTO{
        public Long id;
        @PositiveOrZero
        public float prix;
        public Impression.Formats format;

        public Impression.Qualite qualite;
        public String statut;
        @NotBlank
        public String titre;
        public PhotoDTO photoDeCouverture;
        public ClientDTO consommateur;
        @Size(min = 1)
        public Set<PageDTO> pagesDAlbums;
}