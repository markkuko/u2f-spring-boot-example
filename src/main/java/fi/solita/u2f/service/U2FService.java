package fi.solita.u2f.service;

import com.yubico.u2f.U2F;
import com.yubico.u2f.data.DeviceRegistration;
import com.yubico.u2f.data.messages.RegisterRequestData;
import com.yubico.u2f.data.messages.RegisterResponse;
import com.yubico.u2f.data.messages.SignRequestData;
import com.yubico.u2f.data.messages.SignResponse;
import com.yubico.u2f.exceptions.NoEligibleDevicesException;
import com.yubico.u2f.exceptions.U2fAuthenticationException;
import com.yubico.u2f.exceptions.U2fBadConfigurationException;
import com.yubico.u2f.exceptions.U2fRegistrationException;
import fi.solita.u2f.config.Constants;
import fi.solita.u2f.domain.U2FAuthRequest;
import fi.solita.u2f.domain.Device;
import fi.solita.u2f.domain.U2FRegisterRequest;
import fi.solita.u2f.domain.User;
import fi.solita.u2f.repository.AuthRequestRepository;
import fi.solita.u2f.repository.U2FDeviceRepository;
import fi.solita.u2f.repository.RegisterRequestRepository;
import fi.solita.u2f.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Services for
 * @author markkuko
 */
@Service
public class U2FService {

    private static final Logger log = LoggerFactory.getLogger(U2FService.class);

    @Autowired
    U2F u2f;

    @Autowired
    private AuthRequestRepository authRequestRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private U2FDeviceRepository devices;

    @Autowired
    private RegisterRequestRepository requestDevices;

    public SignRequestData initAuthentication(String username)
        throws NoEligibleDevicesException, U2fBadConfigurationException{
        List<Device> userDevices = devices.findByUsername(username);
        SignRequestData requestData = u2f.startSignature(Constants.SERVER_ADDRESS,
                getRegistrations(username));
        U2FAuthRequest u2FAuthRequest = new U2FAuthRequest();
        u2FAuthRequest.setData(requestData);
        u2FAuthRequest.setId(requestData.getRequestId());
        authRequestRepository.save(u2FAuthRequest);

        return requestData;
    }

    public boolean verifyAuthentication(SignResponse response,
                                        String username) {

        U2FAuthRequest u2FAuthRequest
                = authRequestRepository.findById(response.getRequestId()).orElse(null);
        if(u2FAuthRequest == null) {
            log.info("Did not find auth request by id");
            return false;
        }
        SignRequestData signRequest = u2FAuthRequest.getData();
        try {
            u2f.finishSignature(signRequest, response, getRegistrations(username));
            authRequestRepository.deleteById(response.getRequestId());
            grantAuthority();
            return true;
        } catch (U2fAuthenticationException e) {
            log.warn("U2fAuthenticationException: {}", e.getMessage());
            authRequestRepository.deleteById(response.getRequestId());
            return false;
        }

    }
    public RegisterRequestData initRegistration(String username)
        throws  U2fBadConfigurationException{
        RegisterRequestData registerRequestData = u2f.startRegistration(Constants.SERVER_ADDRESS,
                getRegistrations(username));

        U2FRegisterRequest device = new U2FRegisterRequest();
        device.setUsername(username);
        device.setRegisterRequestData(registerRequestData);
        device.setId(registerRequestData.getRequestId());
        requestDevices.save(device);
        return registerRequestData;


    }
    public boolean verifyRegistration(RegisterResponse registerResponse,
                                      String username)
    throws U2fRegistrationException {
        U2FRegisterRequest u2FRegisterRequest =  requestDevices.findById(
                registerResponse.getRequestId()).orElse(null);
        if(u2FRegisterRequest == null) {
            log.info("Did not find requester device.");
            return false;
        }
        User user =  userRepository.findByUsername(username);
        RegisterRequestData registerRequestData = u2FRegisterRequest.getRegisterRequestData();
        devices.deleteById(registerResponse.getRequestId());
        DeviceRegistration registration =
                u2f.finishRegistration(registerRequestData, registerResponse);
        Device device = new Device();
        device.setUsername(username);
        device.setDeviceRegistration(registration);
        devices.save(device);
        user.setMfaEnabled(true);
        userRepository.save(user);
        return true;
    }
    private List<DeviceRegistration> getRegistrations(String username) {
        List<Device> findRegistrationsByUsername =
                devices.findByUsername(username);
        List<DeviceRegistration> deviceList = new ArrayList<DeviceRegistration>();
        for(Device device: findRegistrationsByUsername) {
            deviceList.add(device.getDeviceRegistration());
        }
        log.debug("Found {} devices for user {}", deviceList.size(), username);
        return deviceList;
    }
    private void grantAuthority() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(auth.getAuthorities());
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                auth.getPrincipal(), auth.getCredentials(), authorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }
}
