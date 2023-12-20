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
public class TransfermarktPlayerResponse {

    private final String id;
    private final String name;
    private final String mainPosition;
    private final String mainFoot;
    private final String shirtNumber;
    private final String birthDate;
    private final String birthPlace;
    private final String nationality;
    private final String height;
}
