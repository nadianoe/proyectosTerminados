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

   @GetMapping("/nextGame.html")
   public String nextGame(){
   		return "nextGame.html";
   }
    
   @GetMapping("/actualGame")
   public String actualGame(){
   		return "actualGame.html";
   }

}
