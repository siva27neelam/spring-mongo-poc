package org.pnc.user.model;

import lombok.Data;
import java.util.List;

@Data
public class UserData {
    private String firstName;
    private String lastName;
    private String email;
    private List<String> region;
    private List<String> hobbies;
}
