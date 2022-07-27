package hmt.service;

import hmt.model.Users;

import java.sql.SQLException;

public interface ILoginService {

    Users login(String username, String password) throws SQLException;
}
