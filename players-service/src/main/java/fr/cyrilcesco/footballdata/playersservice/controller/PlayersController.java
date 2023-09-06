package fr.cyrilcesco.footballdata.playersservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlayersController {

    @GetMapping("/all")
    public ResponseEntity<List<String>> getPlayers() {
        List<String> players = List.of("Zidane", "Mbappe", "Haaland");
        return new ResponseEntity<>(players, HttpStatus.OK);
    }
}
