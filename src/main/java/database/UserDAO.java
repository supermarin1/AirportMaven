package database;

import service.useraccess.permission.PermissionAction;
import model.user.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static database.QueryConstants.*;

public class UserDAO {

    public static boolean queryCheckUser(User user) {
        boolean isCorrect = false;
        try {
            PreparedStatement sqlQuery = DataSource.getConn().prepareStatement(QUERY_USER_CHECK_PASSWORD_PREP);
            sqlQuery.setString(1, user.getUserName());
            sqlQuery.setString(2, user.getPassword());

            ResultSet results = sqlQuery.executeQuery();
            while (results.next()) {
                if (results.getInt(1) == 1) {
                    isCorrect = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isCorrect;
    }

    public static User queryGetUserPermission(User user) {
        try {
            PreparedStatement sqlQuery = DataSource.getConn().prepareStatement(QUERY_USER_GET_USER_PERMISSION_PREP);
            sqlQuery.setString(1, user.getUserName());
            sqlQuery.setString(2, user.getPassword());

            ResultSet results = sqlQuery.executeQuery();
            while (results.next()) {
                user.setPermission(PermissionAction.valueOf(results.getString(USER_PERMISSION)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
