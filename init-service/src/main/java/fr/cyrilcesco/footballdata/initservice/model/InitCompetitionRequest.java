package fr.cyrilcesco.footballdata.initservice.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class InitCompetitionRequest {

    private String competitionId;
    private String year;

}
