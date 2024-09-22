package com.bati_cuisin.repository;



import com.bati_cuisin.database.DatabaseConnection;
import com.bati_cuisin.model.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectRepository implements ProjectRepositoryInterface {
    private Connection connection;

    public ProjectRepository() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void ajouterProjet(Project project) {
        String sql = "INSERT INTO projet (nom_projet, id_client, marge_beneficiaire, cout_total, etat_projet, date_creation) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, project.getNomProjet());
            stmt.setInt(2, project.getIdClient());
            stmt.setDouble(3, project.getMargeBeneficiaire());
            stmt.setDouble(4, project.getCoutTotal()); // Assurez-vous que ce champ est initialisé
            stmt.setString(5, project.getEtatProjet().name());
            stmt.setObject(6, project.getDateCreation());
            stmt.executeUpdate();

            // Récupérer l'ID généré
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    project.setIdProjet(generatedKeys.getInt(1)); // Mettre à jour l'ID du projet
                } else {
                    throw new SQLException("Échec de l'obtention de l'ID du projet.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    @Override
    public Project trouverProjetParId(int idProjet) {
        String sql = "SELECT * FROM projet WHERE id_projet = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProjet);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Project project = new Project(
                        rs.getString("nom_projet"),
                        rs.getInt("id_client"),
                        rs.getDouble("marge_beneficiaire"),
                        Project.EtatProjet.valueOf(rs.getString("etat_projet"))
                );
                project.setIdProjet(rs.getInt("id_projet"));
                project.setCoutTotal(rs.getDouble("cout_total"));
                project.setDateCreation(rs.getTimestamp("date_creation").toLocalDateTime());
                return project;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void updateCoutTotal(int idProjet, double coutTotal) throws SQLException {
        String sql = "UPDATE projet SET cout_total = ? WHERE id_projet = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDouble(1, coutTotal);
            pstmt.setInt(2, idProjet);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

}
