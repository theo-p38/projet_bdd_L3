package fr.uga.l3miage.photonum.photo;

import fr.uga.l3miage.photonum.data.domain.Image;
import fr.uga.l3miage.photonum.image.ImageDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PhotoDTO{
   public Long id;
   @NotBlank
   public String parametres;
   @NotNull
   public ImageDTO imageSource;
}
