package com.bati_cuisin.service;



import com.bati_cuisin.model.Project;
import com.bati_cuisin.repository.ProjectRepositoryInterface;

import java.sql.SQLException;

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
        return projectRepository.trouverProjetParId(idProjet);
    }

    public void mettreAJourCoutTotalProjet(int idProjet, double coutTotal) throws SQLException {
        projectRepository.updateCoutTotal(idProjet, coutTotal);
    }

}