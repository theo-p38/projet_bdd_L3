package fr.uga.l3miage.photonum.service;

import fr.uga.l3miage.photonum.data.domain.Client;
import fr.uga.l3miage.photonum.data.domain.Commande;
import fr.uga.l3miage.photonum.data.domain.Commande.Statut;
import fr.uga.l3miage.photonum.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
@Service
public interface CommandeService extends BaseService<Commande, Long> {



}
