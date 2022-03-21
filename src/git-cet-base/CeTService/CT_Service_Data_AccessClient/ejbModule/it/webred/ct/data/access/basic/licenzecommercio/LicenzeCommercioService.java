package it.webred.ct.data.access.basic.licenzecommercio;

import java.util.List;

import it.webred.ct.data.access.basic.common.dto.RicercaCivicoDTO;
import it.webred.ct.data.access.basic.licenzecommercio.dto.RicercaLicenzeCommercioDTO;
import it.webred.ct.data.model.licenzecommercio.SitLicenzeCommercio;
import it.webred.ct.data.model.licenzecommercio.SitLicenzeCommercioVie;

import javax.ejb.Remote;

@Remote
public interface LicenzeCommercioService {
	
	public SitLicenzeCommercio getLicenzaCommercioByID(RicercaLicenzeCommercioDTO rlc) throws LicenzeCommercioException;
	
	public String  getViaLicenza(RicercaLicenzeCommercioDTO rlc) throws LicenzeCommercioException ;
	
	public List<SitLicenzeCommercio> getLicCommercioCivicoAllaData(RicercaCivicoDTO rc);
	
	public List<SitLicenzeCommercio> getLicCommercioCivicoAllaDataByIndice(RicercaCivicoDTO rc)throws LicenzeCommercioException;
	
	public SitLicenzeCommercioVie getViaByDescrizione(RicercaCivicoDTO rc);
	
}
