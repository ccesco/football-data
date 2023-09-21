package fr.cyrilcesco.footballdata.playersservice.mapper;

import fr.cyrilcesco.footballdata.playersservice.domain.model.Player;
import fr.cyrilcesco.footballdata.playersservice.repository.entity.PlayerDao;
import org.springframework.stereotype.Component;

@Component
public class PlayerDaoMapper {

    public Player mapToDomain(PlayerDao playerDao) {
        Player player = new Player();
        player.setId(String.valueOf(playerDao.getId()));
        player.setFirstName(playerDao.getFirstName());
        player.setLastName(playerDao.getLastName());
        return player;
    }
}
