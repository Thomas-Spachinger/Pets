import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface HouseholdDao {
    House addHouse(int size)
            throws SQLException;
    Person addPerson(int houseID)
            throws SQLException;
    Pet addPet(int personID)
            throws SQLException;
    void deleteHouse(int id)
            throws SQLException;
    void deletePerson(int id)
            throws SQLException;
    void deletePet(int id)
            throws SQLException;
    House getHouse(int id)
            throws SQLException;
    Person getPerson(int id)
            throws SQLException;
    Pet getPet(int id)
            throws SQLException;
    void updateHouse(int id, int size)
            throws SQLException;
    void updatePerson(int id, int houseID)
            throws SQLException;
    void updatePet(int id, int ownerID)
            throws SQLException;
    HashMap<Integer,Pet> getPetByOwner(int ownerID)
            throws SQLException;

    HashMap<Integer,Person> getPersonByHouseID(int personID)
            throws SQLException;
    HashMap<Integer, House> getAllHouses()
            throws SQLException;

}
