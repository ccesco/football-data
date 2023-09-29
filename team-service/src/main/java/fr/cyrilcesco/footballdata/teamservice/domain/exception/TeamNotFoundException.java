package fr.cyrilcesco.footballdata.teamservice.domain.exception;

public class TeamNotFoundException extends RuntimeException {

    public TeamNotFoundException(String identifiant) {
        super("Team with identifiant "+identifiant+" not found");
    }
}
