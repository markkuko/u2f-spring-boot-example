package fi.solita.u2f.controller;

import com.yubico.u2f.exceptions.NoEligibleDevicesException;
import com.yubico.u2f.exceptions.U2fBadConfigurationException;
import fi.solita.u2f.domain.UserRegistrationForm;
import fi.solita.u2f.domain.User;
import fi.solita.u2f.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class UserRegistrationController {

    private static final Logger log = LoggerFactory.getLogger(UserRegistrationController.class);

    @Autowired
    UserRepository userRepository;


    @RequestMapping(method= RequestMethod.GET, value= "/registration")
    public String registrationPage() {
        return "/registration";
    }

    @RequestMapping(method= RequestMethod.POST, value= "/registration")
    public String startAuthentication(Map<String, Object> model,
                                      @Valid  @ModelAttribute UserRegistrationForm form,
                                      BindingResult bindingResult)
            throws NoEligibleDevicesException,U2fBadConfigurationException {

        log.debug("Starting registration");
        if(bindingResult.hasErrors() || !form.getPassword().equals(form.getPassword2())) {
            log.debug("Validation failed for form");
            model.put("errorMessage", "Error");
            return "/registration";
        }

        User userCheck = userRepository.findById(form.getUsername()).orElse(null);
        if(userCheck != null) {
            log.debug("User not null, username {}", form.getUsername());
            model.put("errorMessage", "Error");
            return "/registration";
        }
        User user = new User();
        user.setUsername(form.getUsername());
        user.setMfaEnabled(Boolean.FALSE);
        user.setPassword(encodePassword(form.getPassword()));
        userRepository.save(user);
        model.put("successMessage", "Success");
        return "redirect:/login";
    }
    private String encodePassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return "{bcrypt}".concat(hashedPassword);
    }


}
