package fr.uga.l3miage.photonum.calendrier;

import fr.uga.l3miage.photonum.client.ClientDTO;
import fr.uga.l3miage.photonum.data.domain.Client;
import fr.uga.l3miage.photonum.data.domain.Impression;
import fr.uga.l3miage.photonum.data.domain.Page;
import fr.uga.l3miage.photonum.page.PageDTO;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Data
@Getter
@Setter
public class CalendrierDTO{
        public Long id;
        @PositiveOrZero
        public float prix;
        public Impression.Formats format;
        public Impression.Qualite qualite;
        public String statut;
        public ClientDTO consommateur;
        @Size(min = 12, max = 12)
        public Set<PageDTO> pagesDeCalendrier;
}