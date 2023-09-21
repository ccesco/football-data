package fr.cyrilcesco.footballdata.playersservice.service;

import fr.cyrilcesco.footballdata.playersservice.controller.PlayerApiDelegate;
import fr.cyrilcesco.footballdata.playersservice.domain.api.GetPlayerUseCase;
import fr.cyrilcesco.footballdata.playersservice.mapper.PlayerDtoMapper;
import fr.cyrilcesco.footballdata.playersservice.model.PlayerDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PlayerService implements PlayerApiDelegate {

    private final GetPlayerUseCase getPlayerUseCase;
    private final PlayerDtoMapper playerDtoMapper;

    public PlayerService(GetPlayerUseCase getPlayerUseCase, PlayerDtoMapper playerDtoMapper) {
        this.getPlayerUseCase = getPlayerUseCase;
        this.playerDtoMapper = playerDtoMapper;
    }

    @Override
    public ResponseEntity<PlayerDto> playerIdGet(String identifiant) {
        // erreur throw
        PlayerDto playerDto = playerDtoMapper.mapFromDomain(getPlayerUseCase.getPlayer(identifiant));
        return ResponseEntity.ok(playerDto);
    }
}
