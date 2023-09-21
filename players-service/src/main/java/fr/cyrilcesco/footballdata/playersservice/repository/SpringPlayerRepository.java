package fr.cyrilcesco.footballdata.playersservice.repository;

import fr.cyrilcesco.footballdata.playersservice.repository.entity.PlayerDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringPlayerRepository extends JpaRepository<PlayerDao, Long> {
}
