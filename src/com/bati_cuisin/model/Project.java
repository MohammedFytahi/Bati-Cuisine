package com.bati_cuisin.model;

import java.time.LocalDateTime;

public class Project {
    private int idProjet;
    private String nomProjet;
    private int idClient;
    private double margeBeneficiaire;
    private double coutTotal;
    private EtatProjet etatProjet;
    private LocalDateTime dateCreation;



    public enum EtatProjet {
        EN_COURS,
        TERMINE,
        ANNULE
    }


    public Project(String nomProjet, int idClient, double margeBeneficiaire, EtatProjet etatProjet) {
        this.nomProjet = nomProjet;
        this.idClient = idClient;
        this.margeBeneficiaire = margeBeneficiaire;
        this.etatProjet = etatProjet != null ? etatProjet : EtatProjet.EN_COURS;
        this.dateCreation = LocalDateTime.now();
        this.coutTotal = 0.0;
    }


    public int getIdProjet() {
        return idProjet;
    }

    public void setIdProjet(int idProjet) {
        this.idProjet = idProjet;
    }

    public String getNomProjet() {
        return nomProjet;
    }

    public void setNomProjet(String nomProjet) {
        this.nomProjet = nomProjet;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public double getMargeBeneficiaire() {
        return margeBeneficiaire;
    }

    public void setMargeBeneficiaire(double margeBeneficiaire) {
        this.margeBeneficiaire = margeBeneficiaire;
    }

    public double getCoutTotal() {
        return coutTotal;
    }

    public void setCoutTotal(double coutTotal) {
        this.coutTotal = coutTotal;
    }

    public EtatProjet getEtatProjet() {
        return etatProjet;
    }

    public void setEtatProjet(EtatProjet etatProjet) {
        this.etatProjet = etatProjet;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }


    public void ajouterCout(double cout) {
        this.coutTotal += cout;
    }


    public double calculerCoutTotalAvecMarge() {
        return this.coutTotal + (this.coutTotal * this.margeBeneficiaire / 100);
    }


    @Override
    public String toString() {
        return "Project{" +
                "idProjet=" + idProjet +
                ", nomProjet='" + nomProjet + '\'' +
                ", idClient=" + idClient +
                ", margeBeneficiaire=" + margeBeneficiaire +
                ", coutTotal=" + coutTotal +
                ", etatProjet=" + etatProjet +
                ", dateCreation=" + dateCreation +
                '}';
    }
}
