package io.github.teonistor.fortran;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication(exclude=ErrorMvcAutoConfiguration.class)
public class FortranRoot {

    public static void main(final String[] arg) {
        SpringApplication.run(FortranRoot.class, arg);
    }
}
