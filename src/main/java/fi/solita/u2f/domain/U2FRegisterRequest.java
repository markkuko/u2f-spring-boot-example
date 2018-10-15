package fi.solita.u2f.domain;

import com.yubico.u2f.data.messages.RegisterRequestData;
import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class U2FRegisterRequest {
    @Id
    private String id;
    private String username;
    private RegisterRequestData registerRequestData;

}
