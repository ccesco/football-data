package fr.cyrilcesco.footballdata.teamservice.infrastructure.repository;

import fr.cyrilcesco.footballdata.teamservice.infrastructure.repository.entity.TeamDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostgreSqlTeamRepository extends JpaRepository<TeamDao, Long> {
}
