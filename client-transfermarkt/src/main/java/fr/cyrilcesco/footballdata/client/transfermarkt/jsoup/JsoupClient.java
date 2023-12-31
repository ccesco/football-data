package fr.cyrilcesco.footballdata.client.transfermarkt.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class JsoupClient {

    public Document getDocument(String urlToConnect) throws IOException {
        return Jsoup.connect(urlToConnect)
                .timeout(7000)
                .get();
    }
}
