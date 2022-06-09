package rest;

import entities.*;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//Uncomment the line below, to temporarily disable this test
//@Disabled

public class ResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    @BeforeEach
    public void setUp() {
//        EntityManager em = emf.createEntityManager();
//
//        User user = new User("user", "test123");
//        User admin = new User("admin", "test123");
//        User both = new User("user_admin", "test123");
//
//        Role userRole = new Role("user");
//        Role adminRole = new Role("admin");
//
//        Boat boat1 = new Boat("Brand1","Make1","Boat1","ImageURL1");
//        Boat boat2 = new Boat("Brand2","Make2","Boat2","ImageURL2");
//        Boat boat3 = new Boat("Brand3","Make3","Boat3","ImageURL3");
//
//        Harbour harbour1 = new Harbour("Harbour1","Address1","20");
//        Harbour harbour2 = new Harbour("Harbour2","Address2","15");
//
//        Owner owner1 = new Owner("Owner1","HomeAddress1","12345678");
//        Owner owner2 = new Owner("Owner2","HomeAddress2","87654321");
//        Owner owner3 = new Owner("Owner3","HomeAddress3","43215678");
//
//
//        owner1.addBoat(boat1);
//        owner2.addBoat(boat2);
//        owner3.addBoat(boat3);
//        owner3.addBoat(boat1);
//
//        boat1.setHarbour(harbour1);
//        boat2.setHarbour(harbour2);
//        boat3.setHarbour(harbour1);
//
//
//        user.addRole(userRole);
//        admin.addRole(adminRole);
//        both.addRole(userRole);
//        both.addRole(adminRole);
//
//        try {
//            em.getTransaction().begin();
//
//            em.persist(userRole);
//            em.persist(adminRole);
//            em.persist(user);
//            em.persist(admin);
//            em.persist(both);
//
//            em.persist(boat1);
//            em.persist(boat2);
//            em.persist(boat3);
//
//            em.persist(harbour1);
//            em.persist(harbour2);
//
//            em.persist(owner1);
//            em.persist(owner2);
//            em.persist(owner3);
//
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }

    }

    @Test
    public void testServerIsUp() {
        given().when().get("/info").then().statusCode(200);
    }

}
