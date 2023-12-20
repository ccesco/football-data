package fr.cyrilcesco.footballdata.initservice.players;

import fr.cyrilcesco.footballdata.client.transfermarkt.TransfermarktClient;
import fr.cyrilcesco.footballdata.client.transfermarkt.exception.TransfermarktSocketTimeOut;
import fr.cyrilcesco.footballdata.initservice.domain.model.InitPlayerRequest;
import fr.cyrilcesco.footballdata.initservice.domain.model.Player;
import fr.cyrilcesco.footballdata.initservice.domain.model.mapper.PlayerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GetPlayerInformationsService {

    private final TransfermarktClient transfermarktClient;
    private final PlayerMapper mapper;

    public GetPlayerInformationsService(TransfermarktClient transfermarktClient, PlayerMapper mapper) {
        this.transfermarktClient = transfermarktClient;
        this.mapper = mapper;
    }

    public Player callClient(InitPlayerRequest request) {
        try {
            Player player = mapper.mapFromClientResponse(transfermarktClient.getPlayerInformations(request.getPlayerId(), request.getName()));
            log.info("get Player info ok for request {}}", request);
            return player;
        } catch (TransfermarktSocketTimeOut exception) {
            log.info("TransfermarktSocketTimeOut for request {}}", request);
            return Player.builder().id(request.getPlayerId()).name(request.getName()).build();
        }
    }
}
