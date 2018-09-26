package fi.solita.u2f.domain;

import com.yubico.u2f.data.messages.SignRequestData;
import org.springframework.data.annotation.Id;


public class U2FAuthRequest {
    @Id
    private String id;
    private SignRequestData data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SignRequestData getData() {
        return data;
    }

    public void setData(SignRequestData data) {
        this.data = data;
    }
}
