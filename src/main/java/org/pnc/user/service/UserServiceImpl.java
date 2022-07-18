package org.pnc.user.service;

import lombok.RequiredArgsConstructor;
import org.pnc.user.model.UserData;
import org.pnc.user.model.UserDataEntity;
import org.pnc.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String LAST_NAME = "lastName";
    private static final String FIRST_NAME = "firstName";
    private final UserRepository userRepository;

    @Override
    public List<UserData> getAllUsers() {
        List<UserDataEntity> all = userRepository.findAll();
        List<UserData> userDataList = new ArrayList<>();
        Optional.ofNullable(all).orElseGet(Collections::emptyList).forEach(e -> userDataList.add(mapToData(e)));;
        return userDataList;
    }

    @Override
    public void createUser(UserData userData) {
        try {
            LOGGER.info(userData.toString());
            userRepository.save(maptoEntity(userData));
        } catch (Exception ex) {
            LOGGER.error("Error saving record in database: " + ex.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving record in database - " + ex.getMessage());
        }
    }

    @Override
    public void deleteUser(String email) {
        try {
            userRepository.deleteByEmail(email);
        } catch (Exception ex) {
            LOGGER.error("Error deleting record in database: " + ex.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while deleting record in database - " + ex.getMessage());
        }
    }

    @Override
    public UserData getuser(String email) {
        UserDataEntity byEmail = userRepository.findByEmail(email);
        Optional.ofNullable(byEmail).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User record not found"));
        return mapToData(byEmail);
    }

    private UserData mapToData(UserDataEntity e) {
        UserData data = new UserData();
        data.setFirstName(e.getFirstName());
        data.setLastName(e.getLastName());
        data.setEmail(e.getEmail());
        data.setHobbies(e.getHobbies());
        data.setRegion(e.getRegions());
        data.setAddress(e.getAddress());
        return data;
    }

    @Override
    public List<UserData> searchUsers(Map<String, String> queryParams) {
        String firstName = queryParams.get(FIRST_NAME);
        String lastName = queryParams.get(LAST_NAME);
        List<UserDataEntity> byFirstNameAndLastName = userRepository.findByFirstNameAndLastName(firstName, lastName);
        Optional.ofNullable(byFirstNameAndLastName).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No user exist for given search"));
        List<UserData> userDataList = new ArrayList<>();
        Optional.ofNullable(byFirstNameAndLastName).orElseGet(Collections::emptyList).forEach(e -> userDataList.add(mapToData(e)));
        return userDataList;
    }

    private UserDataEntity maptoEntity(UserData e) {
        UserDataEntity entity = new UserDataEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setFirstName(e.getFirstName());
        entity.setLastName(e.getLastName());
        entity.setEmail(e.getEmail());
        entity.setHobbies(e.getHobbies());
        entity.setRegions(e.getRegion());
        entity.setAddress(e.getAddress());
        return entity;
    }
}
