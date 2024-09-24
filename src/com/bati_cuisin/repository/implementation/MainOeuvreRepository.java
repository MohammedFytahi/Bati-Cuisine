package com.bati_cuisin.repository.implementation;

import com.bati_cuisin.database.DatabaseConnection;
import com.bati_cuisin.model.MainOeuvre;
import com.bati_cuisin.repository.interfaces.MainOeuvreRepositoryInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            stmt.setInt(6, mainOeuvre.getId_projet());

            stmt.executeUpdate();
            System.out.println("Main d'œuvre ajoutée avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<List<MainOeuvre>> findMainOeuvreByProjectId(int idProjet) {
        List<MainOeuvre> mainOeuvres = new ArrayList<>();
        String sql = "SELECT * FROM main_oeuvre WHERE id_projet = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProjet);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                MainOeuvre mainOeuvre = new MainOeuvre(
                        rs.getString("nom"),
                        rs.getDouble("taux_tva"),
                        rs.getDouble("taux_horaire"),
                        rs.getDouble("heures_travail"),
                        rs.getDouble("productivite_ouvrier"),
                        rs.getInt("id_projet")
                );
                mainOeuvres.add(mainOeuvre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mainOeuvres.isEmpty() ? Optional.empty() : Optional.of(mainOeuvres);
    }


}
