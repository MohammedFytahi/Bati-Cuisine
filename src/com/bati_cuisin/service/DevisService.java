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


    public void creerDevis(int idProjet, double montantEstime, LocalDate dateEmission, LocalDate dateValidite, boolean accepte) {
        devisRepository.insererDevis(idProjet, montantEstime, dateEmission, dateValidite, accepte);
    }


    public Devis obtenirDevisParId(int idDevis) {
        return devisRepository.obtenirDevisParId(idDevis);
    }


    public void accepterDevis(int idDevis, boolean accepte) {
        devisRepository.mettreAJourStatutDevis(idDevis, accepte);
    }


    public void supprimerDevis(int idDevis) {
        devisRepository.supprimerDevis(idDevis);
    }

    // Obtenir tous les devis
    public List<Devis> obtenirTousLesDevis() {
        return devisRepository.obtenirTousLesDevis();
    }


}

