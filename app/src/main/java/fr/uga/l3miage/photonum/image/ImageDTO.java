package fr.uga.l3miage.photonum.image;

import fr.uga.l3miage.photonum.client.ClientDTO;
import fr.uga.l3miage.photonum.data.domain.Client;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ImageDTO{
    public Long id;
    @NotBlank
    public String chemin;
    public String info;
    @NotBlank
    public int resolution;
    @NotBlank
    public boolean estPartage;

    public ClientDTO proprietaire;
}
