package fr.cyrilcesco.footballdata.client.transfermarkt.jsoup;

import fr.cyrilcesco.footballdata.client.transfermarkt.exception.TransfermarktErrorParsing;
import fr.cyrilcesco.footballdata.client.transfermarkt.model.Team;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class PageCompetition extends PageDocument {


    public PageCompetition(JsoupClient jsoupClient, String urlToConnect, String urlToConnectSuffix) throws IOException {
        super(jsoupClient, urlToConnect, urlToConnectSuffix);
    }

    public String getCompetitionName() {
        return getPage().selectXpath("//div[@class='data-header__headline-container']//h1").text();
    }

    public List<Team> getTeams() {
        Elements teams = getPage().selectXpath("//td[@class='hauptlink no-border-links']//a");

        return teams
                .stream()
                .filter(element -> !element.text().isEmpty())
                .map(this::mapToTeam)
                .toList();
    }

    private Team mapToTeam(Element element) {
        String href = element.attr("href");
        return Team.builder()
                .name(element.text())
                .id(getIdFromHref(href))
                .seasonYear(getSeasonYear(href))
                .link(href).build();
    }

    private String getIdFromHref(String href) {
        String[] hrefSplitted = href.split("/");
        if (hrefSplitted.length > 5) {
            return hrefSplitted[4];
        }
        throw new TransfermarktErrorParsing(href);
    }

    private String getSeasonYear(String href) {
        String[] hrefSplitted = href.split("/");
        if (hrefSplitted.length >= 7) {
            return hrefSplitted[6];
        }
        throw new TransfermarktErrorParsing(href);
    }
}
