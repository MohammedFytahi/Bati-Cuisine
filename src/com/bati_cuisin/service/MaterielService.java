package com.bati_cuisin.service;

import com.bati_cuisin.model.Materiel;
import com.bati_cuisin.repository.MaterielRepositoryInterface;

public class MaterielService {
    private MaterielRepositoryInterface materielRepository;

    public MaterielService(MaterielRepositoryInterface materielRepository) {
        this.materielRepository = materielRepository;
    }

    public void ajouterMateriel(String nom, double tauxTVA, double coutUnitaire, double quantite, double coutTransport, double coefficientQualite) {
        Materiel materiel = new Materiel(nom, tauxTVA, coutUnitaire, quantite, coutTransport, coefficientQualite);
        materielRepository.ajouterMateriel(materiel);
    }


}
