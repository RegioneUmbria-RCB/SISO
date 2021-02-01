package it.webred.ct.data.access.basic.licenzecommercio;

import java.util.List;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

import it.webred.ct.data.access.basic.CTServiceBaseBean;
import it.webred.ct.data.access.basic.common.dto.RicercaCivicoDTO;
import it.webred.ct.data.access.basic.licenzecommercio.dao.LicenzeCommercioDAO;
import it.webred.ct.data.access.basic.licenzecommercio.dto.RicercaLicenzeCommercioDTO;
import it.webred.ct.data.model.licenzecommercio.SitLicenzeCommercio;
import it.webred.ct.data.model.licenzecommercio.SitLicenzeCommercioVie;
@Stateless
public class LicenzeCommercioServiceBean extends CTServiceBaseBean implements LicenzeCommercioService {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private LicenzeCommercioDAO licCommercioDAO;

	@Override
	public SitLicenzeCommercio getLicenzaCommercioByID(RicercaLicenzeCommercioDTO rlc) throws LicenzeCommercioException {
		return licCommercioDAO.getLicenzaCommercioByID(rlc);
	}
	public String  getViaLicenza(RicercaLicenzeCommercioDTO rlc) throws LicenzeCommercioException {
		String nomeVia ="";
		SitLicenzeCommercioVie via = licCommercioDAO.getViaByIdExt(rlc);
		if (via.getIndirizzo()!= null) {
			if (via.getSedime()!=null)
				nomeVia = via.getSedime() + " ";
			nomeVia += via.getIndirizzo();
			
		}
		return nomeVia;
	}
	
	@Override
	public List<SitLicenzeCommercio> getLicCommercioCivicoAllaDataByIndice(RicercaCivicoDTO rc)throws LicenzeCommercioException{
		return licCommercioDAO.getLicCommercioCivicoAllaDataByIdViaUnico(rc);	
	}
	
	
	@Override
	public List<SitLicenzeCommercio> getLicCommercioCivicoAllaData(RicercaCivicoDTO rc){
		return licCommercioDAO.getLicCommercioCivicoAllaData(rc);
	}
	
	@Override
	public SitLicenzeCommercioVie getViaByDescrizione(RicercaCivicoDTO rc){
		return licCommercioDAO.getViaByDescrizione(rc);
	}
	
	
}
