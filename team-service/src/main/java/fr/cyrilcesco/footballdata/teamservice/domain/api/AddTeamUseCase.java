package fr.cyrilcesco.footballdata.teamservice.domain.api;

import fr.cyrilcesco.footballdata.teamservice.domain.model.Team;

public interface AddTeamUseCase {

    Team addTeam(Team teamToAdd);

}
