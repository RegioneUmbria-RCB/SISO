package eu.smartpeg.rilevazionepresenze;

import java.util.List;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

import eu.smartpeg.rilevazionepresenze.StruttureSessionBeanLocal;
import eu.smartpeg.rilevazionepresenze.StruttureSessionBeanRemote;
import eu.smartpeg.rilevazionepresenze.data.model.Area;
import eu.smartpeg.rilevazionepresenze.data.model.Struttura;
import eu.smartpeg.rilevazionepresenze.ejb.dao.StruttureDAO;


/**
 * Session Bean implementation class StruttureBean
 */
@Stateless
public class StruttureSessionBean implements StruttureSessionBeanRemote, StruttureSessionBeanLocal  {


	@Autowired
	private StruttureDAO struttureDao;
    /**
     * Default constructor. 
     */
    public StruttureSessionBean() {
        // TODO Auto-generated constructor stub
    }

    @Override
	public List<Struttura> findAll() {
    	return struttureDao.getStrutture();
	}

	@Override
	public Struttura salva(Struttura struttura) {
		
		try {
			Struttura res = struttureDao.saveStruttura(struttura) ;
			return res;	
		}
		catch (Exception e) {
			
		}
		return struttura;
		}
	

	@Override
	public void eliminaStruttura(Struttura struttura) throws Exception {
		try {
			struttureDao.eliminaStruttura(struttura);
		}
		catch (Exception ex) {
			//logger.error("Errore eliminaStruttura " + e.getMessage(), e);
			throw ex;
		}
	}
	
	@Override
	public void eliminaArea(Struttura struttura, Area area) throws Exception {
		try {
		struttureDao.eliminaArea(struttura, area) ;
		}catch (Exception e) {
			//logger.error("Errore eliminaArea " + e.getMessage(), e);
			throw e;
		}
	}
	
	@Override
	public List<Area> findAllAreas() {
		return struttureDao.getAree();
		
	}
	
	@Override
	public Struttura findStrutturaById(Long idStruttura) {
		return struttureDao.findStrutturaById(idStruttura);
		
	}
	
	
	@Override
	public String validaStruttura(Struttura struttura) {
		String messaggio = "";
		if(struttura.getNomeStruttura().isEmpty())
		{
			return  "Il nome della struttura è obbligatorio";
		}
		if(struttura.getIndirizzo().isEmpty())
		{
			return  "L'indirizzo della struttura è obbligatorio";
		}
		if(struttura.getTipoStruttura()<0)
		{
			return  "La tipologia di Struttura è obbligatoria";
		}
 		return messaggio;
				
	}

}


