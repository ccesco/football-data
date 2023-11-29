package fr.cyrilcesco.footballdata.competitionservice.domain.api;

import fr.cyrilcesco.footballdata.competitionservice.domain.model.Competition;

public interface AddCompetitionUseCase {

    Competition addCompetition(Competition competitionToAdd);

}
