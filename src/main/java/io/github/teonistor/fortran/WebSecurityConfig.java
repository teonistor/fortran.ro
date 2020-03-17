package io.github.teonistor.fortran;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.regex.Pattern;

import static org.springframework.security.web.csrf.CsrfFilter.DEFAULT_CSRF_MATCHER;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    private static final Pattern CSRF_ENABLED_URIS = Pattern.compile("/login|/logout|/any.*");
    private static final String ROLE_PREFIX = "ROLE_";

    // create table snoop_users (principal varchar primary key, credentials varchar not null, role varchar not null default 'USER');
    // insert into snoop_users (principal, credentials) values ('user', 'password');

    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {

        auth
            .inMemoryAuthentication()
            .withUser("user").password("{enc}password").roles("USER");

    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.formLogin().and()

                /* As per https://devcenter.heroku.com/articles/preparing-a-spring-boot-app-for-production-on-heroku#force-the-use-of-https
                 * The Heroku router sets the "X-Forwarded-Proto" header, therefore its presence is a good way to distinguish server
                 * requests from localhost requests. Spring Security magic handles redirection to https
                 */
                .requiresChannel()
                .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
                .requiresSecure()
                .and()

                // CSRF for Spring Security's default methods and only for pages where I've set it up
                // N.b.   https://www.baeldung.com/spring-security-csrf
                //   and  https://stackoverflow.com/a/34994299
                .csrf().requireCsrfProtectionMatcher(r -> DEFAULT_CSRF_MATCHER.matches(r)
                                                       && CSRF_ENABLED_URIS.matcher(r.getRequestURI()).matches())
                .and()

                .authorizeRequests()

                .antMatchers("/videohub/**")
                .hasAnyRole("ADMIN", "USER")

                .antMatchers("/any/login", "/any/find/*")
                .hasAnyRole("ADMIN", "USER")

                .antMatchers("/**")
                .permitAll()
        ;
    }

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("https://teonistor.github.io");
    }

    // For manual password alteration
    public static void main(String[] arg) {
        System.out.println(new BCryptPasswordEncoder().encode("[]"));
    }
}
