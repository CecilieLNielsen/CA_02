package facades;

import DTO.CityInfoDTO;
import entities.CityInfo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author hassanainali
 */
public class CityFacade {

    private static CityFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private CityFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static CityFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CityFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<CityInfoDTO> getAllCities() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<CityInfo> query = em.createQuery("SELECT c FROM CityInfo c", CityInfo.class);
        List<CityInfoDTO> cities = new ArrayList();
        for (CityInfo c : query.getResultList()) {
            cities.add(new CityInfoDTO(c));
        }
        return cities;
    }
}
