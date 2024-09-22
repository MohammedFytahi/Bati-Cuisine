package com.bati_cuisin.service;

import com.bati_cuisin.model.Devis;
import com.bati_cuisin.repository.DevisRepositoryInterface;

import java.time.LocalDate;
import java.util.List;

public class DevisService {

    private DevisRepositoryInterface devisRepository;

    public DevisService(DevisRepositoryInterface devisRepository) {
        this.devisRepository = devisRepository;
    }

    // Créer un devis
    public void creerDevis(int idProjet, double montantEstime, LocalDate dateEmission, LocalDate dateValidite, boolean accepte) {
        devisRepository.insererDevis(idProjet, montantEstime, dateEmission, dateValidite, false);
    }

    // Obtenir un devis par son ID
    public Devis obtenirDevisParId(int idDevis) {
        return devisRepository.obtenirDevisParId(idDevis);
    }

    // Accepter ou refuser un devis
    public void accepterDevis(int idDevis, boolean accepte) {
        devisRepository.mettreAJourStatutDevis(idDevis, accepte);
    }

    // Supprimer un devis
    public void supprimerDevis(int idDevis) {
        devisRepository.supprimerDevis(idDevis);
    }

    // Obtenir tous les devis
    public List<Devis> obtenirTousLesDevis() {
        return devisRepository.obtenirTousLesDevis();
    }


}

