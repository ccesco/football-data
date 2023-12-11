package fr.cyrilcesco.footballdata.client.transfermarkt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class TransfermarktTeamResponse {

    private final String id;
    private final String name;
    private final String seasonYear;
    private final List<Player> players;
}
