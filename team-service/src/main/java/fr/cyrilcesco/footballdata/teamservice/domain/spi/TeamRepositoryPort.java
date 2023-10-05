package fr.cyrilcesco.footballdata.teamservice.domain.spi;

import fr.cyrilcesco.footballdata.teamservice.domain.model.Team;

import java.util.List;
import java.util.Optional;

public interface TeamRepositoryPort {

    Optional<Team> getTeam(String identifiant);
    List<Team> getTeams();

}
