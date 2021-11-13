/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.RenameMeDTO;
import entities.RenameMe;
import entities.Station;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/**
 *
 * @author tha
 */
public class Populator {
    public static void populate() throws FileNotFoundException, IOException{
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        
        BufferedReader lineReader = new BufferedReader(new FileReader("stations.csv"));
        String lineText = null;

        

        lineReader.readLine();
        em.getTransaction().begin();
        while ((lineText = lineReader.readLine()) != null) {
            String[] data = lineText.split(",");
            String[] courseName = data[0].split(";");
            
            em.persist(new Station(Integer.parseInt(courseName[0]),courseName[1]));
            
            
        }
        em.getTransaction().commit();
        lineReader.close();
    }
    
    public static void main(String[] args) {
        try {
            populate();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
