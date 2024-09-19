package com.bati_cuisin.service;



import com.bati_cuisin.model.Project;
import com.bati_cuisin.repository.ProjectRepositoryInterface;

public class ProjectService {
    private ProjectRepositoryInterface projectRepository;

    public ProjectService(ProjectRepositoryInterface projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void creerNouveauProjet(Project project) {
        projectRepository.ajouterProjet(project);
        System.out.println("Projet ajouté avec succès : " + project.getNomProjet());
    }

    public Project consulterProjet(int idProjet) {
        return projectRepository.trouverProjetParId(idProjet);
    }

    // Méthodes supplémentaires pour gérer les projets peuvent être ajoutées ici
}

