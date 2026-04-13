package fr.uga.l3miage.photonum.impression;

import fr.uga.l3miage.photonum.client.ClientDTO;
import fr.uga.l3miage.photonum.data.domain.Client;
import fr.uga.l3miage.photonum.data.domain.Impression;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Data
@Getter
@Setter
public class ImpressionDTO{
    public Long id;
    public Impression.Statut statut;
    public float prix;
    public Impression.Formats format;
    public Impression.Qualite qualite;
    public ClientDTO consommateur;
}