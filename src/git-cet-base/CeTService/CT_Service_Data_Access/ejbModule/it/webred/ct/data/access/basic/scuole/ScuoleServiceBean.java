package it.webred.ct.data.access.basic.scuole;

import it.webred.ct.data.access.basic.CTServiceBaseBean;
import it.webred.ct.data.access.basic.centriestivi.CentriEstiviDataIn;
import it.webred.ct.data.access.basic.centriestivi.CentriEstiviServiceException;
import it.webred.ct.data.access.basic.centriestivi.dao.CentriEstiviDAO;
import it.webred.ct.data.access.basic.common.dto.KeyValueDTO;
import it.webred.ct.data.access.basic.scuole.dao.ScuoleDAO;
import it.webred.ct.data.model.centriestivi.SitCresCentro;
import it.webred.ct.data.model.centriestivi.SitCresFasciaEta;
import it.webred.ct.data.model.centriestivi.SitCresFermata;
import it.webred.ct.data.model.centriestivi.SitCresTurno;
import it.webred.ct.data.model.scuole.SitSclClassi;
import it.webred.ct.data.model.scuole.SitSclIstituti;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless
public class ScuoleServiceBean extends CTServiceBaseBean implements
		ScuoleService {

	@Autowired
	private ScuoleDAO scuoleDAO;
	
	@Autowired
	private CentriEstiviDAO centriEstiviDAO;

	public static final List<KeyValueDTO> tipoIscrizione = Arrays.asList(
			new KeyValueDTO("prePost0", "PRE Scuola"), new KeyValueDTO(
					"prePost1", "POST Scuola"), new KeyValueDTO("prePost2",
					"PRE-POST Scuola"));

	public static final List<String> tipoMensa = Arrays.asList(
			"Mensa 2 Rientri", "Mensa 3 Rientri", "Mensa 1 Giorno",
			"Mensa 4 Giorni", "Mensa 5 Giorni");

	public static final List<KeyValueDTO> tipoIscrizioneMensa = Arrays
			.asList(new KeyValueDTO("Normale", "Normale"));
	
	public static final List<KeyValueDTO> tipoDietaMensa = Arrays
			.asList(new KeyValueDTO("Non richiesto", "Non richiesto"));

	public static final List<String> centriEstiviFasciaEta = Arrays.asList(
			"3-6 Anni", "6-14 Anni");
	
	public static final List<String> centriEstivi3_6 = Arrays.asList(
			"Scuola dell'infanzia");
	
	public static final List<String> centriEstivi6_14 = Arrays.asList(
			"S. Fedele");
	
	public static final List<String> centriEstiviFermate = Arrays.asList(
			"LECCO ALT EX CINEMA APOLLO", "LIBERTA' ALT VESPUCCI", "BUONARROTI",
			"CHIESA REGINA PACIS");
	
	@Override
	public List<SitSclIstituti> getListaIstitutiByTipo(ScuoleDataIn dataIn)
			throws ScuoleServiceException {

		return scuoleDAO.getListaIstitutiByTipo(dataIn.getTipoIstituto());
	}

	@Override
	public List<SitSclClassi> getListaClassiByIstituto(ScuoleDataIn dataIn)
			throws ScuoleServiceException {

		return scuoleDAO.getListaClassiByIstituto(dataIn.getCodIstituto());
	}

	@Override
	public List<String> getListaAnniByIstituto(ScuoleDataIn dataIn)
			throws ScuoleServiceException {

		return scuoleDAO.getListaAnniByIstituto(dataIn.getCodIstituto());
	}

	@Override
	public List<String> getListaSezioniByIstitutoAnno(ScuoleDataIn dataIn)
			throws ScuoleServiceException {

		return scuoleDAO.getListaSezioniByIstitutoAnno(dataIn.getCodIstituto(),
				dataIn.getAnno());
	}

	@Override
	public List<SitSclIstituti> getListaIstitutiByIscrizione(ScuoleDataIn dataIn)
			throws ScuoleServiceException {

		return scuoleDAO.getListaIstitutiByIscrizione(dataIn
				.getTipoIscrizione());
	}

	@Override
	public List<KeyValueDTO> getListaTipiIscrizione(ScuoleDataIn dataIn)
			throws ScuoleServiceException {
		return tipoIscrizione;
	}

	@Override
	public List<String> getListaTipiIstitutoByIscrizione(ScuoleDataIn dataIn)
			throws ScuoleServiceException {
		return scuoleDAO.getListaTipiIstitutoByIscrizione(dataIn
				.getTipoIscrizione());
	}
	
	@Override
	public List<String> getListaTipiIstituto(ScuoleDataIn dataIn)
			throws ScuoleServiceException {
		return scuoleDAO.getListaTipiIstituto();
	}

	@Override
	public List<String> getListaTipiMensaByIstituto(ScuoleDataIn dataIn)
			throws ScuoleServiceException {
		return tipoMensa;
	}

	@Override
	public List<KeyValueDTO> getListaIscrizioneMensaByIstituto(
			ScuoleDataIn dataIn) throws ScuoleServiceException {
		return tipoIscrizioneMensa;
	}
	
	@Override
	public List<KeyValueDTO> getListaDietaMensaByIstituto(
			ScuoleDataIn dataIn) throws ScuoleServiceException {
		return tipoDietaMensa;
	}
	
	@Override
	public List<SitCresFasciaEta>  getListaCentriEstiviFasciaEta(
			ScuoleDataIn dataIn) throws ScuoleServiceException {
		return centriEstiviDAO.getListaFasciaEta();
	}
	
	@Override
	public List<SitCresCentro> getListaCentriByFascia(ScuoleDataIn dataIn)
			throws ScuoleServiceException {

		return centriEstiviDAO.getListaCentriByFascia(dataIn.getIdFascia());
	}

	@Override
	public List<SitCresTurno> getListaTurniByCentro(ScuoleDataIn dataIn)
			throws ScuoleServiceException {
		
		return centriEstiviDAO.getListaTurniByCentro(dataIn.getIdCentro());
		
	}
	
	@Override
	public List<SitCresFermata> getListaFermateByCentro(ScuoleDataIn dataIn)
			throws ScuoleServiceException {
		
		return centriEstiviDAO.getListaFermateByCentro(dataIn.getIdCentro());
		
	}
}
