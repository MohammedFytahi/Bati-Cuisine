package com.bati_cuisin.service;



import com.bati_cuisin.model.Project;
import com.bati_cuisin.repository.implementation.ClientRepository;
import com.bati_cuisin.repository.implementation.MainOeuvreRepository;
import com.bati_cuisin.repository.implementation.MaterielRepository;
import com.bati_cuisin.repository.interfaces.ProjectRepositoryInterface;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ProjectService {
    private ProjectRepositoryInterface projectRepository;

    private ClientRepository clientRepository;
    private MaterielRepository materielRepository;
    private MainOeuvreRepository mainOeuvreRepository;

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


        return optionalProject.orElseThrow(() -> new IllegalArgumentException("Projet avec l'ID " + idProjet + " n'existe pas."));
    }


    public void mettreAJourCoutTotalProjet(int idProjet, double coutTotal) throws SQLException {
        projectRepository.updateCoutTotal(idProjet, coutTotal);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public void displayAllProjects() {
        getAllProjects().stream()
                .forEach(projet -> {
                    System.out.println("ID Projet: " + projet.getIdProjet());
                    System.out.println("Nom Projet: " + projet.getNomProjet());
                    System.out.println("Marge Bénéficiaire: " + projet.getMargeBeneficiaire());
                    System.out.println("Coût Total: " + projet.getCoutTotal());
                    System.out.println("État du Projet: " + projet.getEtatProjet());
                    System.out.println("Date de Création: " + projet.getDateCreation());
                    System.out.println("--------------------------");
                });
    }

    public void modifyProjectState(int projectId, Project.EtatProjet newState) {
        projectRepository.updateProjectState(projectId, newState);
        System.out.println("L'état du projet avec ID " + projectId + " a été modifié en " + newState);
    }


    public void changeProjectState() {
        Scanner scanner = new Scanner(System.in);


        List<Project> projects = projectRepository.findAll();
        System.out.println("Projets disponibles :");
        for (Project project : projects) {
            System.out.println("ID: " + project.getIdProjet() + ", Nom: " + project.getNomProjet());
        }

        // Demander à l'utilisateur de choisir un projet
        System.out.print("Entrez l'ID du projet à modifier : ");
        int projectId = scanner.nextInt();

        System.out.print("Entrez le nouvel état du projet (EN_COURS, TERMINE, ANNULE) : ");
        String stateInput = scanner.next().toUpperCase();
        Project.EtatProjet newState;

        try {
            newState = Project.EtatProjet.valueOf(stateInput);
            modifyProjectState(projectId, newState);
        } catch (IllegalArgumentException e) {
            System.out.println("État invalide. Veuillez entrer un état valide.");
        }
    }
    public void updateProjectState(int projectId, Project.EtatProjet newState) {
        projectRepository.updateProjectState(projectId, newState);
    }

}