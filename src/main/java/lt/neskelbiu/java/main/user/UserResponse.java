package lt.neskelbiu.java.main.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String username;
    private Role role;
    private String firstname;
    private String lastname;
    private String email;
    private String createdAt;
    private String updatedAt;
    private Boolean havePicture;
}
