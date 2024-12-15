package DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Model.Employee;

public class EmployeeDAOimpl implements EmployeeDAOI {
    private Connection connection;

    public EmployeeDAOimpl() {
        this.connection = DBConnection.getConnection();
    }

    @Override
    public void addEmploye(Employee employe) {
    	String query = "INSERT INTO Employe (nom, prenom, email, telephone, salaire, poste, role) VALUES (?, ?, ?, ?, ?, ?::poste_enum, ?::role_enum)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, employe.getNom());
            stmt.setString(2, employe.getPrenom());
            stmt.setString(3, employe.getEmail());
            stmt.setString(4, employe.getTelephone());
            stmt.setDouble(5, employe.getSalaire());
            stmt.setString(6, employe.getPoste());
            stmt.setString(7, employe.getRole());
            stmt.executeUpdate();
     
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }    
    
    @Override
    public void dropEmploye(Employee employe) {
        int id_delete = Integer.parseInt(JOptionPane.showInputDialog("Entrer l'id"));
        String query = "DELETE FROM Employe WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id_delete);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void updateEmploye(Employee employe) {
        int id_update = Integer.parseInt(JOptionPane.showInputDialog("Entrer l'id de l'employé à mettre à jour :"));
        String query = "UPDATE Employe SET nom = ?, prenom = ?, email = ?, telephone = ?, salaire = ?, poste = ?::poste_enum, role = ?::role_enum WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, employe.getNom());
            stmt.setString(2, employe.getPrenom());
            stmt.setString(3, employe.getEmail());
            stmt.setString(4, employe.getTelephone());
            stmt.setDouble(5, employe.getSalaire());
            stmt.setString(6, employe.getPoste());
            stmt.setString(7, employe.getRole());
            stmt.setInt(8, id_update);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public List<Employee> getAllEmployes() {
        List<Employee> employes = new ArrayList<>();
        String query = "SELECT id, nom, prenom, email, telephone, salaire, poste, role FROM Employe";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Employee employe = new Employee();
                employe.setId(rs.getInt("id"));
                employe.setNom(rs.getString("nom"));
                employe.setPrenom(rs.getString("prenom"));
                employe.setEmail(rs.getString("email"));
                employe.setTelephone(rs.getString("telephone"));
                employe.setSalaire(rs.getDouble("salaire"));
                employe.setPoste(rs.getString("poste"));
                employe.setRole(rs.getString("role"));

                employes.add(employe);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employes;
    }



    @Override
    public List<String> getPostes() {
        return fetchEnumValues("poste_enum");
    }

    @Override
    public List<String> getRoles() {
        return fetchEnumValues("role_enum");
    }

    private List<String> fetchEnumValues(String enumName) {
        List<String> values = new ArrayList<>();
        String query = "SELECT unnest(enum_range(null::" + enumName + "))";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                values.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return values;
    }
   
}
