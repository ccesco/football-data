package fr.cyrilcesco.footballdata.initservice.error;

public class SendMessageError extends RuntimeException {

    public SendMessageError(String element) {
        super("Error sending " + element);
    }
}
