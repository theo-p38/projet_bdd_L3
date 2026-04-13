package fr.uga.l3miage.photonum.service;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;


import fr.uga.l3miage.photonum.data.domain.Commande;
import fr.uga.l3miage.photonum.data.domain.Commande.Statut;
import fr.uga.l3miage.photonum.data.repo.CommandeRepository;
import org.springframework.stereotype.Service;

@Service
public class CommandeServiceImpl implements CommandeService {

    private final CommandeRepository commandeRepository;

    @Autowired
    public CommandeServiceImpl(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }

    @Override
    public Commande get(Long id) throws EntityNotFoundException {
        return commandeRepository.get(id);
    }

    @Override
    public Collection<Commande> list() {
        return commandeRepository.all();
    }

    @Override
    public Commande update(Commande commande) throws EntityNotFoundException {
        return commandeRepository.save(commande);
    }

}
