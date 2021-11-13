/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.StationDTO;
import dtos.user.UserDTO;
import entities.RenameMe;
import entities.Role;
import entities.Station;
import entities.User;
import errorhandling.API_Exception;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;
/**
 *
 * @author christianrosenbaek
 */
public class StationFacadeTest extends TestCase {
    private static EntityManagerFactory emf;
    private static StationFacade facade;
    private User user;
    private Station station1;
    
    public StationFacadeTest() {
        
    }
    
    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = StationFacade.getStationFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }
    
    @BeforeEach
    protected void setUp() throws Exception {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();
            em.createQuery("delete from Station").executeUpdate();
            

            Role userRole = new Role("user"); 
            Station station = new Station(123,"new station");
            station1 = new Station(1,"new station1");
            user = new User("user", "test");
            user.addRole(userRole);
            user.addStation(station);
            
            
            em.persist(userRole);
            em.persist(station);
            em.persist(station1);
            
            em.persist(user);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    

    /**
     * Test of getStations method, of class StationFacade.
     */
    @Test
    public void testGetStations() {
        System.out.println("getStations");
        StationFacade instance = StationFacade.getStationFacade(emf);
        int expResult = 2;
        int result = instance.getStations().size();
        assertEquals(expResult, result);
       
    }

    @Test
    public void testAddStationToUser() throws API_Exception {
        System.out.println("AddStationToUser");
        StationFacade instance = StationFacade.getStationFacade(emf);
        int expResult = 2;
        UserDTO userDTO = instance.addStationToUser(user.getUserName(), 1);
        assertEquals(expResult,userDTO.getStations().size());
    }
    
    @Test
    public void testUpdateUserRole() {
        UserFacade instance = UserFacade.getUserFacade(emf);
        Role newRole = new Role("newRole");
        user.addRole(newRole);
        instance.updateUser(user);
        assertEquals(2, user.getRoleList().size());
    }
    
}
