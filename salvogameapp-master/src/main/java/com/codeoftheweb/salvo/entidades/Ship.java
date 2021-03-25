package com.codeoftheweb.salvo.entidades;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
public class Ship {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private ShipType type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="game_player")
    private GamePlayer gamePlayer;
    
    @ElementCollection
    @Column(name="ships_locations")
    private List<String> locations;

    public Ship() {}

    public Ship(GamePlayer gamePlayer, ShipType type, List<String> locations){
        this.gamePlayer = gamePlayer;
        this.type = type;
        this.locations = locations;
    }

    public long getId() {
        return id;
    }

    public ShipType getType() {
    	return type;
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
    
    public int cantidadDeHits(Salvo salvo) {

        int cantidadHits = 0;
        List<String> shootingLocations = salvo.getLocations();

        for (String location : this.locations) {
            if(shootingLocations.contains(location)){
                cantidadHits++;
            }
        }

        return cantidadHits;
    }

    public List<String> hitPlaces(Salvo salvo) {

        List<String> placesSearched = new ArrayList<>();
        List<String> shootingLocations = salvo.getLocations();

        for (String location : this.locations) {
            if(shootingLocations.contains(location)){
                placesSearched.add(location);
            }
        }

        return placesSearched;
    }

    public boolean wasShotBy(Salvo salvo) {

        boolean wasShot = false;

        if(cantidadDeHits(salvo) > 0){
            wasShot = true;
        }

        return wasShot;
    }

    public int cantidadTotalDeHits(GamePlayer opponent) {

        int totalHits = 0;
        Set<Salvo> salvoesOpponent = opponent.getSalvoes();

        for (Salvo salvo : salvoesOpponent) {
            totalHits += cantidadDeHits(salvo);
        }

        return totalHits;

    }

    public boolean wasSunkByOpponent(GamePlayer opponent) {

        boolean wasSunk = false;

        if (cantidadTotalDeHits(opponent) >= type.getLength()) {
            wasSunk = true;
        }

        return wasSunk;
    }

}
















