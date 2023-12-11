package fr.cyrilcesco.footballdata.initservice.domain.model.mapper;

import fr.cyrilcesco.footballdata.client.transfermarkt.model.TransfermarktCompetitionResponse;
import fr.cyrilcesco.footballdata.initservice.domain.model.Competition;
import fr.cyrilcesco.footballdata.initservice.domain.model.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CompetitionMapper {

    TeamMapper teamMapper = new TeamMapperImpl();

    @Mapping(source = "teams", target = "teams", qualifiedByName = "team")
    Competition mapFromClient(TransfermarktCompetitionResponse transfermarktCompetitionResponse);

    @Named("team")
    static Team mapTeams(fr.cyrilcesco.footballdata.client.transfermarkt.model.Team team) {
        return teamMapper.mapFromClient(team);
    }
}
