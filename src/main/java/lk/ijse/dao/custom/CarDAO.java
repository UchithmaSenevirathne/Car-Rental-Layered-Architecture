package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Car;
import lk.ijse.entity.CarOut;
import java.sql.SQLException;
import java.util.List;

public interface CarDAO extends CrudDAO<Car> {
    boolean updateAvailable(String id) throws SQLException;
    boolean updateAvailableYes(String bId) throws SQLException;
    List<CarOut> getCarOut() throws SQLException;
}
