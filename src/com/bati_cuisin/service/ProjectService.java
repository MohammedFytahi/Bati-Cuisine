package com.bati_cuisin.service;



import com.bati_cuisin.model.Client;
import com.bati_cuisin.model.MainOeuvre;
import com.bati_cuisin.model.Materiel;
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

//    public ProjectService(ProjectRepositoryInterface projectRepository, MaterielRepository materielRepository, MainOeuvreRepository mainOeuvreRepository) {
//        this.projectRepository = projectRepository;
//        this.materielRepository = materielRepository; // Initialize materielRepository
//        this.mainOeuvreRepository = mainOeuvreRepository; // Initialize mainOeuvreRepository
//    }
public ProjectService(ProjectRepositoryInterface projectRepository,
                      ClientRepository clientRepository,
                      MaterielRepository materielRepository,
                      MainOeuvreRepository mainOeuvreRepository) {
    this.projectRepository = projectRepository;
    this.clientRepository = clientRepository;
    this.materielRepository = materielRepository;
    this.mainOeuvreRepository = mainOeuvreRepository;
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
        getAllProjects().forEach(projet -> {
            System.out.println("ID Projet: " + projet.getIdProjet());
            System.out.println("Nom Projet: " + projet.getNomProjet());
            System.out.println("Marge Bénéficiaire: " + projet.getMargeBeneficiaire());
            System.out.println("Coût Total: " + projet.getCoutTotal());
            System.out.println("État du Projet: " + projet.getEtatProjet());
            System.out.println("Date de Création: " + projet.getDateCreation());

            // Retrieve and display materials associated with the project
            List<Materiel> materials = materielRepository.findMaterialsByProjectId(projet.getIdProjet());
            if (materials.isEmpty()) {
                System.out.println("  Aucun matériel trouvé pour ce projet.");
            } else {
                System.out.println("  Matériaux :");
                materials.forEach(materiel -> {
                    System.out.println("    - " + materiel.getNom() + ": " + materiel.getQuantite() + " unités, Coût unitaire: " + materiel.getCoutUnitaire());
                });
            }

            // Retrieve and display labor associated with the project
            List<MainOeuvre> laborList = mainOeuvreRepository.findMainOeuvreByProjectId(projet.getIdProjet());
            if (laborList.isEmpty()) {
                System.out.println("  Aucune main d'œuvre trouvée pour ce projet.");
            } else {
                System.out.println("  Main d'œuvre :");
                laborList.forEach(mainOeuvre -> {
                    System.out.println("    - " + mainOeuvre.getNom() + ": " + mainOeuvre.getHeuresTravail() + " heures, Taux horaire: " + mainOeuvre.getTauxHoraire());
                });
            }

            // Retrieve and display clients associated with the project
            List<Client> clients = clientRepository.findClientsByProjectId(projet.getIdProjet());
            if (clients.isEmpty()) {
                System.out.println("  Aucun client trouvé pour ce projet.");
            } else {
                System.out.println("  Clients :");
                clients.forEach(client -> {
                    System.out.println("    - " + client.getNom() + ", Adresse: " + client.getAdresse() + ", Téléphone: " + client.getTelephone() +
                            ", Professionnel: " + (client.isEst_professionnel() ? "Oui" : "Non"));
                });
            }

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