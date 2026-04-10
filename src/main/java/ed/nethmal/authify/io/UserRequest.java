package ed.nethmal.authify.io;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequest {

    private String name;
    private String email;
    private String password;
}
