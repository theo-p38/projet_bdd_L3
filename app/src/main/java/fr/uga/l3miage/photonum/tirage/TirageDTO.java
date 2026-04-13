package fr.uga.l3miage.photonum.tirage;

import fr.uga.l3miage.photonum.client.ClientDTO;
import fr.uga.l3miage.photonum.data.domain.Client;
import fr.uga.l3miage.photonum.data.domain.Impression;
import fr.uga.l3miage.photonum.data.domain.SelectionTirage;
import fr.uga.l3miage.photonum.selectionTirage.SelectionTirageDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Data
@Getter
@Setter
public class TirageDTO{
        public Long id;
        @PositiveOrZero
        float prix;
        public Impression.Formats format;
        public Impression.Qualite qualite;
        public String statut;
        public ClientDTO consommateur;
        @Size(min = 1)
        public Set<SelectionTirageDTO> selections;
}