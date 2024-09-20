package com.bati_cuisin.repository;

import com.bati_cuisin.database.DatabaseConnection;
import com.bati_cuisin.model.Materiel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MaterielRepository implements MaterielRepositoryInterface {

    private Connection connection;

    public MaterielRepository() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ajouterMateriel(Materiel materiel) {
        String sql = "INSERT INTO materiel (nom, taux_tva, cout_unitaire, quantite, cout_transport, coeff_qualite, id_projet) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, materiel.getNom());
            stmt.setDouble(2, materiel.getTauxTVA());
            stmt.setDouble(3, materiel.getCoutUnitaire());
            stmt.setDouble(4, materiel.getQuantite());
            stmt.setDouble(5, materiel.getCoutTransport());
            stmt.setDouble(6, materiel.getCoefficientQualite());
            stmt.setInt(7, materiel.getId_projet()); // Ajout de l'ID du projet
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
