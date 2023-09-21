package fr.cyrilcesco.footballdata.playersservice.domain.exception;

public class PlayerNotFoundException extends RuntimeException {

    public PlayerNotFoundException(String identifiant) {
        super("Player with identifiant "+identifiant+" not found");
    }
}
