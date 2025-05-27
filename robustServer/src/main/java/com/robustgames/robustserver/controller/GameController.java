package com.robustgames.robustserver.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class GameController {

    // Für den Anfang: Spielstand einfach als String
    private String gameState = "Spielstand...";

    @PostMapping("/turn")
    public String makeTurn(@RequestBody String turnData) {
        // TODO: Parse Turn, Spiellogik anwenden, speichern
        gameState = "Neuer Spielstand nach Turn: " + turnData;
        return "Turn received!";
    }

    @GetMapping("/state")
    public String getState() {
        return gameState;
    }
}