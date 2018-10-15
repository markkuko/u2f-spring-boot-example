package fi.solita.u2f.domain;

import lombok.*;

import com.yubico.u2f.data.DeviceRegistration;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class Device {
    @Id
    private String id;
    private String username;
    private DeviceRegistration deviceRegistration;
}
