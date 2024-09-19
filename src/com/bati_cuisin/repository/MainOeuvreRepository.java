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
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void ajouterMainOeuvre(MainOeuvre mainOeuvre) {
        String sql = "INSERT INTO main_oeuvre (nom, taux_tva, taux_horaire, heures_travail, productivite_ouvrier) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, mainOeuvre.getNom());
            stmt.setDouble(2, mainOeuvre.getTauxTVA());
            stmt.setDouble(3, mainOeuvre.getTauxHoraire());
            stmt.setDouble(4, mainOeuvre.getHeuresTravail());
            stmt.setDouble(5, mainOeuvre.getProductiviteOuvrier());

            stmt.executeUpdate();
            System.out.println("Main d'œuvre ajoutée avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public List<MainOeuvre> getToutesLesMainOeuvre() {
//        List<MainOeuvre> mainOeuvres = new ArrayList<>();
//        String sql = "SELECT * FROM main_oeuvre";
//
//        try (PreparedStatement stmt = connection.prepareStatement(sql);
//             ResultSet rs = stmt.executeQuery()) {
//
//            while (rs.next()) {
//                MainOeuvre mainOeuvre = new MainOeuvre(
//                        rs.getString("nom"),
//                        rs.getDouble("taux_tva"),
//                        rs.getDouble("taux_horaire"),
//                        rs.getDouble("heures_travail"),
//                        rs.getDouble("productivite_ouvrier")
//                );
//                mainOeuvres.add(mainOeuvre);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return mainOeuvres;
//    }
//
//    @Override
//    public MainOeuvre trouverParId(int id) {
//        MainOeuvre mainOeuvre = null;
//        String sql = "SELECT * FROM main_oeuvre WHERE id = ?";
//
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setInt(1, id);
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    mainOeuvre = new MainOeuvre(
//                            rs.getString("nom"),
//                            rs.getDouble("taux_tva"),
//                            rs.getDouble("taux_horaire"),
//                            rs.getDouble("heures_travail"),
//                            rs.getDouble("productivite_ouvrier")
//                    );
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return mainOeuvre;
//    }
//
//    @Override
//    public void mettreAJourMainOeuvre(MainOeuvre mainOeuvre) {
//        String sql = "UPDATE main_oeuvre SET nom = ?, taux_tva = ?, taux_horaire = ?, heures_travail = ?, productivite_ouvrier = ? WHERE id = ?";
//
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setString(1, mainOeuvre.getNom());
//            stmt.setDouble(2, mainOeuvre.getTauxTVA());
//            stmt.setDouble(3, mainOeuvre.getTauxHoraire());
//            stmt.setDouble(4, mainOeuvre.getHeuresTravail());
//            stmt.setDouble(5, mainOeuvre.getProductiviteOuvrier());
//            stmt.setInt(6, mainOeuvre.getId());
//
//            stmt.executeUpdate();
//            System.out.println("Main d'œuvre mise à jour avec succès.");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void supprimerMainOeuvre(int id) {
//        String sql = "DELETE FROM main_oeuvre WHERE id = ?";
//
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setInt(1, id);
//            stmt.executeUpdate();
//            System.out.println("Main d'œuvre supprimée avec succès.");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
