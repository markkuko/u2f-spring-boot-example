package fi.solita.u2f.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Scope("session")
public class PageController {

    @GetMapping(path="/home")
    public String homePage() {
        return "home";
    }
    @GetMapping(path="/")
    public String indexPage() {
        return "index";
    }

}
