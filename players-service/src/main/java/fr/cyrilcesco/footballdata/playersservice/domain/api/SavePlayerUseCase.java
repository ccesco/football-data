package fr.cyrilcesco.footballdata.playersservice.domain.api;

import fr.cyrilcesco.footballdata.playersservice.domain.model.Player;

public interface SavePlayerUseCase {
    Player save(Player playerToSave);
}
