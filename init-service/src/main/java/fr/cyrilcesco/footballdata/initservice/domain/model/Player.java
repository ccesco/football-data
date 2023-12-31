package fr.cyrilcesco.footballdata.initservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@NoArgsConstructor
public class Player {

    private String name;
    private String id;
    private String mainPosition;
    private String mainFoot;
    private String shirtNumber;
    private String birthDate;
    private String birthPlace;
    private String nationality;
    private String height;
}
