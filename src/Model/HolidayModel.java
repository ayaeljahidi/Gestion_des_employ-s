package Model;

import DAO.HolidayDAOimpl;

import java.time.LocalDate;

public class HolidayModel {
    private HolidayDAOimpl dao;
    public HolidayModel(HolidayDAOimpl dao) {
        this.dao=dao;
    }

    public boolean addHoliday(LocalDate dateDebut, LocalDate dateFin, String type, String nom) {
     /*   if (view.getSolde() >dao.getSolde(nom)) {
            System.out.println("Erreur : Solde insuffisant");
            return false;
        }*/

        if (dateDebut.isAfter(dateFin)) {
            System.out.println("Erreur : La date de début est après la date de fin");
            return false;
        }

        Holiday holiday = new Holiday(dateDebut, dateFin, type,nom);
        dao.add(holiday);
        return true;
    }
    
  
}