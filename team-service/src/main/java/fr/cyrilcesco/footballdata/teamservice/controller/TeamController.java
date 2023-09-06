package fr.cyrilcesco.footballdata.teamservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeamController {

    @GetMapping("/all")
    public ResponseEntity<List<String>> getTeams() {
        List<String> teams = List.of("PSG", "Lyon", "Lens");
        return new ResponseEntity<>(teams, HttpStatus.OK);
    }
}
