package eu.smartpeg.rilevazionepresenze;

import java.util.List;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

import eu.smartpeg.rilevazionepresenze.data.model.Motivazione;
import eu.smartpeg.rilevazionepresenze.ejb.dao.DominiDAO;


@Stateless
public class DominiSessionBean implements DominiSessionBeanRemote  {


	@Autowired
	private DominiDAO dominiDAO;
    /**
     * Default constructor. 
     */
    public DominiSessionBean() {
        // TODO Auto-generated constructor stub
    }

    @Override
	public List<Motivazione> findAllMotivazioni() {
    	return dominiDAO.findAllMotivazioni();
	}

}


