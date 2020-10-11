package com.codeoftheweb.salvo.entidades;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private Date creationDate;

    @OneToMany(mappedBy="game", fetch=FetchType.EAGER)
    private Set<GamePlayer> gamePlayers;

    @OneToMany(mappedBy="game", fetch=FetchType.EAGER)
    private Set<Score> scores;

    public Game() {
        this.creationDate = new Date(2019,9,12);
        this.gamePlayers = new HashSet<>();
        this.scores = new HashSet<>(); 
    }

    
    public Game(Date creationDate) {
        this.creationDate = creationDate;
        this.gamePlayers = new HashSet<>();
        this.scores = new HashSet<>();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Long getId() {
        return id;
    }

    public Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public Set<Score> getScores() {
        return scores;
    }

    public boolean isAGameOver(){

        boolean theGameisOver = false;

        for (GamePlayer gamePlayer : gamePlayers) {
            if(gamePlayer.hasAllSunkenShips()){
                theGameisOver = true;
            }
        }

        return theGameisOver;
    }

    public GamePlayer getWinner() {

        GamePlayer ganador = new GamePlayer();
        ganador.getPlayer().setUsername("nadie");
        for (GamePlayer gamePlayer : gamePlayers) {
            if(gamePlayer.hasAllSunkenShips()){
                ganador = gamePlayer.getOpponent();
            }
        }
        return ganador;
    }
    
    public boolean isGamePlayer(String username) {
        boolean siEs = false;
        for(GamePlayer actualGamePlayer : getGamePlayers()){
            String actualUsername = actualGamePlayer.getPlayer().getUsername();
            if(actualUsername.equals(username)){
                siEs = true;
            }
        }
        return siEs;
    }

    public boolean allShipsOfTheGameArePlaced() {

        boolean allPlaced = true;
        boolean hayDosJugadores = gamePlayers.size() == 2;

        for (GamePlayer gamePlayer : gamePlayers ) {
            if(gamePlayer.allShipsArePlaced() == true){
                allPlaced = allPlaced && true;
            }else{
                allPlaced = allPlaced && false;
            }
        }

        return hayDosJugadores && allPlaced;
    }

    public boolean bothPlayersShot() {

        boolean playersShot = true;

        for (GamePlayer gamePlayer : gamePlayers ) {

            GamePlayer opponent = gamePlayer.getOpponent();

            if(gamePlayer.tinenLaMismaCantidadDeSalvoes(opponent) &&
                !nadieDisparoAun()){

                playersShot = playersShot && true;

            }else{

                playersShot = playersShot && false;
            }

        }

        return playersShot;
    }

    public GamePlayer getGamePlayerConMenorId() {
        GamePlayer gamePlayerBuscado = new GamePlayer();
        ArrayList<GamePlayer> jugadores = new ArrayList<>(gamePlayers);

        if(jugadores.size() == 2){
            if(jugadores.get(0).getId() < jugadores.get(1).getId()){
                gamePlayerBuscado = jugadores.get(0);
            }else{
                gamePlayerBuscado = jugadores.get(1);
            }
        }else{
            gamePlayerBuscado = jugadores.get(0);
        }
        
        return gamePlayerBuscado;
    }

    public GamePlayer getJugadorQueDisparaPrimero() {
        GamePlayer gamePlayer = this.getGamePlayerConMenorId();
        return gamePlayer;
    }

    public boolean nadieDisparoAun() {

        boolean nadieDisparo = true;

        for ( GamePlayer gamePlayer : gamePlayers ) {
            if(gamePlayer.getCantidadDeSalvoes() ==  0 ){
                nadieDisparo = nadieDisparo && true;
            }else{
                nadieDisparo = nadieDisparo && false;
            }
        }

        return nadieDisparo;
    }

    public GamePlayer getGamePlayerDeTurno() {

        GamePlayer jugadorDeTurno = getJugadorQueDisparaPrimero();
        GamePlayer primerDisparador = getJugadorQueDisparaPrimero();
        GamePlayer opponent = primerDisparador.getOpponent();

        if (nadieDisparoAun() == true){

            jugadorDeTurno = primerDisparador;

        }else if( primerDisparador.tinenLaMismaCantidadDeSalvoes(opponent)){

            jugadorDeTurno = primerDisparador;

        }

        if( primerDisparador.getCantidadDeSalvoes() >
            opponent.getCantidadDeSalvoes() ){

            jugadorDeTurno = opponent;

        }

        if( opponent.getCantidadDeSalvoes() > 
            primerDisparador.getCantidadDeSalvoes() ){

            jugadorDeTurno = primerDisparador;
        }

        return jugadorDeTurno;
    }
}