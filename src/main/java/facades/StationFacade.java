/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.RenameMeDTO;
import dtos.StationDTO;
import dtos.user.UserDTO;
import entities.RenameMe;
import entities.Station;
import entities.User;
import errorhandling.API_Exception;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import security.errorhandling.AuthenticationException;

/**
 *
 * @author christianrosenbaek
 */
public class StationFacade {
    private static EntityManagerFactory emf;
    private static StationFacade instance;

    public StationFacade() {
    }
    
    
    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static StationFacade getStationFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new StationFacade();
        }
        return instance;
    }
    
    public List<StationDTO> getStations() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Station> query = em.createQuery("SELECT s FROM Station s", Station.class);
        List<Station> stations = query.getResultList();
        return StationDTO.getDtos(stations);
    }
    
    public List<StationDTO> getStationsByUser(User user) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Station> query = em.createQuery("SELECT s FROM Station s INNER JOIN s.users u WHERE u.userName = :username", Station.class);
        query.setParameter("username", user.getUserName());
        List<Station> stations = query.getResultList();
        return StationDTO.getDtos(stations);
    }
    
    public UserDTO addStationToUser(String username, int stationID) throws API_Exception {
        EntityManager em = emf.createEntityManager();
        User user;
        Station station;
        try {
            user = em.find(User.class, username);
            station = em.find(Station.class, stationID);
            if (user == null || station == null) {
                throw new API_Exception("Invalid user name or station id");
            }
            user.addStation(station);
            em.getTransaction().begin();
            user = em.merge(user);
            em.getTransaction().commit();
        } finally {
            em.close();

        }
        return new UserDTO(user);
    }
    
    public UserDTO deleteStationFromUser(String username, int stationID) throws API_Exception{
        EntityManager em = emf.createEntityManager();
        User user;
        Station station;
        try {
            user = em.find(User.class, username);
            station = em.find(Station.class, stationID);
            if (user == null || station == null) {
                throw new API_Exception("Invalid user name or station id");
            }
            user.deleteStation(station);
            em.getTransaction().begin();
            user = em.merge(user);
            em.getTransaction().commit();
        } finally {
            em.close();

        }
        return new UserDTO(user);
    }
}
