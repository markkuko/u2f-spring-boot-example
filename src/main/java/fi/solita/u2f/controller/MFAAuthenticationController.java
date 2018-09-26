package fi.solita.u2f.controller;

import com.yubico.u2f.data.messages.SignRequestData;
import com.yubico.u2f.data.messages.SignResponse;
import com.yubico.u2f.exceptions.*;
import fi.solita.u2f.domain.User;
import fi.solita.u2f.repository.UserRepository;
import fi.solita.u2f.service.U2FService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class MFAAuthenticationController {

    private static final Logger log = LoggerFactory.getLogger(MFAAuthenticationController.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    U2FService u2FService;
    @GetMapping(path= "/u2fauth")
    public String startAuthentication(Map<String, Object> model)
            throws NoEligibleDevicesException,U2fBadConfigurationException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        log.debug("Starting authentication initiation, username {}", username);
        User user = userRepository.findById(username).orElse(null);
        if(user == null) {
            log.debug("User null, username {}", username);
            return "redirect:/u2fregister";
        }
        if(user.isMfaEnabled() == null || !user.isMfaEnabled().booleanValue()) {
            return "redirect:/u2fregister";
        }
        SignRequestData requestData = u2FService.initAuthentication(
                username);
        model.put("data", requestData.toJson());
        model.put("username", username);
        log.debug("Set data for U2F authentication challenge.");
        return "u2f_auth";
    }

    @PostMapping(path= "/u2fauth")
    public String finishAuthentication(Map<String, Object> model,
                                       @RequestParam String tokenResponse
                                       ) throws
            U2fBadInputException{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        log.debug("Starting authentication validation, username {}", username);
        SignResponse response = SignResponse.fromJson(tokenResponse);

        if(u2FService.verifyAuthentication(response, username)) {
            return "redirect:/";
        } else{
            model.put("authError",true);
            return "redirect:/u2fauth";
        }
    }


}
