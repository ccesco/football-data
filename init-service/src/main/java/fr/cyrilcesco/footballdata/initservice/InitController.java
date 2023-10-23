package fr.cyrilcesco.footballdata.initservice;

import fr.cyrilcesco.footballdata.initservice.model.InitCompetitionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InitController {

    private final InitService initService;

    public InitController(InitService initService) {
        this.initService = initService;
    }

    @PostMapping("/init")
    public ResponseEntity<List<InitCompetitionRequest>> initService() {
        return ResponseEntity.ok(initService.launchInitialisationFromCompetitionList());
    }
}
