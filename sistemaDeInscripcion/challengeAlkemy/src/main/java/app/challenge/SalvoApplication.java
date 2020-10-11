//package com.codeoftheweb.salvo;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.security.crypto.password.*;
//import org.springframework.security.crypto.factory.*;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.web.*;
//import javax.servlet.http.*;
//import org.springframework.security.web.authentication.logout.*;
//import org.springframework.security.core.userdetails.*;
//import java.io.IOException;
//import com.codeoftheweb.salvo.entidades.*;
//import com.codeoftheweb.salvo.repositorios.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.boot.autoconfigure.security.SecurityProperties;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//
//@Controller
//@SpringBootApplication
//public class SalvoApplication {
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    public static final long HOUR = 3600*1000;
//
//    public static void main(String[] args) {
//        SpringApplication.run(SalvoApplication.class, args);
//    }
//
//
///*
//    @RequestMapping("/game_gp={gamePlayerId}")
//    public String game(@PathVariable(value = "gamePlayerId") int gamePlayerId) {
//        return"game.html";
//    }
//
//    jkhajskdfklja laksjdflkjaskdf laksdlfkjalksdf
//*/
//
//    @RequestMapping("/games")
//    public String games() {
//        return "games.html";
//    }
//
//    @RequestMapping("/{id}")
//    public String game() {
//        return "game.html";
//    }
///*
//    @RequestMapping
//    public String index() {
//        return "index.html";
//    }
//*/
//    @Bean
//    public CommandLineRunner initData(PlayerRepository players,
//                                      GameRepository games,
//                                      GamePlayerRepository gamePlayers,
//                                      ShipRepository ships,
//                                      SalvoRepository salvoes,
//                                      ScoreRepository scores) {
//        return (args) -> {
//
//
//
//
//        };
//    }
//}
//
//@Configuration
//class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {
//
//    @Autowired
//    PlayerRepository playerRepository;
//
//    @Bean
//    UserDetailsService UserDetailsService(){
//
//        return new UserDetailsService() {
//
//            @Override
//            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//                List<Player> players = playerRepository.findByUsername(username);
//                if (!players.isEmpty()) {
//                    Player player = players.get(0);
//                    return new User(player.getUsername(), player.getPassword(),
//                            AuthorityUtils.createAuthorityList("USER", "PLAYER" + player.getId()));
//                } else {
//                    throw new UsernameNotFoundException("Unknown user: " + username);
//                }
//            }
//        };
//
//    }
//}
//
//@EnableWebSecurity
//@Configuration
//class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http.authorizeRequests()
//                .antMatchers("/rest/**", "/api/game_view/**").authenticated();
//
//        http.formLogin().loginPage("/api/login");
//
//        http.logout().logoutUrl("/api/logout");
//
//        http.csrf().disable();
//
//        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
//
//        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));
//
//        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
//
//        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
//
//    }
//
//    private void clearAuthenticationAttributes(HttpServletRequest request) {
//
//        HttpSession session = request.getSession(false);
//
//        if (session != null) {
//            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
//        }
//    }
//}
