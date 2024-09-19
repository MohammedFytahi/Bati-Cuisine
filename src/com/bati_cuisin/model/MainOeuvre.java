package com.bati_cuisin.model;

public class MainOeuvre extends Composant {
    private double tauxHoraire;
    private double heuresTravail;
    private double productiviteOuvrier;

    public MainOeuvre(String nom, double tauxTVA, double tauxHoraire, double heuresTravail, double productiviteOuvrier) {
        super(nom, tauxTVA);
        this.tauxHoraire = tauxHoraire;
        this.heuresTravail = heuresTravail;
        this.productiviteOuvrier = productiviteOuvrier;
    }

    // Getters et setters pour les attributs spécifiques
    public double getTauxHoraire() {
        return tauxHoraire;
    }

    public void setTauxHoraire(double tauxHoraire) {
        this.tauxHoraire = tauxHoraire;
    }

    public double getHeuresTravail() {
        return heuresTravail;
    }

    public void setHeuresTravail(double heuresTravail) {
        this.heuresTravail = heuresTravail;
    }

    public double getProductiviteOuvrier() {
        return productiviteOuvrier;
    }

    public void setProductiviteOuvrier(double productiviteOuvrier) {
        this.productiviteOuvrier = productiviteOuvrier;
    }
}

