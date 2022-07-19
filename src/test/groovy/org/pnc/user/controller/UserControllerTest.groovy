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

    def "create User"() {
        given: "I have the user request"
        UserData userData = new UserData();
        userData.setFirstName("siva");
        userData.setLastName("Neelam");
        userData.setEmail("hello@gmail.com");

        when: "i call the controller with the request "
        userService.createUser(_ as UserData) >> {}
        ResponseEntity<java.lang.Void> user = userController.createUser(new UserData())

        then: "User should be created"
        user.getStatusCode().name() == "CREATED"
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

    def "Maximum of 2 numbers"(){
        given: "I have 2 numbers"
        when: "I pass 2 numbers as arguments to max function"
        int result = Math.max(a, b);
        then:"I get the maximum of the 2 numbers"
        result == c;
        where: "data is from the below source"
        a   | b     || c
        2   | 3     || 3
        14  | 20    || 20
        0   |0      || 0

    }
}
