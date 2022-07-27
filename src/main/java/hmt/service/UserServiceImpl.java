package hmt.service;

import hmt.model.Products;
import hmt.model.Users;
import hmt.utils.MySQLConnUtils;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class UserServiceImpl implements IUserService {

    private static String SELECT_ALL_USERS = "" +
            "SELECT " +
            "u.id, " +
            "u.username, " +
            "u.password, " +
            "u.name, " +
            "u.phone, " +
            "u.email, " +
            "u.address, " +
            "u.created_time " +
            "FROM users AS u " +
            "ORDER BY u.id DESC;";

    private static String SELECT_USER_BY_ID = "" +
            "SELECT " +
            "u.id, " +
            "u.username, " +
            "u.password, " +
            "u.name, " +
            "u.phone, " +
            "u.email, " +
            "u.address, " +
            "u.created_time " +
            "FROM users AS u " +
            "WHERE u.id = ?;";

    private static String SP_ADD_USER = "{CALL sp_add_user(?, ?, ?, ?, ?, ?, ?, ?)}";

    private static String SP_EDIT_USER_BY_ID = "{CALL sp_edit_user_by_id(?, ?, ?, ?, ?, ?, ?)}";

    private static String SP_DELETE_USER_BY_ID = "{CALL sp_delete_user_by_id(?, ?, ?)}";

    private static String SEARCH_USERS = "" +
            "SELECT " +
            "u.id, " +
            "u.username, " +
            "u.password, " +
            "u.name, " +
            "u.phone, " +
            "u.email, " +
            "u.address, " +
            "u.created_time " +
            "FROM users AS u " +
            "WHERE " +
            "u.name LIKE ? " +
            "OR u.phone LIKE ? " +
            "OR u.email LIKE ? " +
            "OR u.address LIKE ? " +
            "OR created_time LIKE ? " +
            "ORDER BY u.id DESC;";

    @Override
    public List<Users> findAllUsers() {
        List<Users> userList = new ArrayList<>();
        try{
            Connection connection = MySQLConnUtils.getConnection();
            PreparedStatement statement = connection.prepareCall(SELECT_ALL_USERS);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String address = rs.getString("address");
                Date createdTime = rs.getDate("created_time");
                userList.add(new Users(id, username, password, name, phone, email, address, createdTime));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            MySQLConnUtils.printSQLException(e);
        }
        return userList;
    }

    @Override
    public Users findUserById(int userId) {
        Users user = null;
        try {
            Connection connection = MySQLConnUtils.getConnection();
            PreparedStatement statement = connection.prepareCall(SELECT_USER_BY_ID);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String address = rs.getString("address");
                Date createdTime = rs.getTime("created_time");
                user = new Users(id, username, password, name, phone, email, address, createdTime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            MySQLConnUtils.printSQLException(e);
        }
        return user;
    }

    @Override
    public Map<String, String> add(Users user) {
        Map<String, String> result = new HashMap<>();
        try {
            Connection connection = MySQLConnUtils.getConnection();
            CallableStatement statement = connection.prepareCall(SP_ADD_USER);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getPhone());
            statement.setString(5, user.getEmail());
            statement.setString(6, user.getAddress());
            statement.registerOutParameter(7, Types.BOOLEAN);
            statement.registerOutParameter(8, Types.VARCHAR);
            statement.execute();

            Boolean isSuccess = statement.getBoolean("isSuccess");
            String message = statement.getString("message");

            result.put("isSuccess", isSuccess.toString());
            result.put("message", message);

        } catch (SQLException e) {
            MySQLConnUtils.printSQLException(e);
        }
        return result;
    }

    @Override
    public Map<String, String> edit(Users user) {
        Map<String, String> result = new HashMap<>();
        try {
            Connection connection = MySQLConnUtils.getConnection();
            CallableStatement statement = connection.prepareCall(SP_EDIT_USER_BY_ID);

            statement.setInt(1, user.getId());
            statement.setString(2, user.getName());
            statement.setString(3, user.getPhone());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getAddress());
            statement.registerOutParameter(6, Types.BOOLEAN);
            statement.registerOutParameter(7, Types.VARCHAR);
            statement.execute();

            Boolean isSuccess = statement.getBoolean("isSuccess");
            String message = statement.getString("message");

            result.put("isSuccess", isSuccess.toString());
            result.put("message", message);

        } catch (SQLException e) {
            e.printStackTrace();
            MySQLConnUtils.printSQLException(e);
        }
        return result;
    }

    @Override
    public boolean delete(int id) {
        boolean isSuccess = false;
        try {
            Connection connection = MySQLConnUtils.getConnection();
            CallableStatement statement = connection.prepareCall(SP_DELETE_USER_BY_ID);
            statement.setInt(1, id);
            statement.registerOutParameter(2, Types.BOOLEAN);
            statement.registerOutParameter(3, Types.VARCHAR);
            statement.execute();

            isSuccess = statement.getBoolean("isSuccess");

        } catch (SQLException e) {
            e.printStackTrace();
            MySQLConnUtils.printSQLException(e);
        }
        return isSuccess;
    }

    @Override
    public List<Users> searchUsers(String key) {
        List<Users> userList = new ArrayList<>();
        try {
            Connection connection = MySQLConnUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(SEARCH_USERS);
            statement.setString(1, "%" + key + "%");
            statement.setString(2, "%" + key + "%");
            statement.setString(3, "%" + key + "%");
            statement.setString(3, "%" + key + "%");
            statement.setString(3, "%" + key + "%");

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String address = rs.getString("address");
                Date timeCreated = rs.getTime("created_time");
                userList.add(new Users(id, username, password, name, phone, email, address, timeCreated));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            MySQLConnUtils.printSQLException(e);
        }
        return userList;
    }
}
