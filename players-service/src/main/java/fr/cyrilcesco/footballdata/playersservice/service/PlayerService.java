package fr.cyrilcesco.footballdata.playersservice.service;

import fr.cyrilcesco.footballdata.playersservice.controller.PlayersApiDelegate;
import fr.cyrilcesco.footballdata.playersservice.model.Players;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PlayerService implements PlayersApiDelegate {

    @Override
    public ResponseEntity<Players> playersGet() {
        return ResponseEntity.ok(new Players());
    }
}
