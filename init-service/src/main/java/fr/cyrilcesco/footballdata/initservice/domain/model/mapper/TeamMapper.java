package fr.cyrilcesco.footballdata.initservice.domain.model.mapper;

import fr.cyrilcesco.footballdata.client.transfermarkt.model.TransfermarktTeamResponse;
import fr.cyrilcesco.footballdata.initservice.domain.model.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeamMapper {

    @Mapping(target = "informationsLink", source = "link")
    Team mapFromClient(fr.cyrilcesco.footballdata.client.transfermarkt.model.Team team);

    Team mapFromClientResponse(TransfermarktTeamResponse team);
}
