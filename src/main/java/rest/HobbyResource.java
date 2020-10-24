package rest;

import DTO.HobbyDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.HobbyFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
@Path("hobby")
public class HobbyResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();   
    private static final HobbyFacade FACADE =  HobbyFacade.getFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    @Context
    private UriInfo context;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String forTest(){
        return "\"{msg}\" : \"{This is working}\"";
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getHobbyById(@PathParam("id") int id) {
        return GSON.toJson(FACADE.getHobbyById(id));
    }
    
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllHobbies() {
        return GSON.toJson(FACADE.getAllHobbies());
    }
    
    @GET
    @Path("/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getHobbyByName(@PathParam("name") String name) {
        return GSON.toJson(FACADE.getHobbyByName(name));
    }
    
    @GET
    @Path("/count/{hobby}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCountOfPersonWithHobby(@PathParam("hobby") String hobby) {
        return GSON.toJson(FACADE.getCountOfPersonsWithHobby(hobby));
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addHobby(HobbyDTO hDTO) {
        return GSON.toJson(FACADE.addHobby(hDTO));
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String editHobby(HobbyDTO hDTO) {
        return GSON.toJson(FACADE.editHobby(hDTO));
    }    
}
