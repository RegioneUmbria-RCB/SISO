package it.webred.ct.data.access.basic.scuole;

import it.webred.ct.data.access.basic.common.dto.KeyValueDTO;
import it.webred.ct.data.model.centriestivi.SitCresCentro;
import it.webred.ct.data.model.centriestivi.SitCresFasciaEta;
import it.webred.ct.data.model.centriestivi.SitCresFermata;
import it.webred.ct.data.model.centriestivi.SitCresTurno;
import it.webred.ct.data.model.scuole.SitSclClassi;
import it.webred.ct.data.model.scuole.SitSclIstituti;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface ScuoleService {

	public List<KeyValueDTO> getListaTipiIscrizione(ScuoleDataIn dataIn) throws ScuoleServiceException;

	public List<SitSclIstituti> getListaIstitutiByIscrizione(ScuoleDataIn dataIn) throws ScuoleServiceException;

	/**
	 * Recupera tutti gli istituti per tipo
	 * 
	 * @param tipoistituto
	 * @return lista SitSclIstituti
	 */
	public List<SitSclIstituti> getListaIstitutiByTipo(ScuoleDataIn dataIn)
			throws ScuoleServiceException;

	/**
	 * Recupera tutte le classi per istituto
	 * 
	 * @param codistituto
	 * @return lista SitSclClassi
	 */
	public List<SitSclClassi> getListaClassiByIstituto(ScuoleDataIn dataIn)
			throws ScuoleServiceException;

	/**
	 * Recupera tutte gli Anni per istituto
	 * 
	 * @param codistituto
	 * @return lista Stringhe
	 */
	public List<String> getListaAnniByIstituto(ScuoleDataIn dataIn)
			throws ScuoleServiceException;

	/**
	 * Recupera tutte le sezioni per istituto e anno
	 * 
	 * @param codistituto
	 * @param anno
	 * @return lista Stringhe
	 */
	public List<String> getListaSezioniByIstitutoAnno(ScuoleDataIn dataIn)
			throws ScuoleServiceException;

	/**
	 * Recupera tutti i tipi istituto per tipo iscrizione
	 * 
	 * @param tipoIscrizione
	 * @return lista Stringhe
	 */
	public List<String> getListaTipiIstitutoByIscrizione(ScuoleDataIn dataIn)
			throws ScuoleServiceException;
	
	/**
	 * Recupera tutti i tipi istituto
	 * 
	 * @return lista Stringhe
	 */
	public List<String> getListaTipiIstituto(ScuoleDataIn dataIn)
			throws ScuoleServiceException;

	public List<String> getListaTipiMensaByIstituto(ScuoleDataIn dataIn)
			throws ScuoleServiceException;

	public List<KeyValueDTO> getListaIscrizioneMensaByIstituto(ScuoleDataIn dataIn)
			throws ScuoleServiceException;

	public List<KeyValueDTO> getListaDietaMensaByIstituto(ScuoleDataIn dataIn)
			throws ScuoleServiceException;

	public List<SitCresFasciaEta> getListaCentriEstiviFasciaEta(ScuoleDataIn dataIn)
			throws ScuoleServiceException;

	public List<SitCresCentro> getListaCentriByFascia(ScuoleDataIn dataIn)
			throws ScuoleServiceException;

	public List<SitCresTurno> getListaTurniByCentro(ScuoleDataIn dataIn)
			throws ScuoleServiceException;

	public List<SitCresFermata> getListaFermateByCentro(ScuoleDataIn dataIn)
			throws ScuoleServiceException;

}
