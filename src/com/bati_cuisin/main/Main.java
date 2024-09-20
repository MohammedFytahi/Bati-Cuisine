package com.bati_cuisin.main;

import com.bati_cuisin.model.Client;
import com.bati_cuisin.model.Materiel;
import com.bati_cuisin.model.MainOeuvre;
import com.bati_cuisin.model.Project;
import com.bati_cuisin.repository.MainOeuvreRepository;
import com.bati_cuisin.service.ClientService;
import com.bati_cuisin.service.MainOeuvreService;
import com.bati_cuisin.service.MaterielService;
import com.bati_cuisin.service.ProjectService;
import com.bati_cuisin.repository.ClientRepository;
import com.bati_cuisin.repository.MaterielRepository;
import com.bati_cuisin.repository.ProjectRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {


        ProjectRepository projectRepository = new ProjectRepository();
        ClientRepository clientRepository = new ClientRepository();
        MaterielRepository materielRepository = new MaterielRepository();
        MainOeuvreRepository mainOeuvreRepository = new MainOeuvreRepository();

        ProjectService projectService = new ProjectService(projectRepository);
        ClientService clientService = new ClientService(clientRepository);
        MaterielService materielService = new MaterielService(materielRepository);
        MainOeuvreService mainOeuvreService = new MainOeuvreService(mainOeuvreRepository);

        while (true) {
            afficherMenuPrincipal();
            int choix = scanner.nextInt();
            scanner.nextLine();  // Consommer le retour à la ligne

            switch (choix) {
                case 1:
                    creerNouveauProjet(projectService, clientService, materielService, mainOeuvreService);
                    break;
                case 2:
                    // Afficher les projets existants (méthode à implémenter)
                    break;
                case 3:
                    // Calculer le coût d'un projet (méthode à implémenter)
                    break;
                case 4:
                    System.out.println("Merci d'utiliser notre application. À bientôt !");

                    return;
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
        }
    }

    private static void afficherMenuPrincipal() {
        System.out.println("\n=== Bienvenue dans l'application de gestion des projets de rénovation de cuisines ===");
        System.out.println("=== Menu Principal ===");
        System.out.println("1. Créer un nouveau projet");
        System.out.println("2. Afficher les projets existants");
        System.out.println("3. Calculer le coût d'un projet");
        System.out.println("4. Quitter");
        System.out.print("Choisissez une option : ");
    }

    private static void creerNouveauProjet(ProjectService projectService, ClientService clientService, MaterielService materielService, MainOeuvreService mainOeuvreService) {
        System.out.println("--- Recherche de client ---");
        System.out.println("Souhaitez-vous chercher un client existant ou en ajouter un nouveau ?");
        System.out.println("1. Chercher un client existant");
        System.out.println("2. Ajouter un nouveau client");
        System.out.print("Choisissez une option : ");
        int choixClient = scanner.nextInt();
        scanner.nextLine();  // Consommer le retour à la ligne

        Client client = null;

        switch (choixClient) {
            case 1:
                // Code pour chercher un client existant
                System.out.print("Entrez le nom du client : ");
                String nomClientExistant = scanner.nextLine();
                // Appel à la méthode pour récupérer le client (méthode à implémenter dans le service client)
                // client = clientService.getClientByName(nomClientExistant);
                System.out.println("Fonctionnalité non encore implémentée.");
                break;

            case 2:
                // Ajout d'un nouveau client
                System.out.print("Entrez le nom du client : ");
                String nom = scanner.nextLine();
                System.out.print("Entrez l'adresse du client : ");
                String adresse = scanner.nextLine();
                System.out.print("Entrez le numéro de téléphone du client : ");
                String telephone = scanner.nextLine();
                System.out.print("Est-ce un professionnel ? (true/false) : ");
                boolean estProfessionnel = scanner.nextBoolean();
                scanner.nextLine(); // Consommer le retour à la ligne

                // Création et ajout du client
                client = new Client(nom, adresse, telephone, estProfessionnel);
                scanner.nextLine(); // Consommer le retour à la ligne

                // Création et ajout du client
                client = new Client(nom, adresse, telephone, estProfessionnel);
                try {
                    int clientId = clientService.addClient(client); // Récupérer l'ID du client ajouté
                    client.setId(clientId); // Assigner l'ID au client
                    System.out.println("Client ajouté avec succès.");
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Erreur lors de l'ajout du client.");
                }
                break;

            default:
                System.out.println("Option invalide. Veuillez réessayer.");
                return;
        }

        if (client != null) {

            // Création du projet
            System.out.print("Entrez le nom du projet : ");
            String nomProjet = scanner.nextLine();

// Demander la marge bénéficiaire
            System.out.print("Entrez la marge bénéficiaire : ");
            double margeBeneficiaire = scanner.nextDouble();
            scanner.nextLine();




            Project projet = new Project(nomProjet, client.getId(), margeBeneficiaire, Project.EtatProjet.EN_COURS);

            projectService.creerNouveauProjet(projet); // Implémentez cette méthode dans le service projet
            int idProjet = projet.getIdProjet();
            System.out.println("Projet créé avec succès.");

            System.out.println("Souhaitez-vous ajouter des matériaux pour ce projet ?");
            System.out.println("1. Oui");
            System.out.println("2. Non");
            int choixMateriel = scanner.nextInt();
            scanner.nextLine();

            if (choixMateriel == 1) {
                boolean continuerAjout = true;
                while (continuerAjout) {
                    System.out.println("--- Ajout de Matériel ---");
                    System.out.print("Entrez le nom du matériel : ");
                    String nomMateriel = scanner.nextLine();
                    System.out.print("Entrez le taux de TVA : ");
                    double tauxTVA = scanner.nextDouble();
                    System.out.print("Entrez le coût unitaire : ");
                    double coutUnitaire = scanner.nextDouble();
                    System.out.print("Entrez la quantité : ");
                    double quantite = scanner.nextDouble();
                    System.out.print("Entrez le coût de transport : ");
                    double coutTransport = scanner.nextDouble();
                    System.out.print("Entrez le coefficient de qualité : ");
                    double coefficientQualite = scanner.nextDouble();
                    scanner.nextLine();

                    // Création et ajout du matériel
                    materielService.ajouterMateriel(nomMateriel, tauxTVA, coutUnitaire, quantite, coutTransport, coefficientQualite,idProjet);
                    System.out.println("Matériel ajouté avec succès.");

                    // Demander à l'utilisateur s'il souhaite ajouter un autre matériel
                    System.out.println("Souhaitez-vous ajouter un autre matériel ?");
                    System.out.println("1. Oui");
                    System.out.println("2. Non");
                    int choixContinuer = scanner.nextInt();
                    scanner.nextLine();

                    if (choixContinuer != 1) {
                        continuerAjout = false;
                    }
                }
            }


            System.out.println("Souhaitez-vous ajouter de la main d'œuvre pour ce projet ?");
            System.out.println("1. Oui");
            System.out.println("2. Non");
            int choixMainOeuvre = scanner.nextInt();
            scanner.nextLine();

            if (choixMainOeuvre == 1) {
                boolean continuerAjoutMainOeuvre = true;
                while (continuerAjoutMainOeuvre) {
                    System.out.println("--- Ajout de Main d'Œuvre ---");
                    System.out.print("Entrez le nom de la tâche : ");
                    String nomTache = scanner.nextLine();
                    System.out.print("Entrez le taux horaire : ");
                    double tauxHoraire = scanner.nextDouble();
                    System.out.print("Entrez le nombre d'heures de travail : ");
                    double heuresTravail = scanner.nextDouble();
                    System.out.print("Entrez le coût de déplacement : ");
                    double coutDeplacement = scanner.nextDouble();
                    System.out.print("Entrez le coefficient de qualité : ");
                    double coefficientQualite = scanner.nextDouble();
                    scanner.nextLine();


                    MainOeuvre mainOeuvre = new MainOeuvre(nomTache, tauxHoraire, heuresTravail, coutDeplacement, coefficientQualite,idProjet);
                    mainOeuvreService.ajouterMainOeuvre(mainOeuvre);
                    System.out.println("Main d'œuvre ajoutée avec succès.");


                    System.out.println("Souhaitez-vous ajouter une autre tâche de main d'œuvre ?");
                    System.out.println("1. Oui");
                    System.out.println("2. Non");
                    int choixContinuer = scanner.nextInt();
                    scanner.nextLine();

                    if (choixContinuer != 1) {
                        continuerAjoutMainOeuvre = false;
                    }
                }
            }

            System.out.println("Projet créé avec succès avec les matériaux et la main d'œuvre ajoutés.");
        } else {
            System.out.println("La création du projet a échoué. Aucun client sélectionné.");
        }
    }



}
