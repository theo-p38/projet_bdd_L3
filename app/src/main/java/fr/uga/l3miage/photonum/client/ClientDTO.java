package fr.uga.l3miage.photonum.client;

import java.util.Set;

import fr.uga.l3miage.photonum.commande.CommandeDTO;
import fr.uga.l3miage.photonum.data.domain.Commande;
import fr.uga.l3miage.photonum.data.domain.Image;
import fr.uga.l3miage.photonum.data.domain.Impression;
import fr.uga.l3miage.photonum.image.ImageDTO;
import fr.uga.l3miage.photonum.impression.ImpressionDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ClientDTO {
        public Long id;
        @NotBlank
        public String nom;
        @Email
        @NotBlank
        public String adresseMail;
        @NotBlank
        public String prenoms;
        @NotBlank
        public String adressePostale;
        @NotBlank
        public String motDePasse;
        @Size(min = 0)
        public Set<CommandeDTO> commandes;
        //@Size(min = 0)
        //public Set<ImpressionDTO> impressions;
        @Size(min = 0)
        public Set<ImageDTO> images;
}