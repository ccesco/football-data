package fr.cyrilcesco.footballdata.initservice;

import fr.cyrilcesco.footballdata.initservice.model.InitCompetitionRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InitController.class)
class InitControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private InitService initService;

    @Test
    void should_get_empty_response_if_all_send_good() throws Exception {
        when(initService.launchInitialisationFromCompetitionList()).thenReturn(Collections.emptyList());
        mvc.perform(MockMvcRequestBuilders
                        .post("/init"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void should_get_competition_if_send_error() throws Exception {
        when(initService.launchInitialisationFromCompetitionList()).thenReturn(List.of(InitCompetitionRequest.builder().competitionId("FR1").year("2023").build()));
        mvc.perform(MockMvcRequestBuilders
                        .post("/init"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"competitionId\":\"FR1\",\"year\":\"2023\"}]"));
    }
}