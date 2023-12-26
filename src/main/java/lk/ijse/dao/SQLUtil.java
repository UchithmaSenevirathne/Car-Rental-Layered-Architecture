package lk.ijse.dao;

import lk.ijse.db.DbConnection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLUtil {
    public static <T> T execute(String query, Object...args) throws SQLException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(query);

        for (int i = 0; i < args.length; i++) {
            pstm.setObject((i+1), args[i]);
        }

        if(query.toUpperCase().startsWith("SELECT")){
            return (T)pstm.executeQuery();
        }
        return (T)(Boolean)(pstm.executeUpdate() > 0);
    }
}
