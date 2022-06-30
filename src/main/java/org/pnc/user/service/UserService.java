package org.pnc.user.service;

import org.pnc.user.model.UserData;

import java.util.List;
import java.util.Map;

public interface UserService {

    List<UserData> getAllUsers();

    void createUser(UserData userData);

    void deleteUser(String id);

    UserData getuser(String id);

    List<UserData>  searchUsers(Map<String, String> queryParams);
}
