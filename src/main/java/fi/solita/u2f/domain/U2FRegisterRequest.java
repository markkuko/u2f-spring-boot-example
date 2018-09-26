package fi.solita.u2f.domain;

import com.yubico.u2f.data.messages.RegisterRequestData;
import org.springframework.data.annotation.Id;

public class U2FRegisterRequest {
    @Id
    private String id;
    private String username;
    private RegisterRequestData registerRequestData;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public RegisterRequestData getRegisterRequestData() {
        return registerRequestData;
    }

    public void setRegisterRequestData(RegisterRequestData registerRequestData) {
        this.registerRequestData = registerRequestData;
    }
}
