package fr.cyrilcesco.footballdata.competitionservice.domain;

import fr.cyrilcesco.footballdata.competitionservice.domain.api.AddCompetitionUseCase;
import fr.cyrilcesco.footballdata.competitionservice.domain.model.Competition;
import fr.cyrilcesco.footballdata.competitionservice.domain.spi.CompetitionRepositoryPort;

public class DomainCompetitionService implements AddCompetitionUseCase {

    private final CompetitionRepositoryPort competitionRepository;

    public DomainCompetitionService(CompetitionRepositoryPort competitionRepository) {
        this.competitionRepository = competitionRepository;
    }

    @Override
    public Competition addCompetition(Competition competitionToAdd) {
        return competitionRepository.addCompetition(competitionToAdd);
    }
}
