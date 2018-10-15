package fi.solita.u2f.domain;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "username")
@ToString
public class UserRegistrationForm {

    @NotNull
    @Size(min=4)
    private String username;

    @NotNull
    @Size(min=8)
    private String password;

    @NotNull
    @Size(min=8)
    private String password2;
}
