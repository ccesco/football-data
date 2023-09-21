package fr.cyrilcesco.footballdata.playersservice.domain.api;

import fr.cyrilcesco.footballdata.playersservice.domain.model.Player;
import fr.cyrilcesco.footballdata.playersservice.domain.exception.PlayerNotFoundException;

import java.util.List;

public interface GetPlayerUseCase {

    List<Player> getTop10Players();

    Player getPlayer(String identifiant) throws PlayerNotFoundException;

}
