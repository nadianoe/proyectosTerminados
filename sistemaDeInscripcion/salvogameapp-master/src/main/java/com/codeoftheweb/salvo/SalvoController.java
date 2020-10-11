package com.codeoftheweb.salvo;

import com.codeoftheweb.salvo.entidades.*;
import com.codeoftheweb.salvo.repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
/* kjhkjhk kjhkjhjkb kjhkjh  adfsdfasdf*/

import java.util.*;

//@Controller
@RequestMapping("/api")
@RestController
public class SalvoController {

	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private GamePlayerRepository gamePlayerRepository;

    @Autowired
    private ShipRepository shipRepository;

    @Autowired
    private SalvoRepository salvoRepository;

    @Autowired
    private ScoreRepository scoreRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

 
    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<Object> singUpPlayer(
                                        @RequestParam String username, 
                                        @RequestParam String password,
                                        Authentication auth) {
        
        Map<String,Object> infoResponse = new HashMap<>();
        
        if (isGuest(auth)){

        if (playerRepository.findByUsername(username).size() !=  0) {
          infoResponse.put("error", "username in use");
          return new ResponseEntity<>(infoResponse, HttpStatus.FORBIDDEN);
        }

        playerRepository.save(new Player(username, passwordEncoder.encode(password)));
        infoResponse.put("success", "username created");
        infoResponse.put("username of new player", username);
        }
        return new ResponseEntity<>(infoResponse, HttpStatus.CREATED);
        
    }
    
    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }

    @RequestMapping(path = "/game_view/{gamePlayerId}", method = RequestMethod.GET)
	public ResponseEntity<Object> getInfoOfAGame(
                                        @PathVariable long gamePlayerId,
                                        Authentication auth) {
	    
	    Map<String,Object> infoGame = new HashMap<String,Object>();
        Map<String,Object> infoResponse = new HashMap<String,Object>();
        
        GamePlayer gamePlayer = gamePlayerRepository.findById(gamePlayerId);
        GamePlayer opponent = gamePlayer.getOpponent();
        Game game =  gamePlayer.getGame();

        String usernameOpponent = opponent.getUsername();

        String username = auth.getName();
        String usernameGamePlayer = gamePlayer.getPlayer().getUsername();

        if (game.isGamePlayer(username) && 
            gamePlayer.getId() == gamePlayerId &&
            username == usernameGamePlayer){
            
            infoGame.put("id",game.getId());
            infoGame.put("created", game.getCreationDate().getTime());

            List<Map<String,Object>> gamePlayersList = new ArrayList<>();
            List<Map<String,Object>> infoSalvoes = new ArrayList<>();

            for (GamePlayer actualGamePlayer : game.getGamePlayers()) {

                Map<String,Object> gamePlayerInfo = new HashMap<String,Object>();
                Map<String,Object> player = new HashMap<String,Object>();

                player.put("id", actualGamePlayer.getPlayer().getId());
                player.put("username", actualGamePlayer.getPlayer().getUsername());

                gamePlayerInfo.put("id", actualGamePlayer.getId());
                gamePlayerInfo.put("player", player);

                gamePlayersList.add(gamePlayerInfo);

	            for (Salvo actualSalvo : actualGamePlayer.getSalvoes()) {
	                Map<String,Object> salvo = new HashMap<String,Object>();
	                salvo.put("turn",actualSalvo.getTurn());
	                salvo.put("gamePlayerId",actualSalvo.getGamePlayer().getId());
	                salvo.put("locations",actualSalvo.getLocations());
	                infoSalvoes.add(salvo);
	            }
            }

            List<Map<String,Object>> infoShips = new ArrayList<>();

            for (Ship actualShip : gamePlayer.getShips()) {
                Map<String,Object> ship = new HashMap<String,Object>();
                ship.put("type",actualShip.getType());
                ship.put("locations",actualShip.getLocations());
                infoShips.add(ship);
            }

            infoGame.put("gamePlayers", gamePlayersList);
            infoGame.put("ships", infoShips);
            infoGame.put("salvoes", infoSalvoes);

            Map<String,Object> infoGameState = new HashMap<String,Object>();
            
            infoGameState.put("allShipsArePlaced", game.allShipsOfTheGameArePlaced());
            infoGameState.put("jugadorDeTurno", game.getGamePlayerDeTurno().getUsername());
            infoGameState.put("placingShips", gamePlayer.isPlacingShips());
            infoGameState.put("myShipsPlaced", gamePlayer.getShipsPlaced());
            infoGameState.put("jugadorQueDisparaPrimero", game.getJugadorQueDisparaPrimero().getUsername());
            infoGameState.put("theGameIsOver", game.isAGameOver());
            infoGameState.put("ganador", game.getWinner().getUsername());

            Map<String,Object> infoHits = new HashMap<String,Object>();

            infoHits.put("toMe",gamePlayer.getInfoShipsHited(opponent));
            infoHits.put("hitLocationsToMe",gamePlayer.getAllHitLocations(opponent));

            infoHits.put("toOpponent",opponent.getInfoShipsHited(gamePlayer));
            infoHits.put("hitLocationsToOpponent",opponent.getAllHitLocations(gamePlayer));

            Map<String,Object> infoSinks = new HashMap<String,Object>();

            infoSinks.put("toMe",gamePlayer.getBarcosHundidosPor(opponent));
            infoSinks.put("toOpponent",opponent.getBarcosHundidosPor(gamePlayer));

            infoGame.put("hits",infoHits);
            infoGame.put("sinks",infoSinks);

            infoResponse.put("userLoggedIn", username);
            infoResponse.put("userOpponent", usernameOpponent);
            infoResponse.put("infoGame", infoGame);
            infoResponse.put("infoGameState", infoGameState);

            return new ResponseEntity<>(infoResponse, HttpStatus.OK);

        }else{

            infoResponse.put("error","usuario no autorizado");
            return new ResponseEntity<>(infoResponse, HttpStatus.UNAUTHORIZED);
        }
   }
    
    public List<Map<String,Object>> getGames() {

        List<Map<String,Object>> infoGamesList = new ArrayList<>();

        for (Game game : gameRepository.findAll() ) {

            Map<String,Object> infoGame = new HashMap<String,Object>();

            infoGame.put("id",game.getId());
            infoGame.put("created", game.getCreationDate().getTime());

            List<Map<String,Object>> gamePlayersList = new ArrayList<>();
            List<Map<String,Object>> infoScoresList = new ArrayList<>();


            for (GamePlayer actualGamePlayer : game.getGamePlayers()) {

                Map<String,Object> gamePlayer = new HashMap<String,Object>();
                Map<String,Object> player = new HashMap<String,Object>();

                player.put("id", actualGamePlayer.getPlayer().getId());
                player.put("username", actualGamePlayer.getPlayer().getUsername());

                gamePlayer.put("id", actualGamePlayer.getId());
                gamePlayer.put("player", player);

                gamePlayersList.add(gamePlayer);

            }

            for (Score actualScore : game.getScores()) {
                Map<String,Object> score = new HashMap<String,Object>();
                score.put("playerId", actualScore.getPlayer().getId());
                score.put("score", actualScore.getScore());
                infoScoresList.add(score);
            }

            infoGame.put("gamePlayers", gamePlayersList);
            infoGame.put("scores", infoScoresList);
            

            infoGamesList.add(infoGame);

        }

        return infoGamesList;
    }

    @RequestMapping(path = "/games", method = RequestMethod.GET)
    public Map<String,Object> getAllInfo(Authentication auth) {

        Map<String,Object> allInfo = new HashMap<>();
        List<Map<String,Object>> infoGames = getGames();

        if (auth != null) { 

            List<Player> playerList = playerRepository.findByUsername(auth.getName());
            Player actualUser = playerList.get(0);

            Map<String, Object> infoActualUser = new HashMap<>();
            infoActualUser.put("id", actualUser.getId());
            infoActualUser.put("username", actualUser.getUsername());

            allInfo.put("player", infoActualUser);
            allInfo.put("games", infoGames);

        }else {

            allInfo.put("player", null);
            allInfo.put("games", infoGames);
        }

        
        return allInfo;
    }
 
    @RequestMapping(path = "/games", method = RequestMethod.POST)
    public ResponseEntity<Object> addNewGame(Authentication auth) {
        
        Map<String,Object> infoResponse = new HashMap<>();
        List<Player> players = playerRepository.findByUsername(auth.getName());
        
        if(auth.isAuthenticated() && players.size() != 0){
            
            Player playerLoggedIn = players.get(0);
            
            Date date = new Date();
            
            Game newGame = new Game(date);
            gameRepository.save(newGame);
            
            GamePlayer gamePlayer = new GamePlayer(date, playerLoggedIn, newGame);
            gamePlayerRepository.save(gamePlayer);
            
            List<Player> playerList = playerRepository.findByUsername(auth.getName());
            Player actualUser = playerList.get(0);

            Map<String, Object> infoActualUser = new HashMap<>();
            infoActualUser.put("id", actualUser.getId());
            infoActualUser.put("username", actualUser.getUsername());

            infoResponse.put("player", infoActualUser);
            infoResponse.put("games", getGames());
            
            infoResponse.put("gpid", gamePlayer.getId());
            
            return new ResponseEntity<>(infoResponse, HttpStatus.CREATED);
            
        }else{
            
            infoResponse.put("error", "player no autenticated");
            return new ResponseEntity<>(infoResponse, HttpStatus.UNAUTHORIZED);
            
        }
    }
        
    @RequestMapping(path = "/games/{nn}/players", method = RequestMethod.GET)
    public Map<String,Object> getGamePlayersOfGame(@PathVariable 
                                                   @RequestParam long nn) {
        
        Map<String,Object> gamePlayers = new HashMap<>();
        
        if (gameRepository.findById(nn) != null){
            
            Game game = gameRepository.findById(nn);
            List<GamePlayer> gamePlayersList = new ArrayList(game.getGamePlayers());
            gamePlayers.put("playersOfGame" + game.getId(), gamePlayersList);
            
        }
        
        return gamePlayers;
        
    }
    
    @RequestMapping(path = "/games/{gameIdnn}/players", method = RequestMethod.POST)
    public ResponseEntity<Object> addGamePlayerToGame(@PathVariable
                                                      @RequestParam
                                                      long gameIdnn,
                                                      Authentication auth) {
        
        Map<String,Object> infoResponse = new HashMap<>();
        ResponseEntity<Object> response = null;
        
        if (gameRepository.findById(gameIdnn) != null){
            
            Game actualGame = gameRepository.findById(gameIdnn);
            List gamePlayersList = new ArrayList(actualGame.getGamePlayers());
            
            if(gamePlayersList.size() == 1){
                
                if(!isGuest(auth)){
                 
                List<Player> players = playerRepository.findByUsername(auth.getName());
                
                Player playerLoggedIn = players.get(0);
                
                Date date = new Date();
                
                GamePlayer newGamePlayer = new GamePlayer(date, playerLoggedIn, actualGame);
                gamePlayerRepository.save(newGamePlayer);
                
                infoResponse.put("success","game player aggregate");
                infoResponse.put("gpid", newGamePlayer.getId());
                response = new ResponseEntity<>(infoResponse, HttpStatus.CREATED);
                
                }else{
                    
                    infoResponse.put("error","user logged out");
                    response = new ResponseEntity<>(infoResponse, HttpStatus.UNAUTHORIZED);
                }
                
            }else{
                
                infoResponse.put("error","game is full");
                response = new ResponseEntity<>(infoResponse, HttpStatus.FORBIDDEN);
                
            }
            
        }else{
            
            infoResponse.put("error","no such game");
            response = new ResponseEntity<>(infoResponse, HttpStatus.FORBIDDEN);
        }
        
        return response;
    
    }
    
    
    
    @RequestMapping(value="/games/players/{gamePlayerId}/ships", method=RequestMethod.POST)
    public ResponseEntity<Object> addShips(@PathVariable long gamePlayerId, @RequestBody
    List<Ship> ships, Authentication auth) {
        
        Map<String,Object> infoResponse = new HashMap<>();
        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        GamePlayer gamePlayer = gamePlayerRepository.findById(gamePlayerId);
        
        if (gamePlayer != null && !isGuest(auth) &&
            gamePlayer.getId() == gamePlayerId){
            
            if (!gamePlayer.thereIsOccupiedLocation(ships)){
            
                for (Ship ship : ships){
                    
                    ship.setGamePlayer(gamePlayer);
                    gamePlayer.addShip(ship);
                    shipRepository.save(ship);
                    
                }
                
                infoResponse.put("success","ships agregados");
                response = new ResponseEntity<>(infoResponse,HttpStatus.CREATED);
                
            }else{
                
                infoResponse.put("error", "game player has ships placed");
                response = new ResponseEntity<>(infoResponse, HttpStatus.FORBIDDEN);
                
            }
                
        }else{
            
            response = new ResponseEntity<>(infoResponse, HttpStatus.UNAUTHORIZED);
        }
        
        return response;
     
    }
 
    
    
    @RequestMapping(value="/games/players/{gamePlayerId}/ships", method=RequestMethod.GET)
    public Map<String,Object> getShips(@PathVariable long gamePlayerId) {

        Map<String,Object> ships = new HashMap<>();
        
        if (gamePlayerRepository.findById(gamePlayerId) != null){
            
            GamePlayer gamePlayer = gamePlayerRepository.findById(gamePlayerId);
            List<Ship> shipsList = new ArrayList(gamePlayer.getShips());
            ships.put("shipsGamePlayer" + gamePlayerId, shipsList);
            
        }
  
        return ships;
     
    }

    @RequestMapping(value="/games/players/{gamePlayerId}/salvoes", method=RequestMethod.POST)
    public ResponseEntity<Object> addSalvo(@PathVariable long gamePlayerId, @RequestBody
    Salvo salvo, Authentication auth) {
        
        Map<String,Object> infoResponse = new HashMap<>();
        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        GamePlayer gamePlayer = gamePlayerRepository.findById(gamePlayerId);
        GamePlayer opponent = gamePlayer.getOpponent();
        Game game = gamePlayer.getGame();
        String username = gamePlayer.getUsername();

        if (gamePlayer != null && !gamePlayer.isSalvoWithSameTurn(salvo) &&
            salvo.getNumberOfShots() == 5 && !game.isAGameOver() &&
            game.getGamePlayerDeTurno().getUsername() == username ){

            if (!isGuest(auth) && gamePlayer.getId() == gamePlayerId){

                gamePlayer.addSalvo(salvo);
                salvo.setGamePlayer(gamePlayer);
                salvoRepository.save(salvo);

                infoResponse.put("success","sea agregó salvo");
                response = new ResponseEntity<>(infoResponse, HttpStatus.CREATED);

            }else{
            
                String mensaje = "if not logged in or not game player nn";
                response = new ResponseEntity<>(infoResponse, HttpStatus.UNAUTHORIZED);
            
            }

        }else{

              String mensaje = "no such game player or ";
              mensaje += "salvo already fired for this turn or ";
              mensaje += "too many shots in salvo or ";
              mensaje += "is a game over.";

              infoResponse.put("error", mensaje);
              response = new ResponseEntity<>(infoResponse, HttpStatus.FORBIDDEN);

        }

        return response;
     
    }

     @RequestMapping(value="/games/players/{gamePlayerId}/salvoes", method=RequestMethod.GET)
    public Map<String,Object> getSalvoes(@PathVariable long gamePlayerId) {

        Map<String,Object> salvoes = new HashMap<>();
        GamePlayer gamePlayer = gamePlayerRepository.findById(gamePlayerId);
        
        if (gamePlayer != null){
            
            List<Ship> salvoesList = new ArrayList(gamePlayer.getSalvoes());
            salvoes.put("salvoesGamePlayer", salvoesList);
            
        }
  
        return salvoes;
     
    }
    
    
    /*
    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<Object> singUpPlayer(
    @RequestParam String username, @RequestParam String password) {
        
        Map<String,Object> infoResponse = new HashMap<>();
 
        if (username.isEmpty() || password.isEmpty()) {
          return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
 
        if (playerRepository.findByUsername(username).size() !=  0) {
          infoResponse.put("error", "username in use");
          return new ResponseEntity<>(infoResponse, HttpStatus.FORBIDDEN);
        }
 
        playerRepository.save(new Player(username, passwordEncoder.encode(password)));
        infoResponse.put("success", "username created");
        infoResponse.put("username of new player", username);
        return new ResponseEntity<>(infoResponse, HttpStatus.CREATED);
    }
    */
    

    


}



