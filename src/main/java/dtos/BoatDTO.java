package dtos;

import entities.Boat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BoatDTO {

    private Long id;
    private String brand;
    private String make;
    private String name;
    private String image;


    public BoatDTO(Long id, String brand, String make, String name, String image) {
        this.id = id;
        this.brand = brand;
        this.make = make;
        this.name = name;
        this.image = image;
    }


    public BoatDTO(Boat boat) {
        this.id = boat.getId();
        this.brand = boat.getBrand();
        this.make = boat.getMake();
        this.name = boat.getName();
        this.image = boat.getImage();
    }


    public static List<BoatDTO> getBoatDTOs(List<Boat> boatList){
        List<BoatDTO> boatDTOList = new ArrayList<>();

        for (Boat b : boatList) {
            boatDTOList.add(new BoatDTO(b));
        }

        return boatDTOList;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoatDTO boatDTO = (BoatDTO) o;
        return Objects.equals(brand, boatDTO.brand) && Objects.equals(make, boatDTO.make) && Objects.equals(name, boatDTO.name) && Objects.equals(image, boatDTO.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, make, name, image);
    }
}
