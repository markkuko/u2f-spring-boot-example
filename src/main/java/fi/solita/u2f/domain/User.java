package fi.solita.u2f.domain;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "username")
@ToString
public class User  {

    @Id
    private String username;
    private String password;
    private Boolean mfaEnabled;
    private Boolean accountNonLocked;
    private Boolean enabled;

}
