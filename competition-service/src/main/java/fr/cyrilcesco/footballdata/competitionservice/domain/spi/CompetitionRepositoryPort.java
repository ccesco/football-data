package fr.cyrilcesco.footballdata.competitionservice.domain.spi;

import fr.cyrilcesco.footballdata.competitionservice.domain.model.Competition;

import java.util.List;
import java.util.Optional;

public interface CompetitionRepositoryPort {

    Optional<Competition> getCompetition(String identifiant);
    List<Competition> getCompetitions();
    Competition addCompetition(Competition competitionToAdd);

}
