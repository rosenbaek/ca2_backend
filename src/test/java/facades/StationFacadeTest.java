/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.StationsDTO;
import dtos.user.UserDTO;
import entities.Role;
import entities.Station;
import entities.User;
import errorhandling.API_Exception;
import errorhandling.NotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    private User user2;
    private Station station1;
    private Role adminRole;
    
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
            adminRole = new Role("admin"); 
            Station station = new Station(123,"new station");
            station1 = new Station(1,"new station1");
            user = new User("user", "test");
            user.addRole(userRole);
            user.addStation(station);
            
            user2 = new User("user2", "test2");
            user2.addRole(userRole);
            user2.addStation(station);
            user2.addStation(station1);
            
            em.persist(adminRole);
            em.persist(userRole);
            em.persist(station);
            em.persist(station1);
            em.persist(user);
            em.persist(user2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    
    @Test
    public void testGetStations() {
        System.out.println("getStations");
        StationFacade instance = StationFacade.getStationFacade(emf);
        int expResult = 2;
        StationsDTO stationsDTO = instance.getStations();
        assertEquals(expResult, stationsDTO.getStations().size());
       
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
    public void testDeleteStationFromUser() throws API_Exception {
        StationFacade instance = StationFacade.getStationFacade(emf);
        int expResult = 0;
        UserDTO userDTO = instance.deleteStationFromUser(user.getUserName(), 123);
        assertEquals(expResult, userDTO.getStations().size());
    }
    
    @Test
    public void testGetStationsByUser() {
        StationFacade instance = StationFacade.getStationFacade(emf);
        int expResult = 2;
        StationsDTO stationsDTO = instance.getStationsByUser(user2.getUserName());
        assertEquals(expResult, stationsDTO.getStations().size());
    }
    
    @Test
    public void testUpdateUserRole() throws NotFoundException {
        UserFacade instance = UserFacade.getUserFacade(emf); 
        user.addRole(adminRole);
        UserDTO userDTO = instance.updateUser(user);
        assertEquals(2, userDTO.getRoles().size());
    }
    
    @Test
    public void testUpdateUserRoleThatNotExist() throws NotFoundException {
        UserFacade instance = UserFacade.getUserFacade(emf);
        Role newRole = new Role("newRole");
        user.addRole(newRole);
        Throwable exception = assertThrows(NotFoundException.class, () -> instance.updateUser(user));
        assertEquals("Role doesn't exist", exception.getMessage());
    }
    
}
