package com.codeoftheweb.salvo.entidades;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
public class Salvo {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private int turn;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="game_player")
    private GamePlayer gamePlayer;
    
    @ElementCollection
    @Column(name="salvoes_locations")
    private List<String> locations;

    public Salvo() {}

    public Salvo(GamePlayer gamePlayer, int turn, List<String> locations){
        this.gamePlayer = gamePlayer;
        this.turn = turn;
        this.locations = locations;
    }

    public long getId() {
        return id;
    }

    public int getTurn() {
    	return turn;
    }

    public GamePlayer getGamePlayer() {
    	return gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public int getNumberOfShots(){
        int number = locations.size();
        return number;
    }

    

}
















