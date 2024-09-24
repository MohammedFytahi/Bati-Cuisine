package com.bati_cuisin.repository.interfaces;

import com.bati_cuisin.model.Materiel;

import java.sql.SQLException;
import java.util.List;

public interface MaterielRepositoryInterface {
    void ajouterMateriel(Materiel materiel);
    List<Materiel> findMaterialsByProjectId(int idProjet);
}
