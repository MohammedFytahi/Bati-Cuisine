package com.bati_cuisin.main;

import com.bati_cuisin.model.*;
import com.bati_cuisin.repository.implementation.*;
import com.bati_cuisin.service.*;
import com.bati_cuisin.util.InputValidator;
import com.bati_cuisin.util.ValidationException;

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
        InputValidator validator = new InputValidator();

        ProjectService projectService = new ProjectService(projectRepository,
                clientRepository,
                materielRepository,
                mainOeuvreRepository);
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
                    creerNouveauProjet(projectService, clientService, materielService, mainOeuvreService,devisService, validator);
                    break;
                case 2:

                    projectService.displayAllProjects();
                    break;
                case 3:

                    projectService.changeProjectState();
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
        System.out.println("3. Modifier l'etat d'un projet");
        System.out.println("4. Quitter");
        System.out.print("Choisissez une option : ");
    }

    private static void creerNouveauProjet(ProjectService projectService, ClientService clientService, MaterielService materielService, MainOeuvreService mainOeuvreService, DevisService devisService, InputValidator validator) {
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
                String nom = "";

                while (true) {
                    System.out.print("Entrez le nom du client : ");
                    nom = scanner.nextLine();


                    if (InputValidator.validateName(nom)) {

                        break;
                    } else {

                        System.out.println("Nom invalide. Veuillez entrer un nom valide.");
                    }
                }
                String adresse = "";
                while (true) {
                    System.out.print("Entrez l'adresse du client : ");
                    adresse = scanner.nextLine();

                    if (InputValidator.validateAddress(adresse)) {
                        break;
                    } else {
                        System.out.println("Adresse invalide. Veuillez entrer une adresse valide.");
                    }
                }

                String telephone = "";

                while (true) {
                    System.out.print("Entrez le numéro de téléphone du client (10 chiffres) : ");
                    telephone = scanner.nextLine();


                    if (InputValidator.validatePhoneNumber(telephone)) {
                        break;
                    } else {
                        System.out.println("Numéro de téléphone invalide. Il doit contenir exactement 10 chiffres.");
                    }
                }
                System.out.print("Est-ce un professionnel ? (true/false) : ");
                boolean estProfessionnel = scanner.nextBoolean();
                scanner.nextLine();

                client = new Client(nom, adresse, telephone, estProfessionnel);
                try {

                    validator.validateClient(client);
                    int clientId = clientService.addClient(client);
                    client.setId(clientId);
                    System.out.println("Client ajouté avec succès.");
                } catch (ValidationException e) {
                    System.out.println("Erreur de validation : " + e.getMessage());
                    return;
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Erreur lors de l'ajout du client.");
                    return;
                }
                break;

            default:
                System.out.println("Option invalide. Veuillez réessayer.");
                return;
        }

        if (client != null) {
            String nomProjet;
            double margeBeneficiaire;


            while (true) {
                System.out.print("Entrez le nom du projet : ");
                nomProjet = scanner.nextLine();

                if (InputValidator.validateName(nomProjet)) {
                    break;
                } else {
                    System.out.println("Nom de projet invalide. Veuillez entrer un nom valide.");
                }
            }


            while (true) {
                System.out.print("Entrez la marge bénéficiaire : ");
                if (scanner.hasNextDouble()) {
                    margeBeneficiaire = scanner.nextDouble();
                    if (margeBeneficiaire > 0) {
                        break;
                    } else {
                        System.out.println("La marge bénéficiaire doit être un nombre positif.");
                    }
                } else {
                    System.out.println("Veuillez entrer un nombre valide pour la marge bénéficiaire.");
                    scanner.next();
                }
            }


            Project projet = new Project(nomProjet, client.getId(), margeBeneficiaire, Project.EtatProjet.EN_COURS);
            projectService.creerNouveauProjet(projet);
            int idProjet = projet.getIdProjet();
            System.out.println("Projet créé avec succès avec ID: " + idProjet);

            double totalCost = 0.0;


            System.out.println("Souhaitez-vous ajouter des matériaux pour ce projet ?");
            System.out.println("1. Oui");
            System.out.println("2. Non");
            int choixMateriel = scanner.nextInt();
            scanner.nextLine();

            if (choixMateriel == 1) {
                boolean continuerAjout = true;
                while (continuerAjout) {
                    String nomMateriel;
                    double tauxTVA, coutUnitaire, quantite, coutTransport, coefficientQualite;
                    while (true) {
                        System.out.print("Entrez le nom du matériel : ");
                        nomMateriel = scanner.nextLine();

                        if (InputValidator.validateName(nomMateriel)) {
                            break;
                        } else {
                            System.out.println("Nom de matériel invalide. Veuillez entrer un nom valide.");
                        }
                    }


                    while (true) {
                        System.out.print("Entrez le taux de TVA : ");
                        if (scanner.hasNextDouble()) {
                            tauxTVA = scanner.nextDouble();
                            if (InputValidator.validateVAT(tauxTVA)) {
                                break;
                            } else {
                                System.out.println("Le taux de TVA doit être un nombre positif.");
                            }
                        } else {
                            System.out.println("Veuillez entrer un nombre valide pour le taux de TVA.");
                            scanner.next();
                        }
                    }

                    while (true) {
                        System.out.print("Entrez le coût unitaire : ");
                        if (scanner.hasNextDouble()) {
                            coutUnitaire = scanner.nextDouble();
                            if (InputValidator.validateUnitCost(coutUnitaire)) {
                                break;
                            } else {
                                System.out.println("Le coût unitaire doit être un nombre positif.");
                            }
                        } else {
                            System.out.println("Veuillez entrer un nombre valide pour le coût unitaire.");
                            scanner.next();
                        }
                    }


                    while (true) {
                        System.out.print("Entrez la quantité : ");
                        if (scanner.hasNextDouble()) {
                            quantite = scanner.nextDouble();
                            if (InputValidator.validateQuantity(quantite)) {
                                break;
                            } else {
                                System.out.println("La quantité doit être un nombre positif.");
                            }
                        } else {
                            System.out.println("Veuillez entrer un nombre valide pour la quantité.");
                            scanner.next();
                        }
                    }

                    while (true) {
                        System.out.print("Entrez le coût de transport : ");
                        if (scanner.hasNextDouble()) {
                            coutTransport = scanner.nextDouble();
                            if (InputValidator.validateTransportCost(coutTransport)) {
                                break;
                            } else {
                                System.out.println("Le coût de transport doit être un nombre positif.");
                            }
                        } else {
                            System.out.println("Veuillez entrer un nombre valide pour le coût de transport.");
                            scanner.next();
                        }
                    }


                    while (true) {
                        System.out.print("Entrez le coefficient de qualité : ");
                        if (scanner.hasNextDouble()) {
                            coefficientQualite = scanner.nextDouble();
                            if (InputValidator.validateQualityCoefficient(coefficientQualite)) {
                                break;
                            } else {
                                System.out.println("Le coefficient de qualité doit être un nombre positif.");
                            }
                        } else {
                            System.out.println("Veuillez entrer un nombre valide pour le coefficient de qualité.");
                            scanner.next();
                        }
                    }

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


            System.out.println("Souhaitez-vous ajouter de la main d'œuvre pour ce projet ?");
            System.out.println("1. Oui");
            System.out.println("2. Non");
            int choixMainOeuvre = scanner.nextInt();
            scanner.nextLine();

            if (choixMainOeuvre == 1) {
                boolean continuerAjoutMainOeuvre = true;
                String nomTache;
                double tauxHoraire, heuresTravail, coutDeplacement, coefficientQualite;
                while (continuerAjoutMainOeuvre) {
                    while (true) {
                        System.out.print("Entrez le nom de la tâche : ");
                        nomTache = scanner.nextLine();

                        if (InputValidator.validateTaskName(nomTache)) {
                            break;
                        } else {
                            System.out.println("Nom de tâche invalide. Veuillez entrer un nom valide.");
                        }
                    }

                    while (true) {
                        System.out.print("Entrez le taux horaire : ");
                        if (scanner.hasNextDouble()) {
                            tauxHoraire = scanner.nextDouble();
                            if (InputValidator.validateHourlyRate(tauxHoraire)) {
                                break;
                            } else {
                                System.out.println("Le taux horaire doit être un nombre positif.");
                            }
                        } else {
                            System.out.println("Veuillez entrer un nombre valide pour le taux horaire.");
                            scanner.next();
                        }
                    }


                    while (true) {
                        System.out.print("Entrez le nombre d'heures de travail : ");
                        if (scanner.hasNextDouble()) {
                            heuresTravail = scanner.nextDouble();
                            if (InputValidator.validateWorkHours(heuresTravail)) {
                                break;
                            } else {
                                System.out.println("Le nombre d'heures de travail doit être un nombre positif.");
                            }
                        } else {
                            System.out.println("Veuillez entrer un nombre valide pour les heures de travail.");
                            scanner.next();
                        }
                    }


                    while (true) {
                        System.out.print("Entrez le coût de déplacement : ");
                        if (scanner.hasNextDouble()) {
                            coutDeplacement = scanner.nextDouble();
                            if (InputValidator.validateTravelCost(coutDeplacement)) {
                                break;
                            } else {
                                System.out.println("Le coût de déplacement doit être un nombre positif.");
                            }
                        } else {
                            System.out.println("Veuillez entrer un nombre valide pour le coût de déplacement.");
                            scanner.next();
                        }
                    }


                    while (true) {
                        System.out.print("Entrez le coefficient de qualité : ");
                        if (scanner.hasNextDouble()) {
                            coefficientQualite = scanner.nextDouble();
                            if (InputValidator.validateQualityCoefficient(coefficientQualite)) {
                                break;
                            } else {
                                System.out.println("Le coefficient de qualité doit être un nombre positif.");
                            }
                        } else {
                            System.out.println("Veuillez entrer un nombre valide pour le coefficient de qualité.");
                            scanner.next();
                        }
                    }

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




            System.out.println("--- Enregistrement du Devis ---");
            System.out.println("Voici un résumé du devis :");


            System.out.println("Client : ");
            System.out.println("Nom : " + client.getNom());
            System.out.println("Adresse : " + client.getAdresse());
            System.out.println("Téléphone : " + client.getTelephone());
            System.out.println("Client professionnel : " + (client.isEst_professionnel() ? "Oui" : "Non"));


            System.out.println("Coût total du projet (sans marge bénéficiaire) : " + totalCost);
            System.out.println("Coût total du projet (avec marge bénéficiaire) : " + projet.calculerCoutTotalAvecMarge());
            System.out.println("Coût total du projet (avec remise) : " + coutTotalAvecRemise);


            System.out.print("Entrez la date d'émission du devis (format : jj/mm/aaaa) : ");
            String dateEmissionStr = scanner.nextLine().trim();
            LocalDate dateEmission = LocalDate.parse(dateEmissionStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            System.out.print("Entrez la date de validité du devis (format : jj/mm/aaaa) : ");
            String dateValiditeStr = scanner.nextLine().trim();
            LocalDate dateValidite = LocalDate.parse(dateValiditeStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));


            System.out.println("\nRésumé du devis :");
            System.out.println("Client : ");
            System.out.println("Nom : " + client.getNom());
            System.out.println("Adresse : " + client.getAdresse());
            System.out.println("Téléphone : " + client.getTelephone());
            System.out.println("Client professionnel : " + (client.isEst_professionnel() ? "Oui" : "Non"));


            System.out.println("Coût total du projet (sans marge bénéficiaire) : " + totalCost);
            System.out.println("Coût total du projet (avec marge bénéficiaire) : " + projet.calculerCoutTotalAvecMarge());
            System.out.println("Coût total du projet (avec remise) : " + coutTotalAvecRemise);

            System.out.println("Nom du projet : " + projet.getNomProjet());
            System.out.println("Coût total avec remise : " + coutTotalAvecRemise);
            System.out.println("Date d'émission : " + dateEmission);
            System.out.println("Date de validité : " + dateValidite);

            System.out.print("Souhaitez-vous enregistrer le devis ? (y/n) : ");
            String confirmation = scanner.nextLine();

            boolean accepte = false;
            if (confirmation.equalsIgnoreCase("y")) {
                accepte = true;
                Devis devis = new Devis(dateEmission, dateValidite, projet.getIdProjet(), coutTotalAvecRemise, accepte);
                devisService.creerDevis(projet.getIdProjet(), coutTotalAvecRemise, dateEmission, dateValidite, accepte);
                System.out.println("Devis enregistré avec succès !");
            } else {
                projet.setEtatProjet(Project.EtatProjet.ANNULE);
                projectService.updateProjectState(projet.getIdProjet(), Project.EtatProjet.ANNULE);
                System.out.println("Enregistrement du devis annulé et projet marqué comme ANNULE.");
            }


            System.out.println("Projet créé avec succès avec les matériaux, la main d'œuvre, et le devis ajoutés.");
        } else {
            System.out.println("La création du projet a échoué. Aucun client sélectionné.");
        }
    }


    private static double calculerCoutTotalAvecRemise(double totalCost, Client client) {
        final double REMISE_PROFESSIONNEL = 0.10;
        double remise = 0;

        if (client.isEst_professionnel()) {
            remise = REMISE_PROFESSIONNEL;
        }

        return totalCost * (1 - remise);
    }



}
