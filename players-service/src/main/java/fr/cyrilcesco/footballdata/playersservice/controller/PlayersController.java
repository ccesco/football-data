package fr.cyrilcesco.footballdata.playersservice.controller;

import fr.cyrilcesco.footballdata.playersservice.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlayersController {

    private final PlayerService playerService;

    public PlayersController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<String>> getPlayers() {
        return new ResponseEntity<>(playerService.getAllplayerName(), HttpStatus.OK);
    }
}
