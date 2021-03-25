package com.codeoftheweb.salvo.entidades;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
public class GamePlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    private Date joinDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="player_id")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private Game game;

    @OneToMany(mappedBy="gamePlayer", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Ship> ships;

    @OneToMany(mappedBy="gamePlayer", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Salvo> salvoes;

    public GamePlayer (){
        this.joinDate = new Date(2019,9,12);
        this.ships = new HashSet<Ship>();
        this.salvoes = new HashSet<Salvo>();
        this.player = new Player();
        this.game = new Game();
    }

        public GamePlayer (Date date, Player player, Game game){

        this.joinDate = date;
        this.player = player;
        this.game = game;
        this.ships = new HashSet<>();
        this.salvoes = new HashSet<>();
    }


    public long getId() {

        return id;

    }

     public Date getJoinDate() {
        return joinDate;
    }
    

    public Player getPlayer(){

        return player;
    }

    public Game getGame() {
        return game;
    }

    public Set<Ship> getShips() {
        return ships;
    }

    public void addShip(Ship ship){
        ships.add(ship);
    }

    public Set<Salvo> getSalvoes() {
        return salvoes;
    }

    public void setSalvoes(Set<Salvo> salvoes) {
        this.salvoes = salvoes;
    }

    public void addSalvo(Salvo salvo){
        salvoes.add(salvo);
    }

    public int getScoreOfGamePlayer() {
        return player.getScore(game).getScore();
    }
    

    public List<String> getAllLocations() {
        List<String> allLocations = new ArrayList<>();
        for (Ship ship : ships){
            allLocations.addAll(ship.getLocations());
        }
        return allLocations;
    }
    
    public boolean someLocationIsOccupied(List<String> newLocations) {
        
        boolean someIsOccupied = false;
        List<String> occupiedLocations = this.getAllLocations();
        
        for ( String location : newLocations ){

            if(occupiedLocations.contains(location)){
                someIsOccupied = true;
            }
                
        }
        
        return someIsOccupied;
        
    }
    
    public boolean thereIsOccupiedLocation(List<Ship> newShips) {
        
        boolean thereIs = false;
        
        for(Ship ship : newShips){
            
            if (someLocationIsOccupied(ship.getLocations())){
                thereIs = true;
            }
            
        }
        
        return thereIs;
        
    }

    public boolean isSalvoWithSameTurn(Salvo salvo){

        boolean salvoWithSameTurn = false;

        for (Salvo actualSalvo : salvoes ) {

            if(salvo.getTurn() == actualSalvo.getTurn()){
                salvoWithSameTurn = true;
            }
            
        }

        return salvoWithSameTurn;
    }

    public GamePlayer getOpponent() {

        Set<GamePlayer> allGamePlayers = game.getGamePlayers();
        GamePlayer opponent = new GamePlayer();

        for (GamePlayer aGamePlayer : allGamePlayers) {
            if(aGamePlayer.getId() != this.id){
                opponent = aGamePlayer;
            }
        }

        return opponent;
    }

    public List<ShipType> getBarcosHundidosPor(GamePlayer opponent) {

        List<ShipType> barcosHundidos = new ArrayList<>();
        Set<Salvo> salvoesReceived = opponent.getSalvoes();

        for ( Ship ship : ships) {

            if (ship.wasSunkByOpponent(opponent)) {
                barcosHundidos.add(ship.getType());
            }
                
        }

        return barcosHundidos;
    }

    public List<Object> getInfoShipsHited(GamePlayer opponent) {
        
        List<Object> infoOfAllShipsHited = new ArrayList<>();
        Set<Salvo> salvoesReceived = opponent.getSalvoes();

        for (Ship ship : ships) {
            for (Salvo salvo : salvoesReceived) {

                if(ship.wasShotBy(salvo)){

                    HashMap<String,Object> infoShip = new HashMap<>();

                    infoShip.put("type",ship.getType());
                    infoShip.put("numberOfHits",ship.cantidadDeHits(salvo));
                    infoShip.put("turn",salvo.getTurn());

                    if (ship.wasSunkByOpponent(opponent)) {
                        infoShip.put("sunkDown",true);
                    }else{
                        infoShip.put("sunkDown",false);
                    }

                    infoOfAllShipsHited.add(infoShip);

                }
            }
        }

        return infoOfAllShipsHited;
    }

    public List<String> getAllHitLocations(GamePlayer opponent) {

        Set<Salvo> salvoesOpponent = opponent.getSalvoes();
        List<String> allHitsLocations = new ArrayList<>();

        for (Ship ship : ships) {
            for (Salvo salvo : salvoesOpponent) {
                allHitsLocations.addAll(ship.hitPlaces(salvo));
            }
        }

        return allHitsLocations;
    }

    public boolean hasAllSunkenShips() {

        boolean allShipsAreSunk = false;

        GamePlayer opponent = this.getOpponent();
        List<ShipType> barcosHundidos = getBarcosHundidosPor(opponent);
        
        if(barcosHundidos.size() == 5){
            allShipsAreSunk = true;
        }

        return allShipsAreSunk;
    }

    public boolean allShipsArePlaced(){
        boolean shipsArePlaced = false;
        if (ships.size() == 5) {
            shipsArePlaced = true;
        }
        return shipsArePlaced;
    }

    public boolean isPlacingShips(){
        return ships.size() < 5;
    }

    public int cantidadSalvoesDisparados(){
        return salvoes.size();
    }

    public List<ShipType> getShipsPlaced() {

        List<ShipType> barcosPresentes = new ArrayList<>();

        for (Ship ship : ships) {
            barcosPresentes.add(ship.getType());
        }

        return barcosPresentes;
    }

    public String getUsername() {
        String username = player.getUsername();
        return username;
    }

    public int getCantidadDeSalvoes() {
        int cantidadDeSalvoes = salvoes.size();
        return cantidadDeSalvoes;
    }

    public boolean tinenLaMismaCantidadDeSalvoes(GamePlayer opponent) {
        
        boolean mismaCantidadDeSalvoes = false;

        if( this.getCantidadDeSalvoes() ==
            opponent.getCantidadDeSalvoes() ){
            mismaCantidadDeSalvoes = mismaCantidadDeSalvoes && true;
        }else{
            mismaCantidadDeSalvoes = mismaCantidadDeSalvoes && false;
        }
        
        return mismaCantidadDeSalvoes;
    }
}
