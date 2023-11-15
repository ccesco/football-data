package fr.cyrilcesco.footballdata.initservice.competitions.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@NoArgsConstructor
public class Competition {

    private String id;
    private String name;
    private String seasonYear;
    private List<Team> teams;

}
