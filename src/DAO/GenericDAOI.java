package DAO;

import java.util.List;


public interface GenericDAOI<T> {
    void add(T element);
    boolean deleteHolidayById(int holidayId);

    List<T> getAll();

}

// void dropEmploye(T elm);
//void updateEmploye(T elm);
//  List<T> getAllEmployes();
//List<String> getTypes();