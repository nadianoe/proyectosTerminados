package com.nadia.ejemploProyecto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/htmls/*")
public class HomeController {

   @GetMapping("/htmls/scheduledule")
    public String home(){
       return "scheduleGames.html";
   }
}
