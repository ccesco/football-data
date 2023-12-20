package fr.cyrilcesco.footballdata.client.transfermarkt.jsoup;

import fr.cyrilcesco.footballdata.client.transfermarkt.model.Player;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class PagePlayer extends PageDocument {

    public PagePlayer(JsoupClient jsoupClient, String urlToConnect) throws IOException {
        super(jsoupClient, urlToConnect);
    }

    public Player getPlayer() {
        String mainPosition = getPage().selectXpath("//dt[contains(text(),'Main position:')]//following::dd[1]").text();
        String mainFoot = getPage().selectXpath("//span[text()='Foot:']//following::span[1]").text();
        String shirtNumber = getShirtNumber();
        String birthDate = getBirthDate();
        String birthPlace = getPage().selectXpath("//span[@itemprop='birthPlace']//span").attr("title");
        String nationality = getPage().selectXpath("//span[@itemprop='nationality']//img").attr("title");
        String height = getHeight();

        return Player.builder()
                .mainPosition(mainPosition.toLowerCase())
                .mainFoot(mainFoot.toLowerCase())
                .shirtNumber(shirtNumber)
                .birthDate(birthDate)
                .birthPlace(birthPlace)
                .nationality(nationality)
                .height(height)
                .build();
    }

    private String getBirthDate() {
        //Feb 25, 1999 to 25/02/1999
        String birthDate = getPage().selectXpath("//span[@itemprop='birthDate']").text().split("\\(")[0].trim();
        return LocalDate.parse(birthDate, DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(Locale.ENGLISH)).format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    private String getHeight() {
        return getPage().selectXpath("//span[@itemprop='height']").text().replace("m", "").trim();
    }

    private String getShirtNumber() {
        return getPage().selectXpath("//span[@class='data-header__shirt-number']").text().replace("#", "");
    }


}
