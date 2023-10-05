package fr.cyrilcesco.footballdata.teamservice.domain.api;

import fr.cyrilcesco.footballdata.teamservice.domain.exception.TeamNotFoundException;
import fr.cyrilcesco.footballdata.teamservice.domain.model.Team;

import java.util.List;

public interface GetTeamUseCase {

    Team getTeam(String identifiant) throws TeamNotFoundException;
    List<Team> getTeams();

}
