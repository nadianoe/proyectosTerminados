package com.codeoftheweb.salvo.entidades;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.*;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String username;
    
    private String password;


    @OneToMany(mappedBy="player", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Score> scores = new HashSet<>();

    public Player() { }

    public Player(String username, String password) {
        this.username = username;
        this.password =  password;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Score getScore(Game game){
        Score scoreBuscado = null;
        for (Score actualScore : scores ) {
            if (actualScore.getGame().getId() == game.getId()) {
                scoreBuscado = actualScore;
            }
        }
        return scoreBuscado;
    }

    public String getPassword() {
    	return password;
    }

    public void setPassword(String password) {
    	this.password = password;
    }

    
}