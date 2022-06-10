package facades;

import entities.Boat;
import entities.Harbour;
import entities.Owner;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class Facade implements Ifacade {

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static Facade instance;

    public Facade() {
    }

    public static Facade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            em = emf.createEntityManager();
            instance = new Facade();
        }
        return instance;
    }


    @Override
    public List<Owner> getAllOwners() {

        return em.createQuery("SELECT o FROM Owner o",Owner.class).getResultList();
    }

    @Override
    public List<Boat> getBoatsByHarbour(Harbour harbour) {

        return harbour.getBoatList();
    }

    @Override
    public List<Owner> getOwnersByBoat(Boat boat) {

        return boat.getOwnerList();
    }

    @Override
    public Boat createBoat(String brand, String make, String name, String image) {
        Boat boat = new Boat(brand,make,name,image);

        em.getTransaction().begin();
        em.persist(boat);
        em.getTransaction().commit();

        return boat;
    }

    @Override
    public Harbour setBoatHarbour(Boat boat, Harbour harbour) {
        boat.setHarbour(harbour);

        em.merge(boat);

        return boat.getHarbour();
    }

    @Override
    public Boat updateBoat(Boat boat,Owner owner, Harbour harbour) {
        return null;
    }

    @Override
    public String deleteBoat(Boat boat) {
        em.remove(boat);

        if (em.find(Boat.class,boat.getId()) == null){
            return "deleted";
        } else {
            return "not deleted";
        }
    }



    @Override
    public Boat getBoatByName(String name) {
        Boat boat;

        boat = em.createQuery("SELECT b FROM Boat b WHERE b.name = :name",Boat.class)
                .setParameter("name",name).getSingleResult();

        return boat;
    }

    @Override
    public Harbour getHarbourByName(String name) {
        Harbour harbour;

        harbour = em.createQuery("SELECT h FROM Harbour h WHERE h.name = :name",Harbour.class)
                .setParameter("name",name).getSingleResult();

        return harbour;
    }
}
