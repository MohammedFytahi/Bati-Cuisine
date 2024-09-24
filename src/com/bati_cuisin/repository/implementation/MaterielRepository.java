package com.bati_cuisin.repository.implementation;

import com.bati_cuisin.database.DatabaseConnection;
import com.bati_cuisin.model.Materiel;
import com.bati_cuisin.repository.interfaces.MaterielRepositoryInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    private List<Materiel> findMaterialsByProjectId(int idProjet) {
        List<Materiel> materiaux = new ArrayList<>();
        String sql = "SELECT * FROM materiel WHERE id_projet = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProjet);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Materiel materiel = new Materiel(
                        rs.getString("nom"),
                        rs.getDouble("taux_tva"),
                        rs.getDouble("cout_unitaire"),
                        rs.getDouble("quantite"),
                        rs.getDouble("cout_transport"),
                        rs.getDouble("coefficient_qualite"),
                        rs.getInt("id_projet")
                );
                materiaux.add(materiel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return materiaux;
    }



}
