package fr.cyrilcesco.footballdata.client.transfermarkt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Player {
    private final String name;
    private final String id;
    private final String mainPosition;
    private final String mainFoot;
    private final String birthDate;
    private final String height;
    private final String marketValues;
    private final String endDateContract;
    private final String link;
}
