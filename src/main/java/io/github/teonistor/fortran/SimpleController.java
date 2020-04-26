package io.github.teonistor.fortran;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class SimpleController {

    @GetMapping("/")
    String index() {
        return "index";
    }

    @GetMapping("/secure")
    String secure() {
        return "secure";
    }
}
