package fr.cyrilcesco.footballdata.initservice.team;

import fr.cyrilcesco.footballdata.client.transfermarkt.TransfermarktClient;
import fr.cyrilcesco.footballdata.client.transfermarkt.exception.TransfermarktSocketTimeOut;
import fr.cyrilcesco.footballdata.initservice.domain.model.InitTeamRequest;
import fr.cyrilcesco.footballdata.initservice.domain.model.Team;
import fr.cyrilcesco.footballdata.initservice.domain.model.mapper.TeamMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GetTeamInformationsService {

    private final TransfermarktClient transfermarktClient;
    private final TeamMapper mapper;

    public GetTeamInformationsService(TransfermarktClient transfermarktClient, TeamMapper mapper) {
        this.transfermarktClient = transfermarktClient;
        this.mapper = mapper;
    }

    public Team callClient(InitTeamRequest request) {
        try {
            Team team = mapper.mapFromClientResponse(transfermarktClient.getTeamsPlayersInformations(request.getTeamId(), request.getYear()));
            log.info("get Team info ok for request {}}", request);
            return team;
        } catch (TransfermarktSocketTimeOut exception) {
            log.info("TransfermarktSocketTimeOut for request {}}", request);
            return Team.builder().id(request.getTeamId()).seasonYear(request.getYear()).build();
        }
    }
}
