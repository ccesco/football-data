package fr.cyrilcesco.footballdata.initservice.competitions.model;

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
public class Team {

    private String id;
    private String name;

}
