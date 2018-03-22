package fi.solita.u2f.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Scope("session")
public class PageController {

    @RequestMapping("/home")
    public String homePage() {
        return "home";
    }
    @RequestMapping("/")
    public String indexPage() {
        return "index";
    }

}
