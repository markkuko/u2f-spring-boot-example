package fi.solita.u2f.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }
}
