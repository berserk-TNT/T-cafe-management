package hmt.service;

import hmt.model.Users;
import hmt.utils.MySQLConnUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginServiceImpl implements ILoginService {

    private static String LOGIN = "SELECT * FROM users WHERE username = ? and password = ?";

    @Override
    public Users login(String username, String password) throws SQLException {
        Connection connection = MySQLConnUtils.getConnection();
        PreparedStatement statement = connection.prepareStatement(LOGIN);
        statement.setString(1, username);
        statement.setString(2, password);
        ResultSet rs = statement.executeQuery();

        Users user = null;
        if (rs.next()) {
            user = new Users();
            user.setUsername(username);
            user.setPassword(password);
        }
        return user;
    }
}
