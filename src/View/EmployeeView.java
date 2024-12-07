package View;

import Model.EmployeeModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class EmployeeView extends JFrame {
    private JComboBox<String> poste;
    private JComboBox<String> role;
    private JTextField nom, prenom, email, telephone, salaire;
    private JButton ajouter, supprimer, afficher, modifier;
    private JTable JT;
    private DefaultTableModel tableModel;

    public EmployeeView(EmployeeModel model) {
        setTitle("Gestion des Employés");
        setSize(800, 600);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(7, 2, 10, 10));

        panelForm.add(new JLabel("Nom:"));
        nom = new JTextField(20);
        panelForm.add(nom);

        panelForm.add(new JLabel("Prénom:"));
        prenom = new JTextField(20);
        panelForm.add(prenom);

        panelForm.add(new JLabel("Email:"));
        email = new JTextField(20);
        panelForm.add(email);

        panelForm.add(new JLabel("Téléphone:"));
        telephone = new JTextField(20);
        panelForm.add(telephone);

        panelForm.add(new JLabel("Salaire:"));
        salaire = new JTextField(20);
        panelForm.add(salaire);

        panelForm.add(new JLabel("Poste:"));
        poste = new JComboBox<>();
        remplirComboBox(poste, model.getAllPostes());
        panelForm.add(poste);

        panelForm.add(new JLabel("Rôle:"));
        role = new JComboBox<>();
        remplirComboBox(role, model.getAllRoles());
        panelForm.add(role);

        add(panelForm, BorderLayout.NORTH);

        // Boutons d'action
        JPanel panelBoutons = new JPanel(new FlowLayout());

        ajouter = new JButton("Ajouter");
        panelBoutons.add(ajouter);

        supprimer = new JButton("Supprimer");
        panelBoutons.add(supprimer);

        afficher = new JButton("Afficher");
        panelBoutons.add(afficher);

        modifier = new JButton("Modifier");
        panelBoutons.add(modifier);

        add(panelBoutons, BorderLayout.SOUTH);

        // Table des employés
        tableModel = new DefaultTableModel(new String[]{"ID", "Nom", "Prénom", "Email", "Téléphone", "Salaire", "Poste", "Rôle"}, 0);
        JT = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(JT);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    // Remplir les ComboBox avec des données dynamiques
    public static void remplirComboBox(JComboBox<String> comboBox, List<String> items) {
        comboBox.removeAllItems();
        for (String item : items) {
            comboBox.addItem(item);
        }
    }

    // Méthodes d'accès aux composants
    public String getNom() {
        return nom.getText();
    }

    public String getPrenom() {
        return prenom.getText();
    }

    public String getEmail() {
        return email.getText();
    }

    public String getTelephone() {
        return telephone.getText();
    }

    public String getSalaire() {
        return salaire.getText();
    }

    public String getPoste() {
        return (String) poste.getSelectedItem();
    }

    public String getRole() {
        return (String) role.getSelectedItem();
    }

    public void addAjouterListener(ActionListener listener) {
        ajouter.addActionListener(listener);

    }

    public void setNom(JTextField nom) {
		this.nom = nom;
	}

	public void setPrenom(JTextField prenom) {
		this.prenom = prenom;
	}

	public void setTelephone(JTextField telephone) {
		this.telephone = telephone;
	}

	public void setSalaire(JTextField salaire) {
		this.salaire = salaire;
	}

	public void addSupprimerListener(ActionListener listener) {
        supprimer.addActionListener(listener);
       
    }

    public void addAfficherListener(ActionListener listener) {
        afficher.addActionListener(listener);
      
        
    }

    public void addModifierListener(ActionListener listener) {
        modifier.addActionListener(listener);
    }
    

    public void updateTable(Object[][] data) {
        tableModel.setRowCount(0); 
        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }
    
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    
    public JTable getTable() {
        return JT;
    }
    
    public void clearFields() {
        nom.setText("");
        prenom.setText("");
        email.setText("");
        telephone.setText("");
        salaire.setText("");
        poste.setSelectedIndex(-1); // Réinitialise la sélection de la ComboBox
        role.setSelectedIndex(-1);
    }

  
    }
