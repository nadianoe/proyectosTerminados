package com.nadia.ejemploProyecto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class HomeController {

   @GetMapping
    public String home(){
       return "scheduleGames.html";
   }
   
   @GetMapping("/gameRules")
   public String nextGame(){
   		return "gameRules.html";
   }
    
   @GetMapping("/aboutNYSL")
   public String aboutNYSL(){
   		return "aboutNYSL.html";
   }
    
   @GetMapping("/gameResults")
   public String gameResults(){
   		return "gameResults.html";
   }
   
   @GetMapping("/scheduleGames")
   public String scheduleGames(){
   		return "scheduleGames.html";
   }

   @GetMapping("/nextGame")
   public String nextGame(){
   		return "nextGame.html";
   }
    
   @GetMapping("/actualGame")
   public String actualGame(){
   		return "actualGame.html";
   }

}
