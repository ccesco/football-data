package fr.cyrilcesco.footballdata.playersservice.service;

import fr.cyrilcesco.footballdata.playersservice.domain.model.Player;
import fr.cyrilcesco.footballdata.playersservice.domain.spi.PlayerRepositoryPort;
import fr.cyrilcesco.footballdata.playersservice.mapper.PlayerDaoMapper;
import fr.cyrilcesco.footballdata.playersservice.repository.SpringPlayerRepository;
import fr.cyrilcesco.footballdata.playersservice.repository.entity.PlayerDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PlayerRepository implements PlayerRepositoryPort {

    private final SpringPlayerRepository springPlayerRepository;
    private final PlayerDaoMapper playerDaoMapper;

    public PlayerRepository(SpringPlayerRepository springPlayerRepository, PlayerDaoMapper playerDaoMapper) {
        this.springPlayerRepository = springPlayerRepository;
        this.playerDaoMapper = playerDaoMapper;
    }

    @Override
    public List<Player> getAllPlayers() {
        return springPlayerRepository.findAll().stream().map(playerDaoMapper::mapToDomain).toList();
    }

    @Override
    public Optional<Player> getPlayer(String identifiant) {
        Optional<PlayerDao> playerOptionnal = springPlayerRepository.findById(Long.valueOf(identifiant));
        return playerOptionnal.map(playerDaoMapper::mapToDomain);
    }

    @Override
    public Player savePlayer(Player playerToSave) {
        return null;
    }
}
