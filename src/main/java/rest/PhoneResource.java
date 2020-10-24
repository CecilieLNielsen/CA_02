/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.PersonFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import utils.EMF_Creator;

/**
 * REST Web Service
 *
 * @author abed
 */
@Path("phone")
public class PhoneResource {
    
    @Context
    private UriInfo context;
    
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();   
    //private static final PersonFacade FACADE =  (EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPhoneByID(@PathParam("id") int id){
        return GSON.toJson("return phone");
    }
    
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPhoneAll() {
        return GSON.toJson("return phone All");
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deletePhone(@PathParam("id") int id) {
        return GSON.toJson("return Phone");
    }

}
