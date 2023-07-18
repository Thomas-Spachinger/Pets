import java.util.HashMap;

public class House {
    private int houseID;
    private int livingSpace;
    private HashMap<Integer, Person> persons;

    public House(){};

    public House(int livingSpace) {
        this.houseID = houseID;
        this.livingSpace = livingSpace;
    }

    public House(int houseID, int livingSpace, HashMap<Integer, Person> persons) {
        this.houseID = houseID;
        this.livingSpace = livingSpace;
        this.persons = persons;
    }

    public House(int houseID, int livingSpace) {
        this.houseID = houseID;
        this.livingSpace = livingSpace;
        persons = new HashMap<>();
    }

    public int getHouseID() {
        return houseID;
    }
    public void setLivingSpace(int livingSpace) {
        this.livingSpace = livingSpace;
    }

    public HashMap<Integer, Person> getHousePersons() {
        return persons;
    }

    public void setPersons(HashMap<Integer, Person> persons) {
        this.persons = persons;
    }

    @Override
    public String toString() {
        return "House{" + "houseID=" + houseID + ", livingSpace=" + livingSpace + '}';
    }
}