package com.bati_cuisin.service;



import com.bati_cuisin.model.Project;
import com.bati_cuisin.repository.ProjectRepositoryInterface;

import java.sql.SQLException;
import java.util.Optional;

public class ProjectService {
    private ProjectRepositoryInterface projectRepository;

    public ProjectService(ProjectRepositoryInterface projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void creerNouveauProjet(Project project) {
        projectRepository.ajouterProjet(project);
        if (project.getIdProjet() > 0) {
            System.out.println("Projet ajouté avec succès : " + project.getNomProjet() + " avec ID : " + project.getIdProjet());
        } else {
            System.out.println("Erreur lors de l'ajout du projet.");
        }
    }



    public Project consulterProjet(int idProjet) {
        Optional<Project> optionalProject = projectRepository.trouverProjetParId(idProjet);

        // Handling the Optional. If a project is present, return it, otherwise throw an exception or handle the case
        return optionalProject.orElseThrow(() -> new IllegalArgumentException("Projet avec l'ID " + idProjet + " n'existe pas."));
    }


    public void mettreAJourCoutTotalProjet(int idProjet, double coutTotal) throws SQLException {
        projectRepository.updateCoutTotal(idProjet, coutTotal);
    }

}