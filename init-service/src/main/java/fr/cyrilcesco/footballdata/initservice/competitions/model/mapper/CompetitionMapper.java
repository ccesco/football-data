package fr.cyrilcesco.footballdata.initservice.competitions.model.mapper;

import fr.cyrilcesco.footballdata.client.transfermarkt.model.TransfermarktCompetitionResponse;
import fr.cyrilcesco.footballdata.initservice.competitions.model.Competition;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompetitionMapper {
    Competition mapFromClient(TransfermarktCompetitionResponse transfermarktCompetitionResponse);
}
