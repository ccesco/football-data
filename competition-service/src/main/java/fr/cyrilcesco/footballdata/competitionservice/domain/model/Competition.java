package fr.cyrilcesco.footballdata.competitionservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Competition {
    private String id;
    private String name;
    private String seasonYear;
    private List<Team> teams;
}
