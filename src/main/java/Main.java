import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        SQLCommands test = new SQLCommands();
        test.addHouse(104);
        test.addHouse(105);
        test.addHouse(106);
        test.addHouse(107);

        test.addPerson(1);
        test.addPerson(1);
        test.addPerson(1);
        test.addPerson(2);
        test.addPerson(3);

        test.addPet(1);
        test.addPet(1);
        test.addPet(2);
        test.addPet(3);

        System.out.println(test.getAllHouses());

        test.deleteHouse(4);
        test.deletePerson(5);

        test.deletePet(4);
        System.out.println(test.getPetByOwner(3));

        System.out.println(test.getAllHouses());/**/

        test.updateHouse(1, 204);
        System.out.println(test.getAllHouses());

        System.out.println(test.getPersonByHouseID(1));
        System.out.println(test.getPersonByHouseID(2));
        test.updatePerson(1,2);
        System.out.println(test.getPersonByHouseID(1));
        System.out.println(test.getPersonByHouseID(2));

        System.out.println(test.getPet(1));
        System.out.println(test.getPetByOwner(1));

        test.updatePet(1,4);

        System.out.println(test.getPetByOwner(1));
        System.out.println(test.getPetByOwner(4));

    }
}
