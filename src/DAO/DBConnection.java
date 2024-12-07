package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/GestionEmployee";
    private static final String UTILISATEUR = "postgres";
    private static final String MOT_DE_PASSE = "aya@2004";
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erreur lors de la connexion à la base de données.");
            }
        }
        return connection;
    }
}
