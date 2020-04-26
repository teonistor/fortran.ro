package io.github.teonistor.fortran;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.regex.Pattern;

import static org.springframework.security.web.csrf.CsrfFilter.DEFAULT_CSRF_MATCHER;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    private static final Pattern CSRF_ENABLED_URIS = Pattern.compile("/login|/logout|/any.*");

    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {

        auth
            .inMemoryAuthentication()
            .withUser("user").password("{bcrypt}$2a$10$zUi5vlxz/BrgAh2qbUmv9egCanrFjCD2A5se9Kj1jjR5ArC4GtSJG").roles("USER");

    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.formLogin().and()

                /* As per https://devcenter.heroku.com/articles/preparing-a-spring-boot-app-for-production-on-heroku#force-the-use-of-https
                 * The Heroku router sets the "X-Forwarded-Proto" header, therefore its presence is a good way to distinguish server
                 * requests from localhost requests. Spring Security magic handles redirection to https
                 */
                /* Leave out for now until we figure out certificates on own domain
                .requiresChannel()
                .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
                .requiresSecure()
                .and()*/

                // CSRF for Spring Security's default methods
                // N.b.   https://www.baeldung.com/spring-security-csrf
                //   and  https://stackoverflow.com/a/34994299
                .csrf().requireCsrfProtectionMatcher(r -> DEFAULT_CSRF_MATCHER.matches(r)
                                                       && CSRF_ENABLED_URIS.matcher(r.getRequestURI()).matches())
                .and()

                .authorizeRequests()

                .antMatchers("/secure/**")
                .hasAnyRole("ADMIN", "USER")

                .antMatchers("/**")
                .permitAll()
        ;
    }

    // For manual password alteration
    public static void main(String[] arg) {
        System.out.println(new BCryptPasswordEncoder().encode("[]"));
    }
}
