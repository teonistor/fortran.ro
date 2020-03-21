package io.github.teonistor.fortran;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;


@SpringBootApplication(exclude=ErrorMvcAutoConfiguration.class)
public class FortranRoot {

    public static void main(final String[] arg) {
        SpringApplication.run(FortranRoot.class, arg);
    }
}
