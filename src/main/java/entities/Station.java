/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.User;
import errorhandling.API_Exception;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 *
 * @author christianrosenbaek
 */
@Entity
@Table(name = "stations")
public class Station implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "station_id")
    private Integer stationId;
    
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 105)
    @Column(name = "station_name")
    private String stationName;
    
    
    @ManyToMany(mappedBy = "stations")
    private List<User> users; 

    public Station() {
    }

    public Station(Integer stationId) {
        this.stationId = stationId;
    }

    public Station(Integer stationId, String stationName) {
        this.stationId = stationId;
        this.stationName = stationName;
        this.users = new ArrayList<>();
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    
    public List<User> getUserList() {
        return users;
    }

    public void addUser(User user) {
        this.users.add(user);
    }
    
    public void deleteUser(User user) throws API_Exception{
        if (this.users.contains(user)) {
            this.users.remove(user);
        } else {
            throw new API_Exception("Station not connected to user");
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.stationId);
        hash = 97 * hash + Objects.hashCode(this.stationName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Station other = (Station) obj;
        if (!Objects.equals(this.stationName, other.stationName)) {
            return false;
        }
        if (!Objects.equals(this.stationId, other.stationId)) {
            return false;
        }
        return true;
    }

    
    
    
}
