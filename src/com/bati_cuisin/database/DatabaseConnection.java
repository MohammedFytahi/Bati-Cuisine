package com.bati_cuisin.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Instance unique de la connexion
    private static DatabaseConnection instance;
    private Connection connection;

    // Informations de connexion à la base de données
    private String url = "jdbc:postgresql://localhost:5432/Bati-Cuisine";
    private String username = "postgres";
    private String password = "2001";

    // Constructeur privé pour empêcher l'instanciation externe
    private DatabaseConnection() throws SQLException {
        try {
            this.connection = DriverManager.getConnection(url, username, password);
            if (this.connection != null) {
                System.out.println("Connexion à la base de données réussie.");
            }
        } catch (SQLException e) {
            System.out.println("Échec de la connexion à la base de données");
            e.printStackTrace();
            throw e;
        }
    }

    // Méthode publique pour obtenir l'instance unique
    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConnection();
        } else if (instance.getConnection().isClosed()) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    // Méthode pour obtenir la connexion
    public Connection getConnection() {
        return connection;
    }

//    // Méthode pour vérifier si la connexion est ouverte
//    public boolean isConnected() {
//        try {
//            return connection != null && !connection.isClosed();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
}
