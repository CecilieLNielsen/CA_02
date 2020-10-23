package rest;

import DTO.PersonDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Person;
import utils.EMF_Creator;
import facades.PersonFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//Todo Remove or change relevant parts before ACTUAL use
@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();     
    private static final PersonFacade FACADE =  PersonFacade.getFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String forTest(){
        return "\"{msg}\" : \"{This is working}\"";
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPersonByID(@PathParam("id") int id){
        return GSON.toJson(FACADE.getPersonById(id));
    }

    @GET
    @Path("/email/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPersonByEmail(@PathParam("email") String email){
        return GSON.toJson(FACADE.getPersonByEmail(email));
    }
    
    @GET
    @Path("/firstName/{firstName}/lastName/{lastName}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPersonByNames(@PathParam("fristName") String firstName, @PathParam("lastName") String lastName){
        return GSON.toJson(FACADE.getPersonsByName(firstName, lastName));
    }
    
    @GET
    @Path("/zip/{zip}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPersonByCity(@PathParam("zip") int zip){
        return GSON.toJson(FACADE.getPersonsByCity(zip));
    }
    
    @GET
    @Path("/zip/{zip}/street/{street}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPersonByCityAndStreet(@PathParam("zip") int zip, @PathParam("street") String street){
        return GSON.toJson(FACADE.getPersonsByAddress(zip, street));
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPersonAll() {
        return GSON.toJson(FACADE.getAllPersons());
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deletePerson(@PathParam("id") int id) {
        return GSON.toJson(FACADE.deletePerson(id));
    }
    
    @GET
    @Path("/phone/{number}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPersonByPhoneNumber(@PathParam("phone") String number) {
        return GSON.toJson(FACADE.getPersonByPhoneNumber(number));
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addPerson(PersonDTO pDTO) {
        return GSON.toJson(FACADE.addPerson(pDTO));
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String editPerson(PersonDTO pDTO) {
        return GSON.toJson(FACADE.editPerson(pDTO));
    }
}
