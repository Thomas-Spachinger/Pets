import java.util.HashMap;

public class Person {
    private int personID;
    private House houseID;

    private HashMap<Integer, Pet> pets;

    public Person(int personID, House houseID) {
        this.personID = personID;
        this.houseID = houseID;
        pets = new HashMap<>();
    }

    public Person(int personID, House houseID, HashMap<Integer, Pet> pets) {
        this.personID = personID;
        this.houseID = houseID;
        this.pets = pets;
    }

    public void setHouseID(House houseID) {
        this.houseID = houseID;
    }

    public int getPersonID() {
        return personID;
    }

    public HashMap<Integer, Pet> getPets() {
        return pets;
    }

    public void setPets(HashMap<Integer, Pet> pets) {
        this.pets = pets;
    }

    public House getHouse() {
        return houseID;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personID=" + personID +'}';
    }
}
