package org.pnc.user.service

import org.pnc.user.model.Address
import org.pnc.user.model.UserData
import org.pnc.user.model.UserDataEntity
import org.pnc.user.repository.UserRepository
import org.slf4j.Logger
import org.springframework.web.server.ResponseStatusException
import spock.lang.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import static org.mockito.Mockito.*

class UserServiceImplTest extends Specification {

    UserRepository userRepository
    UserServiceImpl userServiceImpl

    def setup() {
        userRepository = Mock(UserRepository)
        userServiceImpl = new UserServiceImpl(userRepository)
    }

    def "test get All Users"() {
        given:
        userRepository.findAll() >> response

        when:
        List<UserData> result = userServiceImpl.getAllUsers()

        then:
        result.size() == expectedResult;

        where:
        response                                     || expectedResult
        [new UserDataEntity(), new UserDataEntity()] || 2
        null                                         || 0

    }

    def "test create User"() {
        given:
        userRepository.save(_ as UserDataEntity) >> {}

        when:
        userServiceImpl.createUser(new UserData())

        then:
        1 * userRepository.save(_);
    }

    def "test create User Error Scenario"() {
        given:
        userRepository.save(_ as UserDataEntity) >> {throw new Exception("Error")}

        when:
        userServiceImpl.createUser(new UserData())

        then:
        def e = thrown(ResponseStatusException)
        e.message !=null
    }

    def "test delete User"() {
        given:
        userRepository.deleteByEmail(_ as String) >> {}

        when:
        userServiceImpl.deleteUser("email")

        then:
        1 * userRepository.deleteByEmail(_);
    }

    def "test delete User error scenario"() {
        given:
        userRepository.deleteByEmail(_ as String) >> {throw new Exception("Error")}

        when:
        userServiceImpl.deleteUser("email")

        then:
        def e = thrown(ResponseStatusException)
        e.message !=null
    }

    def "test getuser"() {
        given:
        userRepository.findByEmail(_) >> (new UserDataEntity("id", "email", "firstName", "lastName", ["String"], "regions", new Address("street", "city", "state", "zipcode")))

        when:
        UserData result = userServiceImpl.getuser("email")

        then:
        result.getEmail() == "email";
    }

    def "test getuser error scenario"() {
        given:
        userRepository.findByEmail(_) >> null

        when:
        userServiceImpl.getuser("email")

        then:
        def e = thrown(ResponseStatusException)
        e.message !=null
    }

    def "test search Users"() {
        given:
        userRepository.findByFirstNameAndLastName(_ as String, _ as String) >> [new UserDataEntity("id", "email", "firstName", "lastName", ["String"], "regions", new Address("street", "city", "state", "zipcode"))]

        when:
        List<UserData> result = userServiceImpl.searchUsers(["firstName": "firstName", "lastName":"lastName"])

        then:
        result.size() == 1;
    }
}
