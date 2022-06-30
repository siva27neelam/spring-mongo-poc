package org.pnc.user.repository;

import org.pnc.user.model.UserDataEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<UserDataEntity, String> {
    UserDataEntity findByEmail(String email);

    void deleteByEmail(String email);

    List<UserDataEntity> findByFirstNameAndLastName(String firstName, String lastName);
}
