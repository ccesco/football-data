package fr.cyrilcesco.footballdata.playersservice.repository;

import fr.cyrilcesco.footballdata.playersservice.repository.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
