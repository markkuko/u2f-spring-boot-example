package fi.solita.u2f.controller;

import com.yubico.u2f.data.messages.RegisterRequestData;
import com.yubico.u2f.data.messages.RegisterResponse;
import com.yubico.u2f.exceptions.U2fBadConfigurationException;
import com.yubico.u2f.exceptions.U2fBadInputException;
import com.yubico.u2f.exceptions.U2fRegistrationException;
import fi.solita.u2f.service.U2FService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@Scope("session")
public class MFARegistrationController {

    @Autowired
    U2FService u2FService;

    private static final Logger log = LoggerFactory.getLogger(MFARegistrationController.class);

    @GetMapping(path="/u2fregister")
    public String startRegistration(Map<String, Object> model) throws U2fBadConfigurationException{
        log.debug("MFARegistration initialization start.");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        var username = auth.getName();
        RegisterRequestData registerRequestData = u2FService.initRegistration(username);
        model.put("data", registerRequestData.toJson());

        return "u2f_register";
    }

    @PostMapping(path= "/u2fregister")
    public String finishRegistration(Map<String, Object> model,
                                     @RequestParam String tokenResponse)
            throws U2fRegistrationException, U2fBadInputException {
        log.debug("MFARegistration verification start.");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        var username = auth.getName();
        RegisterResponse registerResponse = RegisterResponse.fromJson(tokenResponse);
        boolean verification = u2FService.verifyRegistration(registerResponse,
                username);
        if(verification) {
            log.debug("MFARegistration verification success, redirecting.");
            return "redirect:/u2fauth";
        } else {
            log.warn("MFARegistration verification failed, redirecting to register.");
            return "redirect:/u2fauth";
        }
    }


}
