package com.bati_cuisin.repository.implementation;

import com.bati_cuisin.database.DatabaseConnection;
import com.bati_cuisin.model.Devis;
import com.bati_cuisin.repository.interfaces.DevisRepositoryInterface;

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








}
