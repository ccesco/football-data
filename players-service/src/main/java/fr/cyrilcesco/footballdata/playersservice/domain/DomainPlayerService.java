package fr.cyrilcesco.footballdata.playersservice.domain;

import fr.cyrilcesco.footballdata.playersservice.domain.api.GetPlayerUseCase;
import fr.cyrilcesco.footballdata.playersservice.domain.api.SavePlayerUseCase;
import fr.cyrilcesco.footballdata.playersservice.domain.model.Player;
import fr.cyrilcesco.footballdata.playersservice.domain.spi.PlayerRepositoryPort;
import fr.cyrilcesco.footballdata.playersservice.domain.exception.PlayerNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DomainPlayerService implements GetPlayerUseCase, SavePlayerUseCase {

    private final PlayerRepositoryPort playerRepository;

    public DomainPlayerService(PlayerRepositoryPort playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public List<Player> getTop10Players() {
        List<Player> allPlayers = playerRepository.getAllPlayers();
        if(Objects.isNull(allPlayers)) {
            return Collections.emptyList();
        }
        return allPlayers.stream().limit(10).toList();
    }

    @Override
    public Player getPlayer(String identifiant) {
        return playerRepository.getPlayer(identifiant).orElseThrow(() -> new PlayerNotFoundException(identifiant));
    }

    @Override
    public Player save(Player playerToSave) {
        return playerRepository.savePlayer(playerToSave);
    }
}
