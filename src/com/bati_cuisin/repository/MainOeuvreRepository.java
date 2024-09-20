package com.bati_cuisin.repository;

import com.bati_cuisin.database.DatabaseConnection;
import com.bati_cuisin.model.MainOeuvre;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MainOeuvreRepository implements MainOeuvreRepositoryInterface {
    private Connection connection;

    public MainOeuvreRepository() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ajouterMainOeuvre(MainOeuvre mainOeuvre) {
        String sql = "INSERT INTO main_oeuvre (nom, taux_tva, taux_horaire, heures_travail, productivite_ouvrier, id_projet) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, mainOeuvre.getNom());
            stmt.setDouble(2, mainOeuvre.getTauxTVA());
            stmt.setDouble(3, mainOeuvre.getTauxHoraire());
            stmt.setDouble(4, mainOeuvre.getHeuresTravail());
            stmt.setDouble(5, mainOeuvre.getProductiviteOuvrier());
            stmt.setInt(6, mainOeuvre.getId_projet()); // Ajout de l'ID du projet

            stmt.executeUpdate();
            System.out.println("Main d'œuvre ajoutée avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
