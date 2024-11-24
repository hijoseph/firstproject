package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller   /* Controller 선언 */
public class FirstController {

    @GetMapping("/hi") /* url request */
    public String niceToMeetYou(Model model){  /* Model Object get */
        /* Model Variable registration*/
        model.addAttribute("titleheader", "hi");
        model.addAttribute("username", "hyun");
        return "greetings";  /* View template Return */
    }

    @GetMapping("/bye")
    public String seeYouNext(Model model){
        model.addAttribute("titleheader", "bye");
        model.addAttribute("nickname", "hyun");
        return "goodbye";
    }
}
