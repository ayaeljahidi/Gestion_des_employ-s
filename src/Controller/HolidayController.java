package Controller;

import Model.Holiday;
import Model.HolidayModel;
import View.HolidayView;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import java.time.format.DateTimeFormatter;

import DAO.HolidayDAOimpl;

public class HolidayController {
    private HolidayModel model;
    private HolidayView view;
    private HolidayDAOimpl dao;

    public HolidayController(HolidayModel model, HolidayView view) {
        this.model = model;
        this.view = view;
        this.dao = new HolidayDAOimpl();


        // Charger les données dans les ComboBox
        initComboBoxData();
        initDateComboBoxData();  // Charger les dates dans les ComboBox de dateDebut et dateFin
        afficherHolidays();

        
        // Ajouter un congé lorsqu'on clique sur le bouton "Ajouter"
        view.addAjouterListener(e -> addHoliday());
        view.addDropListener(e -> setupTableClickListener());
    }
    private void initComboBoxData() {
        // Charger les types de congés
        HolidayView.remplirComboBox(view.getTypeComboBox(), dao.getTypes());

        // Charger les noms des employés
        HolidayView.remplirCombo(view.getNomComboBox(), dao.getEmployesName());


    }


    
    private void addHoliday() {
        try {
            String nom = view.getNom();
            String type = view.getSelectedType();
            LocalDate dateDebut = LocalDate.parse(view.getDateDebut());
            LocalDate dateFin = LocalDate.parse(view.getDateFin());
            int solde = view.getSolde();

            if (nom == null || type == null || dateDebut == null || dateFin == null) {
                view.afficherMessageErreur("Veuillez remplir tous les champs !");
                return;
            }
            if (view.getSolde() >dao.getSolde(nom)) {
                view.afficherMessageErreur("Solde insuffisant,votre solde est:"+dao.getSolde(nom));

                return;
            }
            
            boolean success = model.addHoliday(dateDebut, dateFin, type, nom);
            if (success) {
            	
                view.afficherMessageSucces("Congé ajouté avec succès !");
                dao.updateSolde(nom,dao.getSolde(nom)-solde);
                view.clearFields();

            }
        } catch (Exception ex) {
            view.afficherMessageErreur("Erreur : " + ex.getMessage());
        }
    }
    
    private void initDateComboBoxData() {
        LocalDate dateDebut = LocalDate.now();
        LocalDate dateFin = LocalDate.of(2025,2,20); 
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        List<String> dates = new ArrayList<>();
        LocalDate currentDate = dateDebut;
        while (!currentDate.isAfter(dateFin)) {
            dates.add(currentDate.format(formatter)); 
            currentDate = currentDate.plusDays(1); 
        }
        
        HolidayView.remplirComboBox(view.DateDebut(), dates);
        HolidayView.remplirComboBox(view.DateFin(), dates);
    }
    
    
    private void afficherHolidays() {
        try {
            List<Holiday> holidays = dao.getAll();
            
            // Vider le tableau avant d'ajouter de nouvelles données
            view.tableModel.setRowCount(0); 

            for (Holiday emp : holidays) {
                Object[] rowData = new Object[5]; // Tableau pour une ligne
                rowData[0] = emp.getId();          // ID du congé (à ajouter dans votre classe Holiday)
                rowData[1] = emp.getNom();         // Nom de l'employé
                rowData[2] = emp.getDateDebut();   // Date de début du congé
                rowData[3] = emp.getDateFin();     // Date de fin du congé
                rowData[4] = emp.getType();        // Type du congé
                
                view.tableModel.addRow(rowData);        // Ajouter la ligne au modèle de la table
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupTableClickListener() {
        // On utilise view.getTable() pour obtenir la référence à JTable
        view.getJT().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = view.getJT().getSelectedRow(); // Récupérer l'index de la ligne sélectionnée
                
                if (row != -1) { // Vérifier que la ligne n'est pas vide
                    // Récupérer l'ID du congé (colonne 0)
                    int holidayId = (int) view.tableModel.getValueAt(row, 0); 
                    String employeeName = (String) view.tableModel.getValueAt(row, 1); // Récupérer le nom de l'employé

                    // Obtenir la durée du congé (en jours) pour ajuster le solde
                    LocalDate dateDebut = (LocalDate) view.tableModel.getValueAt(row, 2);
                    LocalDate dateFin = (LocalDate) view.tableModel.getValueAt(row, 3);
                    long daysOff = java.time.temporal.ChronoUnit.DAYS.between(dateDebut, dateFin);

                    int response = JOptionPane.showConfirmDialog(null, 
                            "Êtes-vous sûr de vouloir supprimer ce congé ?", 
                            "Confirmation", 
                            JOptionPane.YES_NO_OPTION);
                    
                    if (response == JOptionPane.YES_OPTION) {
                        // Appeler la méthode pour supprimer le congé dans la base de données
                        boolean success = dao.deleteHolidayById(holidayId);
                        
                        if (success) {
                            // Mettre à jour le solde de l'employé (ajouter les jours de congé annulés)
                            int currentSolde = dao.getSolde(employeeName);
                            dao.updateSolde(employeeName, currentSolde + (int) daysOff);

                            // Supprimer la ligne du modèle de table
                            view.tableModel.removeRow(row);
                            view.afficherMessageSucces("Congé supprimé avec succès.");
                        } else {
                            view.afficherMessageErreur("Erreur lors de la suppression du congé.");
                        }
                    }
                }
            }
        });
    }


}
