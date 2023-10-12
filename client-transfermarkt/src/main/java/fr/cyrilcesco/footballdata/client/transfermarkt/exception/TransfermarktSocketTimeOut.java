package fr.cyrilcesco.footballdata.client.transfermarkt.exception;

public class TransfermarktSocketTimeOut extends RuntimeException {

    public TransfermarktSocketTimeOut(String elementId) {
        super("Socket Time Out for id " + elementId);
    }
}
