package fr.cyrilcesco.footballdata.initservice.competitions;

import fr.cyrilcesco.footballdata.initservice.competitions.config.InitCompetitionList;
import fr.cyrilcesco.footballdata.initservice.domain.model.InitCompetitionRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InitCompetitionServiceTest {

    @Mock
    private InitCompetitionList competitions;

    @Mock
    private CompetitionProducer producer;

    @InjectMocks
    private InitCompetitionService initService;

    @Test
    void should_get_empty_list_if_message_sended() {
        when(competitions.getCompetitions()).thenReturn(List.of("FR1", "FR2"));
        when(producer.sendMessage(any())).thenReturn(true);

        List<InitCompetitionRequest> initCompetitionRequests = initService.launchInitialisationFromCompetitionList();

        assertEquals(0, initCompetitionRequests.size());
    }

    @Test
    void should_get_list_with_error_when_message_sended_with_error() {
        when(competitions.getCompetitions()).thenReturn(List.of("FR1", "FR2"));
        when(producer.sendMessage(any())).thenReturn(false);

        List<InitCompetitionRequest> initCompetitionRequests = initService.launchInitialisationFromCompetitionList();

        assertEquals(2, initCompetitionRequests.size());
        assertEquals(InitCompetitionRequest.builder().competitionId("FR1").year("2023").build(), initCompetitionRequests.get(0));
        assertEquals(InitCompetitionRequest.builder().competitionId("FR2").year("2023").build(), initCompetitionRequests.get(1));
    }

    @Test
    void should_get_list_with_error_when_message_sended_with_error_for_specific_competition() {
        when(competitions.getCompetitions()).thenReturn(List.of("FR1", "FR2"));
        when(producer.sendMessage(InitCompetitionRequest.builder().competitionId("FR2").year("2023").build())).thenReturn(false);
        when(producer.sendMessage(InitCompetitionRequest.builder().competitionId("FR1").year("2023").build())).thenReturn(true);

        List<InitCompetitionRequest> initCompetitionRequests = initService.launchInitialisationFromCompetitionList();

        assertEquals(1, initCompetitionRequests.size());
        assertEquals(InitCompetitionRequest.builder().competitionId("FR2").year("2023").build(), initCompetitionRequests.get(0));
    }
}