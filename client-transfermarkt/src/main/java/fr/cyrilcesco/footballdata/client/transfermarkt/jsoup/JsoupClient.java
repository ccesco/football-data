package fr.cyrilcesco.footballdata.client.transfermarkt.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class JsoupClient {

    public PageCompetition getDocument(String urlToConnect, String urlToConnectSuffix) throws IOException {
        Document document = Jsoup.connect(urlToConnect + urlToConnectSuffix)
                .timeout(7000)
                .get();
        return new PageCompetition(document);
    }


}
