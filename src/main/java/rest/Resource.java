package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dtos.BoatDTO;
import dtos.HarbourDTO;
import dtos.OwnerDTO;
import entities.Boat;
import entities.Harbour;
import entities.Owner;
import entities.User;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import errorhandling.API_Exception;
import facades.Facade;
import utils.EMF_Creator;

/**
 * @author lam@cphbusiness.dk
 */
@Path("info")
public class Resource {
    
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    Facade facade = Facade.getFacade(EMF_Creator.createEntityManagerFactory());


    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String allUsers() {

        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery ("select u from User u",entities.User.class);
            List<User> users = query.getResultList();
            return "[" + users.size() + "]";
        } finally {
            em.close();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed("user")
    public String getFromUser() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("owners")
    public Response getAllOwners() {

        List<OwnerDTO> ownerDTOList = OwnerDTO.getOwnerDTOs(facade.getAllOwners());

        return Response
                .ok()
                .entity(gson.toJson(ownerDTOList))
                .build();

    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("boats/{harbourName}")
    public Response getBoatsByHarbourName(@PathParam("harbourName") String harbourName) {

        EntityManager em = EMF.createEntityManager();

        List<BoatDTO> boatDTOList = BoatDTO.getBoatDTOs(facade.getBoatsByHarbour(facade.getHarbourByName(harbourName)));

        return Response
                .ok()
                .entity(gson.toJson(boatDTOList))
                .build();

    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("ownersbyboat/{boatName}")
    public Response getOwnersByBoatName(@PathParam("boatName") String boatName) {

        EntityManager em = EMF.createEntityManager();

        List<OwnerDTO> ownerDTOList = OwnerDTO.getOwnerDTOs(facade.getOwnersByBoat(facade.getBoatByName(boatName)));

        return Response
                .ok()
                .entity(gson.toJson(ownerDTOList))
                .build();

    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("createboat")
    public Response createBoat(String jsonString) throws API_Exception {

        EntityManager em = EMF.createEntityManager();

        String brand;
        String make;
        String name;
        String image;

        try{
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            brand = json.get("brand").getAsString();
            make = json.get("make").getAsString();
            name = json.get("name").getAsString();
            image = json.get("image").getAsString();

        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Suplied", 400, e);
        }


        return Response
                .ok()
                .entity(new BoatDTO(facade.createBoat(brand,make,name,image)))
                .build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("setharbour")
    public Response setBoatHarbour(String jsonString) throws API_Exception {

        EntityManager em = EMF.createEntityManager();

        Boat boat;
        Harbour harbour;

        String boatName;
        String harbourName;

        try{
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();

            boatName = json.get("boatName").getAsString();
            harbourName = json.get("harbourName").getAsString();

        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Suplied", 400, e);
        }

        boat = facade.getBoatByName(boatName);
        harbour = facade.getHarbourByName(harbourName);


        return Response
                .ok()
                .entity(new HarbourDTO(facade.setBoatHarbour(boat,harbour)))
                .build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("deleteboat")
    public Response deleteBoat(String jsonString) throws API_Exception {

        EntityManager em = EMF.createEntityManager();

        Boat boat;
        String boatName;

        try{
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();

            boatName = json.get("boatName").getAsString();

        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Suplied", 400, e);
        }

        boat = facade.getBoatByName(boatName);


        return Response
                .ok()
                .entity(facade.deleteBoat(boat))
                .build();
    }



}