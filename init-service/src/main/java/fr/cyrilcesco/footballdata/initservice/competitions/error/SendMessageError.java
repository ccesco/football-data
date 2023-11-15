package fr.cyrilcesco.footballdata.initservice.competitions.error;

public class SendMessageError extends RuntimeException {

    public SendMessageError(String element) {
        super("Error sending " + element);
    }
}
