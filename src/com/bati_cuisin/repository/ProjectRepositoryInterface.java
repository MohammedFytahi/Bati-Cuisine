package com.bati_cuisin.repository;


import com.bati_cuisin.model.Project;

import java.sql.SQLException;


public interface ProjectRepositoryInterface {
    void ajouterProjet(Project project);


    Project trouverProjetParId(int idProjet);
    void updateCoutTotal(int idProjet, double coutTotal) throws SQLException;
}
