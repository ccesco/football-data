package fr.cyrilcesco.footballdata.competitionservice.infrastructure.repository;

import fr.cyrilcesco.footballdata.competitionservice.infrastructure.repository.entity.CompetitionDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostgreSqlCompetitionRepository extends JpaRepository<CompetitionDao, Long> {
}
