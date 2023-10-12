package fr.cyrilcesco.footballdata.client.transfermarkt.exception;

public class TransfermarktErrorParsing extends RuntimeException {

    public TransfermarktErrorParsing(String element) {
        super("Error parsing " + element);
    }
}
