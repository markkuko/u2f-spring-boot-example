package fi.solita.u2f.domain;

import com.yubico.u2f.data.messages.SignRequestData;
import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class U2FAuthRequest {
    @Id
    private String id;
    private SignRequestData data;
}
