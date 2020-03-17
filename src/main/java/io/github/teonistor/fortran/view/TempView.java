package io.github.teonistor.fortran.view;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TempView {
    @GetMapping
    String index() {
        return "Lucrăm la asta";
    }
}
