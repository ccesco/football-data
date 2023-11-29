package fr.cyrilcesco.footballdata.competitionservice.infrastructure.service;

import fr.cyrilcesco.footballdata.competitionservice.domain.model.Competition;
import fr.cyrilcesco.footballdata.competitionservice.domain.spi.CompetitionRepositoryPort;
import fr.cyrilcesco.footballdata.competitionservice.infrastructure.repository.PostgreSqlCompetitionRepository;
import fr.cyrilcesco.footballdata.competitionservice.infrastructure.repository.entity.CompetitionDao;
import fr.cyrilcesco.footballdata.competitionservice.infrastructure.repository.mapper.CompetitionDaoMapper;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class CompetitionRepository implements CompetitionRepositoryPort {

    private final PostgreSqlCompetitionRepository repository;
    private final CompetitionDaoMapper mapper;

    public CompetitionRepository(PostgreSqlCompetitionRepository repository, CompetitionDaoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Competition> getCompetition(String identifiant) {
        return Optional.empty();
    }

    @Override
    public List<Competition> getCompetitions() {
        return Collections.emptyList();
    }

    @Override
    public Competition addCompetition(Competition competitionToAdd) {
        CompetitionDao competitionSave = repository.save(mapper.mapToRepository(competitionToAdd));
        return mapper.mapToDomain(competitionSave);
    }

}
