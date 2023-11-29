package fr.cyrilcesco.footballdata.competitionservice.domain;

import fr.cyrilcesco.footballdata.competitionservice.domain.model.Competition;
import fr.cyrilcesco.footballdata.competitionservice.domain.model.Team;
import fr.cyrilcesco.footballdata.competitionservice.domain.spi.CompetitionRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DomainCompetitionServiceTest {

    @Spy
    private CompetitionRepositoryPort competitionRepository;

    @InjectMocks
    private DomainCompetitionService sut;

    @Test
    void should_add_competition_in_repository() {
        Competition competition = Competition.builder().id("FR1").name("Ligue 1").teams(List.of(Team.builder().id("583").name("Paris Saint Germain").build())).seasonYear("2023").build();
        sut.addCompetition(competition);
        verify(competitionRepository).addCompetition(competition);
    }
}