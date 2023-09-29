package fr.cyrilcesco.footballdata.teamservice.infrastructure.mapper;

import fr.cyrilcesco.footballdata.teamservice.domain.model.Team;
import fr.cyrilcesco.footballdata.teamservice.infrastructure.repository.entity.TeamDao;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeamDaoMapper {
    Team mapToDomain(TeamDao teamDao);
}
