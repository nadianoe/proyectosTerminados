//package com.codeoftheweb.salvo;
//
//import com.codeoftheweb.salvo.entidades.*;
//import com.codeoftheweb.salvo.repositorios.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//import java.util.*;
//
//
//@RequestMapping("/api")
//@RestController
//public class SalvoController {
//
//    @RequestMapping(path = "/games", method = RequestMethod.GET)
//    public ResponseEntity<Object> obtenerTodaLaInformacionDelJuego() {
//
//        Map<String, Object> allInfo = new HashMap<>();
//
//        return new ResponseEntity<>(allInfo, HttpStatus.OK);
//    }
//
//
//    @RequestMapping(path = "/game_view/{gamePlayerId}", method = RequestMethod.GET)
//    public ResponseEntity<Object> getInfoOfAGame(@PathVariable long gamePlayerId) {
//
//        Map<String, Object> infoGame = new HashMap<String, Object>();
//        Map<String, Object> infoResponse = new HashMap<String, Object>();
//
//        return new ResponseEntity<>(infoResponse, HttpStatus.UNAUTHORIZED);
//
//    }
//
//
//    @RequestMapping(value = "/games/players/{gamePlayerId}/ships", method = RequestMethod.GET)
//    public Map<String, Object> getShips(@PathVariable long gamePlayerId) {
//
//        Map<String, Object> ships = new HashMap<>();
//
//        if (accesoABase.contieJugadonConID(gamePlayerId)) {
//
//            GamePlayer gamePlayer = gamePlayerRepository.findById(gamePlayerId);
//            List<Ship> shipsList = new ArrayList(gamePlayer.getShips());
//            ships.put("shipsGamePlayer" + gamePlayerId, shipsList);
//
//        }
//
//        return ships;
//
//    }
//
//
//    @RequestMapping(value = "/games/players/{gamePlayerId}/ships", method = RequestMethod.POST)
//    public ResponseEntity<Object> addShips(@PathVariable long gamePlayerId,
//                                           @RequestParam String shipsJsonString) {
//
//        /* deserializar el string "shipsJsonString", es decir,
//         convertirlos a objetos java */
//
//        Map<String, Object> infoResponse = new HashMap<>();
//
//        if (accesoABase.contieneJugadorConId(gamePlayerId)) {
//
//            if (!gamePlayer.tieneOcupadosLosLugares(ships)) {
//                accesoABase.insertarBarcosAJugador(gamePlayerId,ships);
//                response = new ResponseEntity<>(infoResponse, HttpStatus.CREATED);
//            } else {
//                response = new ResponseEntity<>(infoResponse, HttpStatus.CONFLICT);
//
//            }
//
//        } else {
//
//            response = new ResponseEntity<>(infoResponse, HttpStatus.UNAUTHORIZED);
//        }
//
//        return response;
//
//    }
//
//
//    public List<Map<String, Object>> obtenerInfoJuegos() {
//
//        List<Map<String, Object>> informacionDeCadaJuego = new ArrayList<>();
//
//        for (Game game : accesoABase.obtenerJuegos()) {
//
//            Map<String, Object> infoDeUnJuego = new HashMap<String, Object>();
//
//            infoGame.put("id", game.getId());
//            infoGame.put("created", game.getCreationDate().getTime());
//
//            List<Map<String, Object>> gamePlayersList = new ArrayList<>();
//
//            for (GamePlayer actualGamePlayer : game.obtenerJugadores()) {
//
//                Map<String, Object> gamePlayer = new HashMap<String, Object>();
//                gamePlayer.put("id", actualGamePlayer.getId());
//                gamePlayersList.add(gamePlayer);
//
//            }
//
//
//            infoGame.put("gamePlayers", gamePlayersList);
//            infoGamesList.add(infoGame);
//
//        }
//
//        return infoGamesList;
//    }
//}