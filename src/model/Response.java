package model;

import java.time.*;

public class Response {
    /* ATTRIBUTES */
    private int responseID;
    private int disasterID;
    private int shelterID;
    private int employeeID;
    private String responseType;            // RS, E, MA, RL, RO
    private LocalDateTime responseStart;    // YYYY-MM-DD HH:MI:SS       
    private LocalDateTime responseEnd;

    
    public Response() {}

    public int getResponseID() {
        return responseID;
    }

    public void setResponseID(int responseID) {
        this.responseID = responseID;
    }
}