package DAO;
import java.util.List;
import Model.Employee;

public interface EmployeeDAOI {
    void addEmploye(Employee employee);
    void dropEmploye(Employee employee);
    void updateEmploye(Employee employee);
    List<Employee> getAllEmployes();
    

    List<String> getPostes();
    List<String> getRoles();
}
