package fr.cyrilcesco.footballdata.initservice.competitions.model;

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
public class Team {

    private final String id;
    private final String name;

}
