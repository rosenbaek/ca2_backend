/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Station;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author christianrosenbaek
 */
public class StationsDTO {
    private List<StationDTO> stations = new ArrayList<>();

    public StationsDTO(List<Station> s) {
        s.forEach(station->stations.add(new StationDTO(station)));
    }

    public List<StationDTO> getStations() {
        return stations;
    }
    
    
    
}
