package com.bati_cuisin.service;

import com.bati_cuisin.model.MainOeuvre;
import com.bati_cuisin.repository.interfaces.MainOeuvreRepositoryInterface;

public class MainOeuvreService {

    private MainOeuvreRepositoryInterface mainOeuvreRepository;


    public MainOeuvreService(MainOeuvreRepositoryInterface mainOeuvreRepository) {
        this.mainOeuvreRepository = mainOeuvreRepository;
    }


    public void ajouterMainOeuvre(MainOeuvre mainOeuvre) {
        if (mainOeuvre.getTauxHoraire() > 0 && mainOeuvre.getHeuresTravail() > 0) {
            mainOeuvreRepository.ajouterMainOeuvre(mainOeuvre);
            System.out.println("Main d'œuvre ajoutée avec succès.");
        } else {
            System.out.println("Le taux horaire et les heures de travail doivent être supérieurs à zéro.");
        }
    }


}
