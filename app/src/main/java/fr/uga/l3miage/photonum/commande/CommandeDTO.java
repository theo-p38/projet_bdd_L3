package fr.uga.l3miage.photonum.commande;

import fr.uga.l3miage.photonum.data.domain.Commande;
import fr.uga.l3miage.photonum.data.domain.Impression;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Data
@Getter
@Setter
public class CommandeDTO{
        public long id;
        @NotEmpty
        public Date dateDeCommande;
        @PositiveOrZero
        public float prixTotal;
        @NotEmpty
        public Commande.Statut statut;
}