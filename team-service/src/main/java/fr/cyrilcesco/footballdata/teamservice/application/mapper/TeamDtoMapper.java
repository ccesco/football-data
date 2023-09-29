package fr.cyrilcesco.footballdata.teamservice.application.mapper;

import fr.cyrilcesco.footballdata.teamservice.domain.model.Team;
import fr.cyrilcesco.footballdata.teamservice.model.TeamDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeamDtoMapper {
    TeamDto mapFromDomain(Team team);
}
