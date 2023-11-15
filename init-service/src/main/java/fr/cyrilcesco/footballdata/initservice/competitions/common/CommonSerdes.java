package fr.cyrilcesco.footballdata.initservice.competitions.common;

import fr.cyrilcesco.footballdata.initservice.competitions.model.Competition;
import fr.cyrilcesco.footballdata.initservice.competitions.model.InitCompetitionRequest;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonSerde;

public class CommonSerdes {

    public static final Serde<String> STRING_SERDES = Serdes.String();
    public static final JsonSerde<Competition> COMPETITION_SERDES = new JsonSerde<>(Competition.class);
    public static final JsonSerde<InitCompetitionRequest> INIT_COMPETITION_SERDES = new JsonSerde<>(InitCompetitionRequest.class);
}
