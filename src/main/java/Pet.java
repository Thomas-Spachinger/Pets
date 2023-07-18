public class Pet {
    private int petID;
    private Person ownerID;

    public Pet(int petID, Person ownerID) {
        this.petID = petID;
        this.ownerID = ownerID;
    }

    public void setOwnerID(Person ownerID) {
        this.ownerID = ownerID;
    }

    public int getPetID() {
        return petID;
    }

    public Person getOwner() {
        return ownerID;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "petID=" + petID +
                '}';
    }
}
