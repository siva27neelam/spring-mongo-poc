package org.pnc.user.controller

import org.pnc.user.model.UserData
import org.pnc.user.service.UserService
import org.springframework.http.ResponseEntity
import spock.lang.*

class UserControllerTest extends Specification {
    UserController userController;
    UserService userService;

    def setup() {
        userService = Mock(UserService);
        userController = new UserController(userService);
    }

    def "test get All Users"() {
        given:
        userService.getAllUsers() >> response;

        when:
        ResponseEntity<List<UserData>> result = userController.getAllUsers()

        then:
        result.getStatusCode().value() == expectedResult

        where:
        response                                  || expectedResult
        [new UserData()]                          || 200
        [new UserData(), new UserData()]          || 200
    }

    def "test create User"() {
        given:
        userService.createUser(_ as UserData) >> {}

        when:
        ResponseEntity<java.lang.Void> result = userController.createUser(new UserData())

        then:
        result != null
    }

    def "test update User"() {
        given:
        userService.createUser(_ as UserData) >> {}

        when:
        ResponseEntity<Void> result = userController.updateUser(new UserData())

        then:
        result != null
    }

    def "test delete User"() {
        given:
        userService.deleteUser(_ as String) >> {}

        when:
        ResponseEntity<Void> result = userController.deleteUser("id")

        then:
        result != null
    }

    def "test getuser"() {
        given:
        userService.getuser(_ as String) >> new UserData()

        when:
        ResponseEntity<UserData> result = userController.getuser("id")

        then:
        result != null
    }

    def "test search Users"() {
        given:
        userService.searchUsers(_ as Map) >> [new UserData()]

        when:
       ResponseEntity<List<UserData>> result = userController.searchUsers(["firstName": "Lastname"])

        then:
        result != null
    }
}
