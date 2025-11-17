package model;

public class Shelter {
    /* ATTRIBUTES */
    private int shelterID;
    private String shelterName;
    private String address;
    private int capacity;
    private Person[] occupants;
    private boolean status;             // could also be enum

    /* CONSTRUCTOR */
    public Shelter() {

    }

    /* GETTERS & SETTERS */
    public int getSheleterID() {
        return shelterID;
    }

    public String getShelterName() {
        return shelterName;
    }

    public String getAddress() {
        return address;
    }

    public int getCapacity() {
        return capacity;
    }

    public Person[] getOccupants() {
        return occupants;
    }

    public boolean getStatus() {
        return status;
    }

    public void setSheleterID(int shelterID) {
        this.shelterID = shelterID;
    }

    public void setShelterName(String shelterName) {
        this.shelterName = shelterName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setOccupants(Person[] occupants) {
        this.occupants = occupants;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    /* METHODS  Might change this */

}