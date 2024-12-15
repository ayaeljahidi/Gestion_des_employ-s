import Controller.HolidayController;
import DAO.HolidayDAOimpl;
import Model.HolidayModel;
import View.HolidayView;

public class MainHoliday {
    public static void main(String[] args) {
        HolidayView view = new HolidayView();
        HolidayDAOimpl empDAO = new HolidayDAOimpl();
        HolidayModel model = new HolidayModel(empDAO);
        new HolidayController(model, view);
    }
}
