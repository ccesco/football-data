package fr.cyrilcesco.footballdata.client.transfermarkt.jsoup;

import org.jsoup.nodes.Document;

import java.io.IOException;

public class PageDocument {

    private final Document page;

    public PageDocument(JsoupClient jsoupClient, String urlToConnect, String urlToConnectSuffix) throws IOException {
        this.page = jsoupClient.getDocument(urlToConnect + urlToConnectSuffix);
    }

    public PageDocument(JsoupClient jsoupClient, String urlToConnect) throws IOException {
        this.page = jsoupClient.getDocument(urlToConnect);
    }

    public Document getPage() {
        return page;
    }
}
