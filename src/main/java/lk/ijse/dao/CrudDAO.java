package lk.ijse.dao;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.CustomerDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CrudDAO<T> {
    public boolean save(T dto) throws SQLException;

    public List<T> getAll() throws SQLException;

    public T search(String id) throws SQLException;

    public boolean update(T dto) throws SQLException;

    public boolean delete(String id) throws SQLException;

    public String generateNextId() throws SQLException;
}
