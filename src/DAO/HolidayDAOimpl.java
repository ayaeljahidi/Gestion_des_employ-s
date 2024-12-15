package DAO;

import Model.Employee;
import Model.Holiday;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class HolidayDAOimpl implements GenericDAOI<Holiday> {
    private Connection connection;

    public HolidayDAOimpl() {
        this.connection = DBConnection.getConnection();
    }

    @Override
    public void add(Holiday holiday) {
        String query = "INSERT INTO holiday (date_debut, date_fin, type, nom) VALUES (?, ?, ?::holy_type, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDate(1, Date.valueOf(holiday.getDateDebut()));
            stmt.setDate(2, Date.valueOf(holiday.getDateFin()));
            stmt.setString(3, holiday.getType());
            stmt.setString(4, holiday.getNom());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Insertion réussie !");
            } else {
                System.out.println("Aucune ligne insérée.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<String> getTypes() {
        List<String> types = new ArrayList<>();
        String query = "SELECT unnest(enum_range(NULL::holy_type))";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                types.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return types;
    }
    
    
    public List<Employee> getEmployesName() {
        List<Employee> employes = new ArrayList<>();  
        String query = "SELECT nom FROM Employe";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Employee employe = new Employee();
                employe.setNom(rs.getString("nom"));
                employes.add(employe);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employes;
    }
    
    
    public int getSolde(String nom) {
        String query = "SELECT solde FROM Employe WHERE nom = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nom);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("solde");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Retourne -1 si une erreur survient ou si l'employé n'existe pas
    }

    public void updateSolde(String nom, int nouveauSolde) {
        String query = "UPDATE Employe SET solde = ? WHERE nom = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, nouveauSolde);
            stmt.setString(2, nom);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    
    @Override
    public List<Holiday> getAll() {
        List<Holiday> holidays = new ArrayList<>();
        String query = "SELECT idHoliday, nom, type, date_debut, date_fin FROM Holiday"; 

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Holiday holi = new Holiday();
                holi.setId(rs.getInt("idHoliday"));
                holi.setNom(rs.getString("nom")); 
                holi.setType(rs.getString("type"));
                
                java.sql.Date dateDebut = rs.getDate("date_debut");
                java.sql.Date dateFin = rs.getDate("date_fin");
                
                holi.setDateDebut(dateDebut.toLocalDate());
                holi.setDateFin(dateFin.toLocalDate());
                
                holidays.add(holi);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return holidays;
    }
    @Override 
    public boolean deleteHolidayById(int holidayId) {
        String query = "DELETE FROM Holiday WHERE idHoliday = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, holidayId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Retourner true si la suppression a réussi
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
}