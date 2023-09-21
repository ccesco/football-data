package fr.cyrilcesco.footballdata.playersservice.domain.model;

public class Player {
    private String id;
    private String lastName;
    private String firstName;
    private MainPosition mainPosition;
    private MainFoot mainFoot;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public MainPosition getMainPosition() {
        return mainPosition;
    }

    public void setMainPosition(MainPosition mainPosition) {
        this.mainPosition = mainPosition;
    }

    public MainFoot getMainFoot() {
        return mainFoot;
    }

    public void setMainFoot(MainFoot mainFoot) {
        this.mainFoot = mainFoot;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
