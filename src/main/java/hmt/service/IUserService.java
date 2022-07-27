package hmt.service;

import hmt.model.Users;

import java.util.List;
import java.util.Map;

public interface IUserService {

    Users findUserById(int userId);

    List<Users> findAllUsers();

    Map<String, String> edit(Users user);

    Map<String, String> add(Users user);

    boolean delete(int userId);

    List<Users> searchUsers(String key);
}
