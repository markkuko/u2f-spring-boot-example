package fi.solita.u2f.domain;

import com.yubico.u2f.data.DeviceRegistration;
import org.springframework.data.annotation.Id;

public class Device {
    @Id
    private String id;
    private String username;
    private DeviceRegistration deviceRegistration;

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

    public DeviceRegistration getDeviceRegistration() {
        return deviceRegistration;
    }

    public void setDeviceRegistration(DeviceRegistration deviceRegistration) {
        this.deviceRegistration = deviceRegistration;
    }
}
