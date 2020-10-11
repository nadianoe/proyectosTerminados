package com.codeoftheweb.salvo;

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
import com.codeoftheweb.salvo.entidades.*;
import com.codeoftheweb.salvo.repositorios.*;
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
public class SalvoApplication {
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public static final long HOUR = 3600*1000;
            
    public static void main(String[] args) {
        SpringApplication.run(SalvoApplication.class, args);
    }

    @RequestMapping("/games")
    public String games() {
        return "games.html";
    }
/*
    @RequestMapping("/game_gp={gamePlayerId}")
    public String game(@PathVariable(value = "gamePlayerId") int gamePlayerId) {
        return"game.html";
    }

    jkhajskdfklja laksjdflkjaskdf laksdlfkjalksdf
*/ 
    @RequestMapping("/{id}")
    public String game() {
        return "game.html";
    }
/*
    @RequestMapping
    public String index() {
        return "index.html";
    }
*/   
    @Bean
    public CommandLineRunner initData(PlayerRepository players,
                                      GameRepository games,
                                      GamePlayerRepository gamePlayers,
                                      ShipRepository ships,
                                      SalvoRepository salvoes,
                                      ScoreRepository scores) {
        return (args) -> {

            String password = passwordEncoder.encode("24");
            Player player1 = new Player("j.bauer@ctu.gov", password); 
            
            password = passwordEncoder.encode("42");
            Player player2 = new Player("c.obrian@ctu.gov", password); 
            
            password = passwordEncoder.encode("kb");
            Player player3 = new Player("kim_bauer@gmail.com", password);
            
            password = passwordEncoder.encode("mole");
            Player player4 = new Player("t.almeida@ctu.gov", password);
            
            players.save(player1);
            players.save(player2); 
            players.save(player3);
            players.save(player4);
            
            Date date = new Date();

            Game game1 = new Game(date);
            games.save(game1);

            Game game2 = new Game(new Date(date.getTime() +  HOUR));
            games.save(game2);

            Game game3 = new Game(new Date(date.getTime() + 2 * HOUR));
            games.save(game3);

            Game game4 = new Game(new Date(date.getTime() + 3 * HOUR));
            games.save(game4);

            Game game5 = new Game(new Date(date.getTime() + 4 * HOUR));
            games.save(game5);

            Game game6 = new Game(new Date(date.getTime() + 5 * HOUR));
            games.save(game6);

            Game game7 = new Game(new Date(date.getTime() + 6 * HOUR));
            games.save(game7);

            Game game8 = new Game(new Date(date.getTime() + 7 * HOUR));
            games.save(game8);


            Date date1 = new Date();
            Date date2 = new Date(date1.getTime() + HOUR / 2);
            Date date3 = new Date(date2.getTime() + HOUR / 2);
            Date date4 = new Date(date3.getTime() + HOUR / 2);
            Date date5 = new Date(date4.getTime() + HOUR / 2);
            Date date6 = new Date(date5.getTime() + HOUR / 2);
            Date date7 = new Date(date6.getTime() + HOUR / 2);
            Date date8 = new Date(date7.getTime() + HOUR / 2);
            Date date9 = new Date(date8.getTime() + HOUR / 2);
            Date date10 = new Date(date9.getTime() + HOUR / 2);
            Date date11 = new Date(date10.getTime() + HOUR / 2);
            Date date12 = new Date(date11.getTime() + HOUR / 2);
            Date date13 = new Date(date12.getTime() + HOUR / 2);
            Date date14 = new Date(date13.getTime() + HOUR / 2);

            GamePlayer gamePlayer1 = new GamePlayer(date1, player1, game1);
            GamePlayer gamePlayer2 = new GamePlayer(date2, player2, game1);

            GamePlayer gamePlayer3 = new GamePlayer(date3, player1, game2);
            GamePlayer gamePlayer4 = new GamePlayer(date4, player2, game2);

            GamePlayer gamePlayer5 = new GamePlayer(date5, player2, game3);
            GamePlayer gamePlayer6 = new GamePlayer(date6, player4, game3);

            GamePlayer gamePlayer7 = new GamePlayer(date7, player2, game4);
            GamePlayer gamePlayer8 = new GamePlayer(date8, player1, game4);

            GamePlayer gamePlayer9 = new GamePlayer(date9, player4, game5);
            GamePlayer gamePlayer10 = new GamePlayer(date10, player1, game5);

            GamePlayer gamePlayer11 = new GamePlayer(date11, player3, game6);
            //GamePlayer gamePlayer12 = new GamePlayer(date12, player4, game6);

            GamePlayer gamePlayer12 = new GamePlayer(date12, player4, game7);
            //GamePlayer gamePlayer14 = new GamePlayer();

            GamePlayer gamePlayer13 = new GamePlayer(date13, player3, game8);
            GamePlayer gamePlayer14 = new GamePlayer(date14, player4, game8);

            gamePlayers.save(gamePlayer1);
            gamePlayers.save(gamePlayer2);
            gamePlayers.save(gamePlayer3);
            gamePlayers.save(gamePlayer4);
            gamePlayers.save(gamePlayer5);
            gamePlayers.save(gamePlayer6);
            gamePlayers.save(gamePlayer7);
            gamePlayers.save(gamePlayer8); 
            gamePlayers.save(gamePlayer9); 
            gamePlayers.save(gamePlayer10);
            gamePlayers.save(gamePlayer11); 
            gamePlayers.save(gamePlayer12);
            gamePlayers.save(gamePlayer13);
            gamePlayers.save(gamePlayer14);


            List<String> locations = new ArrayList<>();

            locations = Arrays.asList("H2", "H3", "H4");
            Ship ship1 = new Ship(gamePlayer1,ShipType.DESTROYER,locations);
            ships.save(ship1);
            gamePlayer1.addShip(ship1);

            locations = Arrays.asList("E1", "F1", "G1");
            Ship ship2 = new Ship(gamePlayer1,ShipType.SUBMARINE,locations);
            ships.save(ship2);
            gamePlayer1.addShip(ship2);

            locations = Arrays.asList("B4", "B5");
            Ship ship3 = new Ship(gamePlayer1,ShipType.PATROLBOAT,locations);
            ships.save(ship3);
            gamePlayer1.addShip(ship3);

            locations = Arrays.asList("B5", "C5", "D5");
            Ship ship4 = new Ship(gamePlayer2,ShipType.DESTROYER,locations);
            ships.save(ship4);
            gamePlayer2.addShip(ship4);

            locations = Arrays.asList("F1", "F2");
            Ship ship5 = new Ship(gamePlayer2,ShipType.PATROLBOAT,locations);
            ships.save(ship5);
            gamePlayer2.addShip(ship5);




            locations = Arrays.asList("B5", "C5", "D5");
            Ship ship6 = new Ship(gamePlayer3,ShipType.DESTROYER,locations);
            ships.save(ship6);
            gamePlayer3.addShip(ship6);

            locations = Arrays.asList("C6", "C7");
            Ship ship7 = new Ship(gamePlayer3,ShipType.PATROLBOAT,locations);
            ships.save(ship7);
            gamePlayer3.addShip(ship7);

            locations = Arrays.asList("A2", "A3", "A4");
            Ship ship8 = new Ship(gamePlayer4,ShipType.SUBMARINE,locations);
            ships.save(ship8);
            gamePlayer4.addShip(ship8);

            locations = Arrays.asList("G6", "H6");
            Ship ship9 = new Ship(gamePlayer4,ShipType.PATROLBOAT,locations);
            ships.save(ship9);
            gamePlayer4.addShip(ship9);




            locations = Arrays.asList("B5", "C5", "D5");
            Ship ship10 = new Ship(gamePlayer5,ShipType.DESTROYER,locations);
            ships.save(ship10);
            gamePlayer5.addShip(ship10);

            locations = Arrays.asList("C6", "C7");
            Ship ship11 = new Ship(gamePlayer5,ShipType.PATROLBOAT,locations);
            ships.save(ship11);
            gamePlayer5.addShip(ship11);

            locations = Arrays.asList("A2", "A3", "A4");
            Ship ship12 = new Ship(gamePlayer6,ShipType.SUBMARINE,locations);
            ships.save(ship12);
            gamePlayer6.addShip(ship12);

            locations = Arrays.asList("G6", "H6");
            Ship ship13 = new Ship(gamePlayer6,ShipType.PATROLBOAT,locations);
            ships.save(ship13);
            gamePlayer6.addShip(ship13);




            locations = Arrays.asList("B5", "C5", "D5");
            Ship ship14 = new Ship(gamePlayer7,ShipType.DESTROYER,locations);
            ships.save(ship14);
            gamePlayer7.addShip(ship14);

            locations = Arrays.asList("C6", "C7");
            Ship ship15 = new Ship(gamePlayer7,ShipType.PATROLBOAT,locations);
            ships.save(ship15);
            gamePlayer7.addShip(ship15);

            locations = Arrays.asList("A2", "A3", "A4");
            Ship ship16 = new Ship(gamePlayer8,ShipType.SUBMARINE,locations);
            ships.save(ship16);
            gamePlayer8.addShip(ship16);

            locations = Arrays.asList("G6", "H6");
            Ship ship17 = new Ship(gamePlayer8,ShipType.PATROLBOAT,locations);
            ships.save(ship17);
            gamePlayer8.addShip(ship17);




            locations = Arrays.asList("B5", "C5", "D5");
            Ship ship18 = new Ship(gamePlayer9,ShipType.DESTROYER,locations);
            ships.save(ship18);
            gamePlayer9.addShip(ship18);

            locations = Arrays.asList("C6", "C7");
            Ship ship19 = new Ship(gamePlayer9,ShipType.PATROLBOAT,locations);
            ships.save(ship19);
            gamePlayer9.addShip(ship19);

            locations = Arrays.asList("A2", "A3", "A4");
            Ship ship20 = new Ship(gamePlayer10,ShipType.SUBMARINE,locations);
            ships.save(ship20);
            gamePlayer10.addShip(ship20);

            locations = Arrays.asList("G6", "H6");
            Ship ship21 = new Ship(gamePlayer10,ShipType.PATROLBOAT,locations);
            ships.save(ship21);
            gamePlayer10.addShip(ship21);




            locations = Arrays.asList("B5", "C5", "D5");
            Ship ship22 = new Ship(gamePlayer11,ShipType.DESTROYER,locations);
            ships.save(ship22);
            gamePlayer11.addShip(ship22);

            locations = Arrays.asList("C6", "C7");
            Ship ship23 = new Ship(gamePlayer11,ShipType.PATROLBOAT,locations);
            ships.save(ship23);
            gamePlayer11.addShip(ship23);



            locations = Arrays.asList("B5", "C5", "D5");
            Ship ship24 = new Ship(gamePlayer13,ShipType.DESTROYER,locations);
            ships.save(ship24);
            gamePlayer13.addShip(ship24);

            locations = Arrays.asList("C6", "C7");
            Ship ship25 = new Ship(gamePlayer13,ShipType.PATROLBOAT,locations);
            ships.save(ship25);
            gamePlayer13.addShip(ship25);

            locations = Arrays.asList("A2", "A3", "A4");
            Ship ship26 = new Ship(gamePlayer14,ShipType.SUBMARINE,locations);
            ships.save(ship26);
            gamePlayer14.addShip(ship26);

            locations = Arrays.asList("G6", "H6");
            Ship ship27 = new Ship(gamePlayer14,ShipType.PATROLBOAT,locations);
            ships.save(ship27);
            gamePlayer14.addShip(ship27);



            locations = Arrays.asList("B5", "C5", "F1");
            Salvo salvo1 = new Salvo(gamePlayer1,1,locations);
            salvoes.save(salvo1);
            gamePlayer1.addSalvo(salvo1);

            locations = Arrays.asList("B4", "B5", "B6");
            Salvo salvo2 = new Salvo(gamePlayer2,1,locations);
            salvoes.save(salvo2);
            gamePlayer2.addSalvo(salvo2);

            locations = Arrays.asList("F2", "D5");
            Salvo salvo3 = new Salvo(gamePlayer1,2,locations);
            salvoes.save(salvo3);
            gamePlayer1.addSalvo(salvo3);

            locations = Arrays.asList("E1", "H3", "A2");
            Salvo salvo4 = new Salvo(gamePlayer2,2,locations);
            salvoes.save(salvo4);
            gamePlayer2.addSalvo(salvo4);



            locations = Arrays.asList("A2", "A4", "G6");
            Salvo salvo5 = new Salvo(gamePlayer3,1,locations);
            salvoes.save(salvo5);
            gamePlayer3.addSalvo(salvo5);

            locations = Arrays.asList("B5", "D5", "C7");
            Salvo salvo6 = new Salvo(gamePlayer4,1,locations);
            salvoes.save(salvo6);
            gamePlayer4.addSalvo(salvo6);

            locations = Arrays.asList("A3", "H6");
            Salvo salvo7 = new Salvo(gamePlayer3,2,locations);
            salvoes.save(salvo7);
            gamePlayer3.addSalvo(salvo7);

            locations = Arrays.asList("C5", "C6");
            Salvo salvo8 = new Salvo(gamePlayer4,2,locations);
            salvoes.save(salvo8);
            gamePlayer4.addSalvo(salvo8);



            locations = Arrays.asList("G6", "H6", "A4");
            Salvo salvo9 = new Salvo(gamePlayer5,1,locations);
            salvoes.save(salvo9);
            gamePlayer5.addSalvo(salvo9);

            locations = Arrays.asList("H1", "H2", "H3");
            Salvo salvo10 = new Salvo(gamePlayer6,1,locations);
            salvoes.save(salvo10);
            gamePlayer6.addSalvo(salvo10);

            locations = Arrays.asList("A2", "A3", "D8");
            Salvo salvo11 = new Salvo(gamePlayer5,2,locations);
            salvoes.save(salvo11);
            gamePlayer5.addSalvo(salvo11);

            locations = Arrays.asList("E1", "F2", "G3");
            Salvo salvo12 = new Salvo(gamePlayer6,2,locations);
            salvoes.save(salvo12);
            gamePlayer6.addSalvo(salvo12);



            locations = Arrays.asList("A3", "A4", "F7");
            Salvo salvo13 = new Salvo(gamePlayer7,1,locations);
            salvoes.save(salvo13);
            gamePlayer7.addSalvo(salvo13);

            locations = Arrays.asList("B5", "C6", "H1");
            Salvo salvo14 = new Salvo(gamePlayer8,1,locations);
            salvoes.save(salvo14);
            gamePlayer8.addSalvo(salvo14);

            locations = Arrays.asList("A2", "G6", "H6");
            Salvo salvo15 = new Salvo(gamePlayer7,2,locations);
            salvoes.save(salvo15);
            gamePlayer7.addSalvo(salvo15);

            locations = Arrays.asList("C5", "C7", "D5");
            Salvo salvo16 = new Salvo(gamePlayer8,2,locations);
            salvoes.save(salvo16);
            gamePlayer8.addSalvo(salvo16);




            locations = Arrays.asList("A1", "A2", "A3");
            Salvo salvo17 = new Salvo(gamePlayer9,1,locations);
            salvoes.save(salvo17);
            gamePlayer9.addSalvo(salvo17);

            locations = Arrays.asList("B5", "B6", "C7");
            Salvo salvo18 = new Salvo(gamePlayer10,1,locations);
            salvoes.save(salvo18);
            gamePlayer10.addSalvo(salvo18);

            locations = Arrays.asList("G6", "G7", "G8");
            Salvo salvo19 = new Salvo(gamePlayer9,2,locations);
            salvoes.save(salvo19);
            gamePlayer9.addSalvo(salvo19);

            locations = Arrays.asList("C6", "D6", "E6");
            Salvo salvo20 = new Salvo(gamePlayer10,2,locations);
            salvoes.save(salvo20);
            gamePlayer10.addSalvo(salvo20);

            locations = Arrays.asList("H1", "H8");
            Salvo salvo21 = new Salvo(gamePlayer10,3,locations);
            salvoes.save(salvo21);
            gamePlayer10.addSalvo(salvo21);




            Score score1 = new Score(6,player1,game1,new Date(date1.getTime() +  HOUR));
            Score score2 = new Score(4,player2,game1,new Date(date1.getTime() +  HOUR));
            scores.save(score1);
            scores.save(score2);

            Score score3 = new Score(5,player1,game2,new Date(date3.getTime() +  HOUR));
            Score score4 = new Score(7,player2,game2,new Date(date3.getTime() +  HOUR));
            scores.save(score3);
            scores.save(score4);


            Score score5 = new Score(2,player2,game3,new Date(date5.getTime() +  HOUR));
            Score score6 = new Score(3,player4,game3,new Date(date5.getTime() +  HOUR));
            scores.save(score5);
            scores.save(score6);


            Score score7 = new Score(4,player2,game4,new Date(date7.getTime() +  HOUR));
            Score score8 = new Score(4,player1,game4,new Date(date7.getTime() +  HOUR));
            scores.save(score7);
            scores.save(score8);


            Score score9 = new Score(4,player4,game5,new Date(date9.getTime() +  HOUR));
            Score score10 = new Score(1,player1,game5,new Date(date9.getTime() +  HOUR));
            scores.save(score9);
            scores.save(score10);

            Score score11 = new Score(6,player3,game8,new Date(date13.getTime() +  HOUR));
            Score score12 = new Score(2,player4,game8,new Date(date13.getTime() +  HOUR));
            scores.save(score11);
            scores.save(score12);



        };
    }
}

@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {
    
    @Autowired
    PlayerRepository playerRepository;
    
    @Bean
    UserDetailsService UserDetailsService(){

        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                List<Player> players = playerRepository.findByUsername(username);
                if (!players.isEmpty()) {
                    Player player = players.get(0);
                    return new User(player.getUsername(), player.getPassword(),
                            AuthorityUtils.createAuthorityList("USER", "PLAYER" + player.getId()));
                } else {
                    throw new UsernameNotFoundException("Unknown user: " + username);
                }
            }
        };

    }
}

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    
        http.authorizeRequests()
                .antMatchers("/rest/**", "/api/game_view/**").authenticated();

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