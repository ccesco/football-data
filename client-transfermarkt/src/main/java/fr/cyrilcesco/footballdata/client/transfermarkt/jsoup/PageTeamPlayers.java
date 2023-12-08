package fr.cyrilcesco.footballdata.client.transfermarkt.jsoup;

import fr.cyrilcesco.footballdata.client.transfermarkt.exception.TransfermarktErrorParsing;
import fr.cyrilcesco.footballdata.client.transfermarkt.model.Player;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class PageTeamPlayers extends PageDocument{

    public PageTeamPlayers(JsoupClient jsoupClient, String urlToConnect) throws IOException {
        super(jsoupClient, urlToConnect);
    }

    public List<Player> getPlayers() {
        Elements players = getPage().selectXpath("//td[@class='hauptlink']//a[1]");

        return players
                .stream()
                .filter(element -> !element.text().isEmpty())
                .map(this::mapToPlayer)
                .toList();
    }

    private Player mapToPlayer(Element element) {
        String href = element.attr("href");
        return Player.builder().name(element.text()).link(href).id(getIdFromHref(href)).build();
    }

    private String getIdFromHref(String href) {
        String[] hrefSplitted = href.split("/");
        if(hrefSplitted.length == 5) {
            return hrefSplitted[4];
        }
        throw new TransfermarktErrorParsing(href);
    }
}
