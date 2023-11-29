package fr.cyrilcesco.footballdata.competitionservice.infrastructure.repository.mapper;

import fr.cyrilcesco.footballdata.competitionservice.domain.model.Competition;
import fr.cyrilcesco.footballdata.competitionservice.domain.model.Team;
import fr.cyrilcesco.footballdata.competitionservice.infrastructure.repository.entity.CompetitionDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CompetitionDaoMapperTest {

    private final CompetitionDaoMapper mapper = new CompetitionDaoMapperImpl();

    @Nested
    @DisplayName("test map to domain")
    class testMapToDomain {

        @Test
        void should_return_empty_when_competition_empty() {
            CompetitionDao competitionDao = CompetitionDao.builder().build();
            Competition competition = mapper.mapToDomain(competitionDao);

            assertNull(competition.getId());
            assertNull(competition.getName());
            assertNull(competition.getTeams());
            assertNull(competition.getSeasonYear());
        }

        @Test
        void should_return_not_empty_when_competition_not_empty() {
            CompetitionDao competitionDao = CompetitionDao.builder().id("FR1").name("Ligue 1").build();
            Competition competition = mapper.mapToDomain(competitionDao);

            assertEquals("FR1", competition.getId());
            assertEquals("Ligue 1", competition.getName());
            assertNull(competition.getTeams());
            assertNull(competition.getSeasonYear());
        }
    }

    @Nested
    @DisplayName("test map to repository")
    class testMapToRepository {

        @Test
        void should_return_empty_when_competition_empty() {
            Competition competition = Competition.builder().build();
            CompetitionDao competitionDao = mapper.mapToRepository(competition);

            assertNull(competitionDao.getId());
            assertNull(competitionDao.getName());
        }

        @Test
        void should_return_not_empty_when_competition_not_empty() {
            Competition competition = Competition.builder().id("FR1").name("Ligue 1").seasonYear("2023").teams(List.of(Team.builder().id("583").name("Paris Saint Germain").build())).build();
            CompetitionDao competitionDao = mapper.mapToRepository(competition);

            assertEquals("FR1", competitionDao.getId());
            assertEquals("Ligue 1", competitionDao.getName());
        }
    }

}