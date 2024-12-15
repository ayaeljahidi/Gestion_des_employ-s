package Controller;

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
        // Définir une date de début et une date de fin (par exemple, du 1er janvier 2024 au 31 décembre 2024)
        LocalDate dateDebut = LocalDate.now();
        LocalDate dateFin = LocalDate.of(2025,2,20); 
        
        // Format de date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        // Liste des dates à ajouter
        List<String> dates = new ArrayList<>();
        LocalDate currentDate = dateDebut;
        while (!currentDate.isAfter(dateFin)) {
            dates.add(currentDate.format(formatter));  // Ajouter la date au format "yyyy-MM-dd"
            currentDate = currentDate.plusDays(1);  // Incrémenter d'un jour
        }
        
        HolidayView.remplirComboBox(view.DateDebut(), dates);
        HolidayView.remplirComboBox(view.DateFin(), dates);
    }

}
