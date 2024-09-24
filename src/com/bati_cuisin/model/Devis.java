package com.bati_cuisin.model;

import java.time.LocalDate;

public class Devis {
    private int idDevis;
    private int idProjet;
    private double montantEstime;
    private LocalDate dateEmission;
    private LocalDate dateValidite;
    private boolean accepte;

    public Devis(int idDevis, int idProjet, double montantEstime, LocalDate dateEmission, LocalDate dateValidite, boolean accepte) {
        this.idDevis = idDevis;
        this.idProjet = idProjet;
        this.montantEstime = montantEstime;
        this.dateEmission = dateEmission;
        this.dateValidite = dateValidite;
        this.accepte = accepte;
    }

    public Devis(LocalDate dateEmission, LocalDate dateValidite, int idProjet, double totalCost, boolean accepte) {
    }


    public int getIdDevis() {
        return idDevis;
    }

    public void setIdDevis(int idDevis) {
        this.idDevis = idDevis;
    }

    public int getIdProjet() {
        return idProjet;
    }

    public void setIdProjet(int idProjet) {
        this.idProjet = idProjet;
    }

    public double getMontantEstime() {
        return montantEstime;
    }

    public void setMontantEstime(double montantEstime) {
        this.montantEstime = montantEstime;
    }

    public LocalDate getDateEmission() {
        return dateEmission;
    }

    public void setDateEmission(LocalDate dateEmission) {
        this.dateEmission = dateEmission;
    }

    public LocalDate getDateValidite() {
        return dateValidite;
    }

    public void setDateValidite(LocalDate dateValidite) {
        this.dateValidite = dateValidite;
    }

    public boolean isAccepte() {
        return accepte;
    }

    public void setAccepte(boolean accepte) {
        this.accepte = accepte;
    }
}
