package Model;

import java.time.LocalDate;

public class Holiday {
    private int id;
    private String nom;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String type;

    public Holiday(LocalDate dateDebut, LocalDate dateFin, String type,String nom) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.type = type;
        this.nom=nom;
    }

    public Holiday() {}

	// Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
   
    public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
  
}
