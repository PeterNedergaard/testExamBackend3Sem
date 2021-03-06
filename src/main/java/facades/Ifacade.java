package facades;

import entities.Boat;
import entities.Harbour;
import entities.Owner;

import java.util.List;

public interface Ifacade {

    List<Owner> getAllOwners();

    List<Harbour> getAllHarbours();

    List<Boat> getAllBoats();

    List<Boat> getBoatsByHarbour(Harbour harbour);

    List<Owner> getOwnersByBoat(Boat boat);

    Boat createBoat(String brand, String make, String name, String image);

    Harbour setBoatHarbour(Boat boat, Harbour harbour);

    Boat updateBoat(Boat boat,Owner owner, Harbour harbour);

    Boat deleteBoat(Boat boat);

    Harbour getHarbourByName(String name);

    Boat getBoatByName(String name);

}
