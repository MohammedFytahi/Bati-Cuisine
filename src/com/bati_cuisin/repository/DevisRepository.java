package com.bati_cuisin.repository;

import com.bati_cuisin.database.DatabaseConnection;
import com.bati_cuisin.model.Devis;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DevisRepository implements DevisRepositoryInterface {

    private Connection connection;

    public DevisRepository() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insererDevis(int idProjet, double montantEstime, LocalDate dateEmission, LocalDate dateValidite, boolean accepte) {
        String sql = "INSERT INTO devis (id_projet, montant_estime, date_emission, date_validite, accepte) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProjet);
            stmt.setDouble(2, montantEstime);
            stmt.setDate(3, Date.valueOf(dateEmission));
            stmt.setDate(4, Date.valueOf(dateValidite));
            stmt.setBoolean(5, accepte);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Devis obtenirDevisParId(int idDevis) {
        String sql = "SELECT * FROM devis WHERE id_devis = ?";
        Devis devis = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idDevis);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                devis = mapResultSetToDevis(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return devis;
    }

    @Override
    public void mettreAJourStatutDevis(int idDevis, boolean accepte) {
        String sql = "UPDATE devis SET accepte = ? WHERE id_devis = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBoolean(1, accepte);
            stmt.setInt(2, idDevis);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimerDevis(int idDevis) {
        String sql = "DELETE FROM devis WHERE id_devis = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idDevis);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Devis> obtenirTousLesDevis() {
        String sql = "SELECT * FROM devis";
        List<Devis> devisList = new ArrayList<>();

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Devis devis = mapResultSetToDevis(rs);
                devisList.add(devis);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return devisList;
    }

    private Devis mapResultSetToDevis(ResultSet rs) throws SQLException {
        int idDevis = rs.getInt("id_devis");
        int idProjet = rs.getInt("id_projet");
        double montantEstime = rs.getDouble("montant_estime");
        LocalDate dateEmission = rs.getDate("date_emission").toLocalDate();
        LocalDate dateValidite = rs.getDate("date_validite").toLocalDate();
        boolean accepte = rs.getBoolean("accepte");

        return new Devis(idDevis, idProjet, montantEstime, dateEmission, dateValidite, accepte);
    }
}
