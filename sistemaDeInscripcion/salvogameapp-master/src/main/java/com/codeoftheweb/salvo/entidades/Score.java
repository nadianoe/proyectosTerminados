package com.codeoftheweb.salvo.entidades;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
public class Score {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private int score;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="player_id")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="game_id")
    private Game game;
    
    private Date finishDate;

    public Score() {}

    public Score(int score, Player player, Game game, Date finishDate){
        this.score = score;
        this.player = player;
        this.game = game;
        this.finishDate = finishDate;
    }

    public long getId() {
        return id;
    }

    public int getScore() {
    	return score;
    }

    public Player getPlayer() {
    	return player;
    }

    public Game getGame() {
        return game;
    }


}
















