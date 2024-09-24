package com.bati_cuisin.util;

import com.bati_cuisin.model.Client;
import com.bati_cuisin.model.Project;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InputValidator {


    public static boolean validateName(String nom) {
        return nom != null && nom.matches("[a-zA-Z\\s]+");
    }

    public static boolean validateAddress(String adresse) {
        return adresse != null && !adresse.trim().isEmpty();
    }

    public static boolean validateVAT(double vat) {
        return vat >= 0;
    }


    public static boolean validateUnitCost(double unitCost) {
        return unitCost >= 0;
    }


    public static boolean validateQuantity(double quantity) {
        return quantity >= 0;
    }


    public static boolean validateTransportCost(double transportCost) {
        return transportCost >= 0;
    }


    public static boolean validateQualityCoefficient(double qualityCoefficient) {
        return qualityCoefficient >= 0;
    }



    public static boolean validatePhoneNumber(String telephone) {
        return telephone != null && telephone.matches("^\\d{10}$");
    }
    public void validateClient(Client client) throws ValidationException {

        if (client.getNom() == null || client.getNom().trim().isEmpty()) {
            throw new ValidationException("Le nom du client est requis.");
        }
        if (client.getAdresse() == null || client.getAdresse().trim().isEmpty()) {
            throw new ValidationException("L'adresse du client est requise.");
        }
        if (client.getTelephone() == null || !client.getTelephone().matches("^\\d{10}$")) {
            throw new ValidationException("Le numéro de téléphone du client est invalide. Il doit contenir exactement 10 chiffres.");
        }
    }

    public static boolean validateTaskName(String taskName) {
        return taskName != null && taskName.matches("[a-zA-Z\\s]+");
    }


    public static boolean validateHourlyRate(double hourlyRate) {
        return hourlyRate >= 0; // Hourly rate must be non-negative
    }

    // Validate work hours
    public static boolean validateWorkHours(double workHours) {
        return workHours >= 0; // Work hours must be non-negative
    }

    // Validate travel cost
    public static boolean validateTravelCost(double travelCost) {
        return travelCost >= 0; // Travel cost must be non-negative
    }




    public void validateProject(Project project) throws ValidationException {
        if (project.getNomProjet() == null || project.getNomProjet().trim().isEmpty()) {
            throw new ValidationException("Le nom du projet est requis.");
        }
        if (project.getMargeBeneficiaire() <= 0 || project.getMargeBeneficiaire() > 100) {
            throw new ValidationException("La marge bénéficiaire doit être un pourcentage entre 0 et 100.");
        }
    }


    public void validateMateriel(String nomMateriel, double tauxTVA, double coutUnitaire, double quantite) throws ValidationException {
        if (nomMateriel == null || nomMateriel.trim().isEmpty()) {
            throw new ValidationException("Le nom du matériel est requis.");
        }
        if (tauxTVA < 0 || tauxTVA > 100) {
            throw new ValidationException("Le taux de TVA doit être un pourcentage entre 0 et 100.");
        }
        if (coutUnitaire <= 0) {
            throw new ValidationException("Le coût unitaire doit être supérieur à 0.");
        }
        if (quantite <= 0) {
            throw new ValidationException("La quantité doit être supérieure à 0.");
        }
    }


    public void validateMainOeuvre(String nomTache, double tauxHoraire, double heuresTravail) throws ValidationException {
        if (nomTache == null || nomTache.trim().isEmpty()) {
            throw new ValidationException("Le nom de la tâche est requis.");
        }
        if (tauxHoraire <= 0) {
            throw new ValidationException("Le taux horaire doit être supérieur à 0.");
        }
        if (heuresTravail <= 0) {
            throw new ValidationException("Le nombre d'heures de travail doit être supérieur à 0.");
        }
    }


    public void validateDevisDates(String dateEmissionStr, String dateValiditeStr) throws ValidationException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate dateEmission = LocalDate.parse(dateEmissionStr, formatter);
            LocalDate dateValidite = LocalDate.parse(dateValiditeStr, formatter);

            if (dateEmission.isAfter(dateValidite)) {
                throw new ValidationException("La date d'émission ne peut pas être postérieure à la date de validité.");
            }
        } catch (DateTimeParseException e) {
            throw new ValidationException("Les dates doivent être au format AAAA-MM-JJ.");
        }
    }
}
