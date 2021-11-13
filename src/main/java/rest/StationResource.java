/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dtos.StationDTO;
import dtos.StationsDTO;
import dtos.user.UserDTO;
import errorhandling.API_Exception;
import facades.StationFacade;
import facades.UserFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static rest.UserResource.USER_FACADE;
import utils.EMF_Creator;

/**
 * REST Web Service
 *
 * @author christianrosenbaek
 */
@Path("station")
public class StationResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    public static final StationFacade STATION_FACADE = StationFacade.getStationFacade(EMF);
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
   

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getStations(@PathParam("username") String username) throws API_Exception, Exception {
        StationsDTO stationsDTO = STATION_FACADE.getStations();
        return Response.ok().entity(gson.toJson(stationsDTO)).build();
    }
    
    @GET
    @Path("/{username}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response getStationsByUser(@PathParam("username") String username) throws API_Exception, Exception {
        StationsDTO stationsDTO = STATION_FACADE.getStationsByUser(username);
        return Response.ok().entity(gson.toJson(stationsDTO)).build();
    }
   
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response addStationToUser(String jsonString) throws API_Exception, Exception {
        JsonParser parser = new JsonParser();
        JsonObject o = parser.parse(jsonString).getAsJsonObject();
        String username = o.get("username").getAsString();
        int stationID = o.get("station_id").getAsInt();
  
        UserDTO userDTO = STATION_FACADE.addStationToUser(username,stationID);
        return Response.ok().entity(gson.toJson(userDTO)).build();
    }
    
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteStationFromUser(String jsonString) throws API_Exception, Exception {
        JsonParser parser = new JsonParser();
        JsonObject o = parser.parse(jsonString).getAsJsonObject();
        String username = o.get("username").getAsString();
        int stationID = o.get("station_id").getAsInt();
        UserDTO userDTO = STATION_FACADE.deleteStationFromUser(username, stationID);
        return Response.ok().entity(gson.toJson(userDTO)).build();
    }
}
