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
    public void addOccupant(Person person) {
    if (isFull()) {
        System.out.println("Shelter is full");
        return;
    }
    
    for (int i = 0; i < occupants.length; i++) {
        if (occupants[i] == null) {
            occupants[i] = person;
            break;
        }
    }
    }

    public int getCurrentOccupancy() {
    int count = 0;
    for (Person p : occupants) {
        if (p != null) count++;
    }
    return count;
    }

    public boolean isFull() {
        return getCurrentOccupancy() >= capacity;
    }

}