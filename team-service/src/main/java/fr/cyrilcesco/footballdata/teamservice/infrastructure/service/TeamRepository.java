package fr.cyrilcesco.footballdata.teamservice.infrastructure.service;

import fr.cyrilcesco.footballdata.teamservice.domain.model.Team;
import fr.cyrilcesco.footballdata.teamservice.domain.spi.TeamRepositoryPort;
import fr.cyrilcesco.footballdata.teamservice.infrastructure.mapper.TeamDaoMapper;
import fr.cyrilcesco.footballdata.teamservice.infrastructure.repository.PostgreSqlTeamRepository;
import fr.cyrilcesco.footballdata.teamservice.infrastructure.repository.entity.TeamDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TeamRepository implements TeamRepositoryPort {

    private final PostgreSqlTeamRepository repository;
    private final TeamDaoMapper mapper;

    public TeamRepository(PostgreSqlTeamRepository repository, TeamDaoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Team> getTeam(String identifiant) {
        Optional<TeamDao> teamOptionnal = repository.findById(Long.valueOf(identifiant));
        return teamOptionnal.map(mapper::mapToDomain);
    }

    @Override
    public List<Team> getTeams() {
        return repository.findAll().stream().map(mapper::mapToDomain).toList();
    }


}
