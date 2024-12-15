package Controller;

import Model.Holiday;
import Model.HolidayModel;
import View.HolidayView;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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



}
