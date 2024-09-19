package com.bati_cuisin.repository;


import com.bati_cuisin.model.Project;


public interface ProjectRepositoryInterface {
    void ajouterProjet(Project project);


    Project trouverProjetParId(int idProjet);
    // D'autres méthodes comme mettre à jour un projet, supprimer un projet peuvent être ajoutées ici
}
