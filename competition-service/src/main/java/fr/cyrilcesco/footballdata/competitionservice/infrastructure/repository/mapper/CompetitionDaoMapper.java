package fr.cyrilcesco.footballdata.competitionservice.infrastructure.repository.mapper;

import fr.cyrilcesco.footballdata.competitionservice.domain.model.Competition;
import fr.cyrilcesco.footballdata.competitionservice.infrastructure.repository.entity.CompetitionDao;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompetitionDaoMapper {
    Competition mapToDomain(CompetitionDao dao);

    CompetitionDao mapToRepository(Competition team);
}
