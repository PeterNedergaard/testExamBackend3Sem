package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Harbour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private String address;
    private String capacity;

    @OneToMany(mappedBy = "harbour")
    private List<Boat> boatList = new ArrayList<>();


    public Harbour(String name, String address, String capacity) {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
    }

    public Harbour() {
    }



    public void addBoat(Boat boat){
        this.boatList.add(boat);
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public List<Boat> getBoatList() {
        return boatList;
    }

    public void setBoatList(List<Boat> boatList) {
        this.boatList = boatList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Harbour harbour = (Harbour) o;
        return Objects.equals(name, harbour.name) && Objects.equals(address, harbour.address) && Objects.equals(capacity, harbour.capacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, capacity);
    }
}
