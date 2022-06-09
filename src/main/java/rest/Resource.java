package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.BoatDTO;
import dtos.HarbourDTO;
import dtos.OwnerDTO;
import entities.Harbour;
import entities.Owner;
import entities.User;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.*;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

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
    @Path("boats/{harbourId}")
    public Response getBoatsByHarbourId(@PathParam("harbourId") Long harbourId) {

        EntityManager em = EMF.createEntityManager();

        List<BoatDTO> boatDTOList = BoatDTO.getBoatDTOs(facade.getBoatsByHarbour(em.find(Harbour.class, harbourId)));

        return Response
                .ok()
                .entity(gson.toJson(boatDTOList))
                .build();

    }



}