package lk.ijse.dao;

import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T> {
    boolean save(T dto) throws SQLException;

    List<T> getAll() throws SQLException;

    T search(String id) throws SQLException;

    boolean update(T dto) throws SQLException;

    boolean delete(String id) throws SQLException;

    String generateNextId() throws SQLException;
}
