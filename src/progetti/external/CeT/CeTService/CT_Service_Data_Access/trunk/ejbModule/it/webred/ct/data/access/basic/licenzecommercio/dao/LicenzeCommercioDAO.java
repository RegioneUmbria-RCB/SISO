package it.webred.ct.data.access.basic.licenzecommercio.dao;

import java.util.List;

import it.webred.ct.data.access.basic.common.dto.RicercaCivicoDTO;
import it.webred.ct.data.access.basic.licenzecommercio.dto.RicercaLicenzeCommercioDTO;
import it.webred.ct.data.model.licenzecommercio.SitLicenzeCommercio;
import it.webred.ct.data.model.licenzecommercio.SitLicenzeCommercioVie;

public interface LicenzeCommercioDAO {
	
	public SitLicenzeCommercio getLicenzaCommercioByID(RicercaLicenzeCommercioDTO rlc);
	
	public SitLicenzeCommercioVie getViaByIdExt(RicercaLicenzeCommercioDTO rlc);
	
	public List<SitLicenzeCommercio> getLicCommercioCivicoAllaData(RicercaCivicoDTO rc);
	
	public List<SitLicenzeCommercio> getLicCommercioCivicoAllaDataByIdViaUnico(RicercaCivicoDTO rc);
	
	public SitLicenzeCommercioVie getViaByDescrizione(RicercaCivicoDTO rc);
	
}
