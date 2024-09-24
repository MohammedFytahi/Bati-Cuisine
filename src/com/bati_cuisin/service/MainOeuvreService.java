package com.bati_cuisin.service;

import com.bati_cuisin.model.MainOeuvre;
import com.bati_cuisin.repository.interfaces.MainOeuvreRepositoryInterface;

public class MainOeuvreService {

    private MainOeuvreRepositoryInterface mainOeuvreRepository;

    // Constructeur injectant le repository
    public MainOeuvreService(MainOeuvreRepositoryInterface mainOeuvreRepository) {
        this.mainOeuvreRepository = mainOeuvreRepository;
    }

    // Méthode pour ajouter une nouvelle main d'œuvre
    public void ajouterMainOeuvre(MainOeuvre mainOeuvre) {
        if (mainOeuvre.getTauxHoraire() > 0 && mainOeuvre.getHeuresTravail() > 0) {
            mainOeuvreRepository.ajouterMainOeuvre(mainOeuvre);
            System.out.println("Main d'œuvre ajoutée avec succès.");
        } else {
            System.out.println("Le taux horaire et les heures de travail doivent être supérieurs à zéro.");
        }
    }

//    // Méthode pour récupérer toutes les main d'œuvre
//    public List<MainOeuvre> getToutesLesMainOeuvre() {
//        return mainOeuvreRepository.getToutesLesMainOeuvre();
//    }
//
//    // Méthode pour trouver une main d'œuvre par ID
//    public MainOeuvre trouverParId(int id) {
//        return mainOeuvreRepository.trouverParId(id);
//    }
//
//    // Méthode pour mettre à jour une main d'œuvre
//    public void mettreAJourMainOeuvre(MainOeuvre mainOeuvre) {
//        if (mainOeuvre.getId() > 0) {
//            mainOeuvreRepository.mettreAJourMainOeuvre(mainOeuvre);
//            System.out.println("Main d'œuvre mise à jour avec succès.");
//        } else {
//            System.out.println("ID non valide pour la mise à jour.");
//        }
//    }
//
//    // Méthode pour supprimer une main d'œuvre par ID
//    public void supprimerMainOeuvre(int id) {
//        MainOeuvre mainOeuvre = mainOeuvreRepository.trouverParId(id);
//        if (mainOeuvre != null) {
//            mainOeuvreRepository.supprimerMainOeuvre(id);
//            System.out.println("Main d'œuvre supprimée avec succès.");
//        } else {
//            System.out.println("Main d'œuvre avec ID " + id + " non trouvée.");
//        }
//    }
}
