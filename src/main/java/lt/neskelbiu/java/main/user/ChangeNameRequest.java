package lt.neskelbiu.java.main.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeNameRequest {
    private String firstname;
    private String lastname;
}
