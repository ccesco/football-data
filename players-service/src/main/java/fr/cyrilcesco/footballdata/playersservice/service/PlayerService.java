package fr.cyrilcesco.footballdata.playersservice.service;

import fr.cyrilcesco.footballdata.playersservice.repository.PlayerRepository;
import fr.cyrilcesco.footballdata.playersservice.repository.entity.Player;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    private PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<String> getAllplayerName() {
        return playerRepository.findAll().stream().map(Player::getLastName).toList();
    }
}
