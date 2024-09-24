package com.bati_cuisin.service;

import com.bati_cuisin.model.Devis;
import com.bati_cuisin.repository.interfaces.DevisRepositoryInterface;

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





}

