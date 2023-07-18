import java.sql.*;
import java.util.*;

public class SQLCommands implements HouseholdDao {

    static private Map<Integer, House> houses;
    static private Map<Integer, Person> persons;
    static private Map<Integer, Pet> pets;
    static Connection connection = ConnectionSingleton.getInstance();

    public SQLCommands() {
        houses = new HashMap<>();
        persons = new HashMap<>();
        pets = new HashMap<>();
    }

    @Override
    public House addHouse(int size) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO house (livingSpace) VALUES ( ? );", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, size);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int id = (int) rs.getLong(1);
            System.out.println();
            System.out.println("House with " + size + " living space was added.");
            House a = new House( id, size);
            houses.put(id, a);
            System.out.println(houses);
            return a;
        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        return null;
    }


        @Override
    public Person addPerson(int houseID)  {
        try {

            PreparedStatement ps = connection.prepareStatement("INSERT INTO person (houseID) VALUES ( ? );", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, houseID);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int id = (int) rs.getLong(1);
            System.out.println();
            System.out.println("Person was added to House " + houseID);
            Person a = new Person(id, getHouse(houseID));
            persons.put(id, a);
            a.getHouse().getHousePersons().put(id, a);
            return a;

        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Pet addPet(int personID) throws SQLException {
        try {

            PreparedStatement ps = connection.prepareStatement("INSERT INTO pet (personID) VALUES ( ? );", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, personID);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int id = (int) rs.getLong(1);
            System.out.println();
            System.out.println("Pet was added to Person with ID " + personID);
            Pet a = new Pet(id, getPerson(personID));
            pets.put(id, a);
            a.getOwner().getPets().put(id, a);
            return a;

        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteHouse(int id) throws SQLException {
        try {
            Statement statement = connection.createStatement();
            statement.execute("DELETE from House where HouseID = " + id + ";");
            houses.remove(id);
            System.out.println("House with ID " + id + " deleted");
        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    @Override
    public void deletePerson(int id) throws SQLException {
        try {
            Statement statement = connection.createStatement();
            statement.execute("DELETE from Person where PersonID = " + id + ";");
            getPerson(id).getHouse().getHousePersons().remove(id);
            persons.remove(id);
            System.out.println("Person with ID " + id + " deleted");
        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    @Override
    public void deletePet(int id) throws SQLException {
        try {
            Statement statement = connection.createStatement();
            statement.execute("DELETE from Pet where PetID = " + id + ";");
            getPet(id).getOwner().getPets().remove(id);
            pets.remove(id);
            System.out.println("Pet with ID " + id + " deleted");
        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    @Override
    public House getHouse(int id) throws SQLException {

        try {
            if (houses.containsKey(id)) {
                return houses.get(id);
            } else {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from House where HouseID = " + id + ";");
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(resultSet.getString(1)).append(" ");
                stringBuilder.append(resultSet.getString(2)).append("; ");
                System.out.println(stringBuilder);


                House temp = new House(resultSet.getInt(1), resultSet.getInt(2), getPersonByHouseID(id));
                houses.put(id, temp);
                return temp;
            }

        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Person getPerson(int id) throws SQLException {

        try {
            if (persons.containsKey(id)) {
                return persons.get(id);
            } else {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from Person where PersonID = " + id + ";");
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(resultSet.getString(1)).append(" ");
                stringBuilder.append(resultSet.getString(2)).append("; ");
                System.out.println(stringBuilder);
                House n = getHouse(resultSet.getInt(2));
                Person temp = new Person(resultSet.getInt(1), n);
                persons.put(id, temp);
                n.getHousePersons().put(id, temp);
                return temp;
            }
        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Pet getPet(int id) throws SQLException {

        try {
            if (pets.containsKey(id)) {
                return pets.get(id);
            } else {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from Pet where PetID = " + id + ";");
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(resultSet.getString(1)).append(" ");
                stringBuilder.append(resultSet.getString(2)).append("; ");
                System.out.println(stringBuilder);
                Person n = getPerson(resultSet.getInt(2));
                Pet temp = new Pet(resultSet.getInt(1), n);
                pets.put(id, temp);
                n.getPets().put(id,temp);
                return temp;
            }
        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HashMap<Integer, Pet> getPetByOwner(int ownerID) throws SQLException {
        HashMap<Integer, Pet> tempMap = new HashMap<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Pet where PersonID = " + ownerID + ";");
            StringBuilder stringBuilder = new StringBuilder();

            while (resultSet.next()) {
                stringBuilder.append(resultSet.getString(1)).append(" ");
                stringBuilder.append(resultSet.getString(2)).append("; ");
                Pet temp = new Pet(resultSet.getInt(1), getPerson(resultSet.getInt(2)));
                tempMap.put(resultSet.getInt(1), temp);
            }

            return tempMap;
        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HashMap<Integer, Person> getPersonByHouseID(int houseID) throws SQLException {
        HashMap<Integer, Person> tempMap = new HashMap<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from person where HouseID = " + houseID + ";");
            StringBuilder stringBuilder = new StringBuilder();

            while (resultSet.next()) {
                stringBuilder.append(resultSet.getString(1)).append(" ");
                stringBuilder.append(resultSet.getString(2)).append("; ");
                Person temp = new Person(resultSet.getInt(1), getHouse(resultSet.getInt(2)), getPetByOwner(resultSet.getInt(1)));
                tempMap.put(resultSet.getInt(1), temp);
            }
            return tempMap;
        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HashMap<Integer, House> getAllHouses() throws SQLException {
        HashMap<Integer, House> tempMap = new HashMap<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from House;");
            StringBuilder stringBuilder = new StringBuilder();

            while (resultSet.next()) {
                stringBuilder.append(resultSet.getString(1)).append(" ");
                stringBuilder.append(resultSet.getString(2)).append("; ");
                House temp = new House(resultSet.getInt(1), resultSet.getInt(2), getPersonByHouseID(resultSet.getInt(1)));
                tempMap.put(resultSet.getInt(1), temp);
            }
            return tempMap;
        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateHouse(int houseID, int size) throws SQLException {
        try {
            Statement statement = connection.createStatement();
            statement.execute("UPDATE House SET livingSpace = " + size + " where HouseID = " + houseID + ";");
            System.out.println("Houses size with ID " + houseID + " was changed to: " + size);
            House n = new House(houseID, size, getPersonByHouseID(houseID));
            houses.put(houseID, n);
        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    @Override
    public void updatePerson(int id, int houseID) throws SQLException {
        try {
            Statement statement = connection.createStatement();
            statement.execute("UPDATE Person SET houseID = " + houseID + " where PersonID = " + id + ";");
            System.out.println("HouseID of Person with Person ID " + id + " was changed to: " + houseID);
            Person n = new Person(id, persons.get(id).getHouse(), getPetByOwner(id));
            getHouse(houseID).getHousePersons().remove(id);
            getHouse(houseID).getHousePersons().put(id, n);
            persons.put(id, n);
        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    @Override
    public void updatePet(int id, int ownerID) throws SQLException {
        try {
            Statement statement = connection.createStatement();
            statement.execute("UPDATE Pet SET PersonID = " + ownerID + " where PetID = " + id + ";");
            System.out.println("OwnerID of Pet with Pet ID " + id + " was changed to: " + ownerID);
            getPet(id).getOwner().getPets().remove(id);
            getPet(id).setOwnerID(getPerson(ownerID));
            getPerson(ownerID).getPets().put(id, getPet(id));
        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }
}
