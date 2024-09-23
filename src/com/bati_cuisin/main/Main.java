package com.bati_cuisin.main;

import com.bati_cuisin.model.*;
import com.bati_cuisin.repository.*;
import com.bati_cuisin.service.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {


        ProjectRepository projectRepository = new ProjectRepository();
        ClientRepository clientRepository = new ClientRepository();
        MaterielRepository materielRepository = new MaterielRepository();
        MainOeuvreRepository mainOeuvreRepository = new MainOeuvreRepository();
        DevisRepository devisRepository = new DevisRepository();

        ProjectService projectService = new ProjectService(projectRepository);
        ClientService clientService = new ClientService(clientRepository);
        MaterielService materielService = new MaterielService(materielRepository);
        MainOeuvreService mainOeuvreService = new MainOeuvreService(mainOeuvreRepository);
        DevisService devisService = new DevisService(devisRepository);

        while (true) {
            afficherMenuPrincipal();
            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    creerNouveauProjet(projectService, clientService, materielService, mainOeuvreService,devisService);
                    break;
                case 2:

                    break;
                case 3:

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

    private static void creerNouveauProjet(ProjectService projectService, ClientService clientService, MaterielService materielService, MainOeuvreService mainOeuvreService, DevisService devisService) {
        System.out.println("--- Recherche de client ---");
        System.out.println("Souhaitez-vous chercher un client existant ou en ajouter un nouveau ?");
        System.out.println("1. Chercher un client existant");
        System.out.println("2. Ajouter un nouveau client");
        System.out.print("Choisissez une option : ");
        int choixClient = scanner.nextInt();
        scanner.nextLine();

        Client client = null;

        switch (choixClient) {
            case 1:
                System.out.print("Entrez le nom du client : ");
                String nomClientExistant = scanner.nextLine();

                try {
                    client = clientService.trouverClientParNom(nomClientExistant);
                    if (client != null) {
                        System.out.println("Client trouvé : " + client.getNom() + ", " + client.getAdresse());
                    } else {
                        System.out.println("Aucun client trouvé avec ce nom.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Erreur lors de la recherche du client.");
                }
                break;

            case 2:
                System.out.print("Entrez le nom du client : ");
                String nom = scanner.nextLine();
                System.out.print("Entrez l'adresse du client : ");
                String adresse = scanner.nextLine();
                System.out.print("Entrez le numéro de téléphone du client : ");
                String telephone = scanner.nextLine();
                System.out.print("Est-ce un professionnel ? (true/false) : ");
                boolean estProfessionnel = scanner.nextBoolean();
                scanner.nextLine();

                client = new Client(nom, adresse, telephone, estProfessionnel);
                try {
                    int clientId = clientService.addClient(client);
                    client.setId(clientId);
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
            System.out.print("Entrez le nom du projet : ");
            String nomProjet = scanner.nextLine();

            System.out.print("Entrez la marge bénéficiaire : ");
            double margeBeneficiaire = scanner.nextDouble();
            scanner.nextLine();

            Project projet = new Project(nomProjet, client.getId(), margeBeneficiaire, Project.EtatProjet.EN_COURS);
            projectService.creerNouveauProjet(projet);
            int idProjet = projet.getIdProjet();
            System.out.println("Projet créé avec succès.");

            double totalCost = 0.0;

            // Ajout de matériaux
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

                    double coutMateriel = (coutUnitaire * quantite + coutTransport) * coefficientQualite * (1 + tauxTVA / 100);
                    totalCost += coutMateriel;

                    materielService.ajouterMateriel(nomMateriel, tauxTVA, coutUnitaire, quantite, coutTransport, coefficientQualite, idProjet);
                    System.out.println("Matériel ajouté avec succès.");
                    System.out.println("le coût du matériel : " + coutMateriel);

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

            // Ajout de main d'œuvre
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

                    double coutMainOeuvre = (tauxHoraire * heuresTravail + coutDeplacement) * coefficientQualite;
                    totalCost += coutMainOeuvre;

                    MainOeuvre mainOeuvre = new MainOeuvre(nomTache, tauxHoraire, heuresTravail, coutDeplacement, coefficientQualite, idProjet);
                    mainOeuvreService.ajouterMainOeuvre(mainOeuvre);
                    System.out.println("Main d'œuvre ajoutée avec succès.");
                    System.out.println("le coût de main d'œuvre : " + coutMainOeuvre);

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

            projet.setCoutTotal(totalCost);

            // Calcul du coût total avec remise
            double coutTotalAvecRemise = calculerCoutTotalAvecRemise(totalCost, client);
            System.out.println("Coût total du projet (sans marge bénéficiaire) : " + totalCost);
            System.out.println("Coût total du projet (avec marge bénéficiaire) : " + projet.calculerCoutTotalAvecMarge());
            System.out.println("Coût total du projet (avec remise) : " + coutTotalAvecRemise);

            try {
                projectService.mettreAJourCoutTotalProjet(projet.getIdProjet(), coutTotalAvecRemise);
                System.out.println("Coût total mis à jour avec succès dans la base de données.");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur lors de la mise à jour du coût total dans la base de données.");
            }

            // Enregistrement du devis
            System.out.println("--- Enregistrement du Devis ---");
            System.out.print("Entrez la date d'émission du devis (format : jj/mm/aaaa) : ");
            String dateEmissionStr = scanner.nextLine().trim();
            LocalDate dateEmission = LocalDate.parse(dateEmissionStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            System.out.print("Entrez la date de validité du devis (format : jj/mm/aaaa) : ");
            String dateValiditeStr = scanner.nextLine().trim();
            LocalDate dateValidite = LocalDate.parse(dateValiditeStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            System.out.print("Souhaitez-vous enregistrer le devis ? (y/n) : ");
            String confirmation = scanner.nextLine();

            boolean accepte = false;
            if (confirmation.equalsIgnoreCase("y")) {
                accepte = true;
                Devis devis = new Devis(dateEmission, dateValidite, projet.getIdProjet(), coutTotalAvecRemise, accepte);
                devisService.creerDevis(projet.getIdProjet(), coutTotalAvecRemise, dateEmission, dateValidite, accepte);
                System.out.println("Devis enregistré avec succès !");
            } else {
                System.out.println("Enregistrement du devis annulé.");
            }

            System.out.println("Projet créé avec succès avec les matériaux, la main d'œuvre, et le devis ajoutés.");
        } else {
            System.out.println("La création du projet a échoué. Aucun client sélectionné.");
        }
    }

    // Méthode pour calculer le coût total avec remise
    private static double calculerCoutTotalAvecRemise(double totalCost, Client client) {
        final double REMISE_PROFESSIONNEL = 0.10; // 10% pour les professionnels
        double remise = 0;

        if (client.isEst_professionnel()) {
            remise = REMISE_PROFESSIONNEL;
        }

        return totalCost * (1 - remise);
    }



}
