package fr.cyrilcesco.footballdata.playersservice.mapper;

import fr.cyrilcesco.footballdata.playersservice.domain.model.Player;
import fr.cyrilcesco.footballdata.playersservice.model.PlayerDto;
import org.springframework.stereotype.Component;

@Component
public class PlayerDtoMapper {

    public PlayerDto mapFromDomain(Player player) {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setId(player.getId());
        playerDto.setFirstName(player.getFirstName());
        playerDto.setLastName(player.getLastName());
        return playerDto;
    }
}
