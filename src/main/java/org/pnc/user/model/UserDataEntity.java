package org.pnc.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "UserData")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDataEntity {
    @Id
    private String id;
    @Indexed(name = "unique_email", unique = true)
    private String email;
    private String firstName;
    private String lastName;
    private List<String> hobbies;
    private List<String> regions;
    private Address address;
}
