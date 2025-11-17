package model;

import java.util.*;;

public class Disaster {
    /* ATTRIBUTES */
    private int disasterID;
    private String disasterType;
    private Date dateOccurred;
    private String location;
    private String severity;        // L, M, H, S
    private int casualties;
    private int damages;

    public Disaster() {}

    public int getDisasterID() {
        return disasterID;
    }

    public void setDisasterID(int disasterID) {
        this.disasterID = disasterID;
    }
}