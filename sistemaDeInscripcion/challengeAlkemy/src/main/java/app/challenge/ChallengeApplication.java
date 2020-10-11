package app.challenge;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.factory.*;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.web.*;
import javax.servlet.http.*;
import org.springframework.security.web.authentication.logout.*;
import org.springframework.security.core.userdetails.*;
import java.io.IOException;
import app.challenge.entidades.*;
import app.challenge.repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@SpringBootApplication
public class ChallengeApplication {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(ChallengeApplication.class, args);
    }

    @RequestMapping("/")
    public String loginForm() {
        return "formLogin.html";
    }

    @RequestMapping("/admin/sistema")
    public String adminPage() {
        return "sistemaAdmin.html";
    }

    @RequestMapping("/alumno/{idAlumno}/sistema")
    public String alumnoPagina() {
        return "sistemaAlumno.html";
    }

    @Bean
    public CommandLineRunner initData(AlumnoRepository alumnoRepository,
                                      ProfesorRepository profesorRepository,
                                      MateriaRepository materiaRepository,
                                      AdminRepository adminRepository) {
        return (args) -> {

            String passAlumno = passwordEncoder.encode("1");
            Alumno alumno = new Alumno(1,1234,passAlumno);
            alumnoRepository.save(alumno);

            String passAdmin = passwordEncoder.encode("2");
            Admin admin = new Admin(1,5678,passAdmin);
            adminRepository.save(admin);

            Profesor profesor = new Profesor(1,"Nadia","Colque",12,true);
            profesorRepository.save(profesor);

            Materia materia = new Materia(1,"Matemática","13:00","15:00",profesor,12);
            materiaRepository.save(materia);
        };
    }
}

@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    AlumnoRepository alumnoRepository;

    @Autowired
    AdminRepository adminRepository;

    @Bean
    UserDetailsService userDetailsService() {

        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

                long dni = Long.parseLong(username);

                if (alumnoRepository.existsByDni(dni)) {

                    Alumno alumno = alumnoRepository.findByDni(dni);
                    String legajoString = alumno.getPass();
                    return new User(username, legajoString,
                            AuthorityUtils.createAuthorityList("USER"));

                } else if (adminRepository.existsByDni(dni)) {

                    Admin admin = adminRepository.findByDni(dni);
                    String passString = admin.getPasswordNumerico();
                    return new User(username, passString,
                            AuthorityUtils.createAuthorityList("ADMIN"));

                } else {
                    throw new UsernameNotFoundException("usuario con dni : " + dni + " desconocido");
                }
            }
        };

    }

    @EnableWebSecurity
    @Configuration
    class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.authorizeRequests()
                    .antMatchers("/api/admin/**","api/alumno/**").authenticated()
                    .antMatchers("/api/admin/**").hasRole("ADMIN");
            http.formLogin().loginPage("/api/login");
            http.logout().logoutUrl("/api/logout");

            http.csrf().disable();

            http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

            http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

            http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

            http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

        }

        private void clearAuthenticationAttributes(HttpServletRequest request) {

            HttpSession session = request.getSession(false);

            if (session != null) {
                session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            }
        }
    }
}
