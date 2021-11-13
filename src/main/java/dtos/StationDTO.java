/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.RenameMe;
import entities.Role;
import entities.Station;
import entities.User;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author christianrosenbaek
 */
public class StationDTO {
    private Integer station_id;
    private String station_name;

    public StationDTO(Station station) {
        this.station_id = station.getStationId();
        this.station_name = station.getStationName();
    }

    public static List<StationDTO> getDtos(List<Station> station) {
        List<StationDTO> stationDTOs = new ArrayList();
        station.forEach(sta -> stationDTOs.add(new StationDTO(sta)));
        return stationDTOs;
    }
    
    
    public Station getEntity() {
        return new Station(this.station_id, this.station_name);
    }
    
    
    public Integer getStation_id() {
        return station_id;
    }

    public void setStation_id(Integer station_id) {
        this.station_id = station_id;
    }

    public String getStation_name() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }
    
    
    
    
}
