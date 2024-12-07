package Controller;

import Model.Employee;
import Model.EmployeeModel;
import View.EmployeeView;

import java.util.List;

import DAO.EmployeeDAOimpl;

public class EmployeeController {
    private EmployeeModel model;
    private EmployeeView view;

    public EmployeeController(EmployeeModel model, EmployeeView view) {
        this.model = model;
        this.view = view;

        
        this.view.addAjouterListener(e -> addEmploye());
        this.view.addSupprimerListener(e->removeEmployee());
        this.view.addModifierListener(e ->updateEmploye());
        this.view.addAfficherListener(e -> afficherEmployes()); 

    }
    public void removeEmployee() {
        EmployeeDAOimpl dao = new EmployeeDAOimpl();
        dao.dropEmploye(null);
    }
    private void addEmploye() {
        try {
            String nom = view.getNom();
            String prenom = view.getPrenom();
            String email = view.getEmail();
            String telephone = view.getTelephone();
            String salaireStr = view.getSalaire();
            String poste = view.getPoste();
            String role = view.getRole();

            if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || telephone.isEmpty() || salaireStr.isEmpty() || poste == null || role == null) {
                view.showMessage("Veuillez remplir tous les champs.");
                return;
            }

            double salaire = Double.parseDouble(salaireStr);

            model.addEmploye(nom, prenom, email, telephone, salaire, poste, role);
            view.showMessage("Employé ajouté avec succès !");
            view.clearFields();

           
        } catch (Exception ex) {
            view.showMessage("Erreur : " + ex.getMessage());
        }
    }
    
    private void updateEmploye() {
        try {
            String nom = view.getNom();
            String prenom = view.getPrenom();
            String email = view.getEmail();
            String telephone = view.getTelephone();
            String salaireStr = view.getSalaire();
            String poste = view.getPoste();
            String role = view.getRole();
            double salaire = Double.parseDouble(salaireStr);
            

            if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || telephone.isEmpty() || salaireStr.isEmpty() || poste == null || role == null) {
                view.showMessage("Veuillez remplir tous les champs.");
                return;
            }


            Employee employe = new Employee(nom, prenom, email, telephone, salaire, poste, role);

            model.updateEmploye(employe);

            view.showMessage("Employé modifié avec succès !");
         // Réinitialise les champs de texte
            view.clearFields();

        } catch (Exception ex) {
            view.showMessage("Erreur : " + ex.getMessage());
        }
    }

    private void afficherEmployes() {
        try {
            List<Employee> employes = model.getAllEmployes();
            Object[][] data = new Object[employes.size()][8];
            
            for (int i = 0; i < employes.size(); i++) {
                Employee emp = employes.get(i);
                data[i][0] = emp.getId();
                data[i][1] = emp.getNom();
                data[i][2] = emp.getPrenom();
                data[i][3] = emp.getEmail();
                data[i][4] = emp.getTelephone();
                data[i][5] = emp.getSalaire();
                data[i][6] = emp.getPoste();
                data[i][7] = emp.getRole();
            }
            
            view.updateTable(data);
        } catch (Exception e) {
            view.showMessage("Erreur lors de l'affichage des employés : " + e.getMessage());
        }
    }
    
    
    	
    }

