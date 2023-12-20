package fr.cyrilcesco.footballdata.initservice.domain.model.mapper;

import fr.cyrilcesco.footballdata.client.transfermarkt.model.TransfermarktPlayerResponse;
import fr.cyrilcesco.footballdata.initservice.domain.model.Player;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlayerMapper {
    Player mapFromClientResponse(TransfermarktPlayerResponse team);
}
