package org.pnc.user.model;

import lombok.Data;
import java.util.List;

@Data
public class UserData {
    private String firstName;
    private String lastName;
    private String email;
    private String region;
    private List<String> hobbies;
    private Address address;
}
