package Model;
import DAO.EmployeeDAOI;
import java.util.List;

public class EmployeeModel {
    private EmployeeDAOI employeeDAOI;

    public EmployeeModel(EmployeeDAOI employeeDAOI) {
        this.employeeDAOI = employeeDAOI;
    }

    public void addEmploye(String nom, String prenom, String email, String telephone, double salaire, String poste, String role) {
        Employee employee = new Employee(nom, prenom, email, telephone, salaire, poste, role);
        employeeDAOI.addEmploye(employee);
    }
    

    public List<Employee> getAllEmployes() {
        return employeeDAOI.getAllEmployes();
    }


    public void updateEmploye(Employee employe) {
        employeeDAOI.updateEmploye(employe);
    }

    public List<String> getAllPostes() {
        return employeeDAOI.getPostes();
    }

    public List<String> getAllRoles() {
        return employeeDAOI.getRoles(); 
    }
}
