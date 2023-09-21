package fr.cyrilcesco.footballdata.playersservice.domain.spi;

import fr.cyrilcesco.footballdata.playersservice.domain.model.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerRepositoryPort {

    List<Player> getAllPlayers();
    Optional<Player> getPlayer(String identifiant);
    Player savePlayer(Player playerToSave);

}
