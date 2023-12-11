package fr.cyrilcesco.footballdata.initservice.competitions;

import fr.cyrilcesco.footballdata.client.transfermarkt.TransfermarktClient;
import fr.cyrilcesco.footballdata.client.transfermarkt.exception.TransfermarktSocketTimeOut;
import fr.cyrilcesco.footballdata.initservice.domain.model.Competition;
import fr.cyrilcesco.footballdata.initservice.domain.model.mapper.CompetitionMapper;
import fr.cyrilcesco.footballdata.initservice.domain.model.InitCompetitionRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GetCompetitionInformationsService {

    private final TransfermarktClient transfermarktClient;
    private final CompetitionMapper mapper;

    public GetCompetitionInformationsService(TransfermarktClient transfermarktClient, CompetitionMapper mapper) {
        this.transfermarktClient = transfermarktClient;
        this.mapper = mapper;
    }

    public Competition callClient(InitCompetitionRequest request) {
        try {
            Competition competition = mapper.mapFromClient(transfermarktClient.getTeamsOfCompetition(request.getCompetitionId(), request.getYear()));
            log.info("get Competition info ok for request {}}", request);
            return competition;
        } catch (TransfermarktSocketTimeOut exception) {
            log.info("TransfermarktSocketTimeOut for request {}}", request);
            return Competition.builder().id(request.getCompetitionId()).seasonYear(request.getYear()).build();
        }
    }
}
