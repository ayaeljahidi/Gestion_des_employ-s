import Controller.EmployeeController;
import DAO.EmployeeDAOimpl;
import Model.EmployeeModel;
import View.EmployeeView;

public class Main {
    public static void main(String[] args) {
        EmployeeDAOimpl empDAO = new EmployeeDAOimpl();
        EmployeeModel model = new EmployeeModel(empDAO);
        EmployeeView view = new EmployeeView(model);
        new EmployeeController(model, view);
    }
}
