package dtos;

import entities.Owner;

import java.util.ArrayList;
import java.util.List;

public class OwnerDTO {

    private Long id;
    private String name;
    private String address;
    private String phone;


    public OwnerDTO(Long id, String name, String address, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }


    public OwnerDTO(Owner owner) {
        this.id = owner.getId();;
        this.name = owner.getName();
        this.address = owner.getAddress();
        this.phone = owner.getPhone();
    }


    public static List<OwnerDTO> getOwnerDTOs(List<Owner> ownerList){
        List<OwnerDTO> ownerDTOList = new ArrayList<>();

        for (Owner o : ownerList) {
            ownerDTOList.add(new OwnerDTO(o));
        }

        return ownerDTOList;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
