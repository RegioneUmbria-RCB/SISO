package it.webred.cs.csa.ejb.client;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.csa.ejb.dto.OperatoriSearchCriteria;
import it.webred.cs.csa.ejb.dto.configurazione.CsOOperatoreSettoreEstesa;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreAnagrafica;
import it.webred.cs.data.model.CsOOperatoreBASIC;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOperatoreTipoOperatore;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsOSettoreBASIC;
import it.webred.cs.data.model.CsOZonaSoc;
import it.webred.cs.data.model.view.CsAmAnagraficaOperatore;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.LinkedHashMap;
import java.util.List;

import javax.ejb.Remote;
/**
 * @author Andrea
 *
 */
@Remote
public interface AccessTableOperatoreSessionBeanRemote {
	
	public List<CsOOperatoreSettore> findOperatoreSettori(OperatoreDTO dto) throws Exception;

	public CsOOperatore findOperatoreById(OperatoreDTO dto) throws Exception;
	
	public CsOOperatore findOperatoreByUsername(OperatoreDTO dto) throws Exception;

	public Long findOperatoreIdByUsername(OperatoreDTO dto) throws Exception;

	public CsOOperatoreSettore findOperatoreSettoreById(OperatoreDTO dto) throws Exception;
	
	public List<CsOOperatoreSettore> findOperatoreSettoreByOperatore(OperatoreDTO dto) throws Exception;
	
	public List<CsOOperatoreSettoreEstesa> findOperatoreSettoreEstesaByOperatore(OperatoreDTO dto) throws Exception;
	
	public List<CsOOperatoreSettore> findOperatoreSettoreBySettore(OperatoreDTO dto) throws Exception;
	
	public List<CsOOperatoreAnagrafica> findAllOperatoreAnagrafica(CeTBaseObject cet) throws Exception;
	
	public CsOSettoreBASIC findSettoreBASICById(OperatoreDTO dto) throws Exception;
	
	public List<CsOSettoreBASIC> findSettoreBASICByOrganizzazione(OperatoreDTO dto) throws Exception;
	
	public List<CsOSettoreBASIC> findSettoreBASICSecondoLivelloByOrganizzazione (OperatoreDTO dto) throws Exception; //SISO-812
	
	public List<CsOSettoreBASIC> findSettoreBASICSenzaSecondoLivelloByOrganizzazione (OperatoreDTO dto) throws Exception; //SISO-812
	
	public CsOOrganizzazione findOrganizzazioneById(OperatoreDTO dto) throws Exception;
	
	public List<CsOOperatoreTipoOperatore> getOperatoriByTipoId(BaseDTO dto) throws Exception;
	
	public List<CsOOperatoreTipoOperatore> getOperatoriByTipoIdSettore(BaseDTO dto) throws Exception;
	
	public CsOOperatoreTipoOperatore getTipoByOperatoreSettore(OperatoreDTO dto) throws Exception;
	
	public List<CsOOperatore> getOperatoriByTipoDescrizione(BaseDTO dto) throws Exception;
	
	public CsOOperatoreTipoOperatore getOperatoreTipoOpById(BaseDTO dto) throws Exception;
	
	public List<CsOOperatore> getOperatoriByCatSocialeOrg(BaseDTO dto) throws Exception;
	
	public CsOOperatore salvaOperatore(BaseDTO dto) throws Exception;
	
	public CsOOperatoreTipoOperatore salvaTipoOperatore(BaseDTO dto) throws Exception;
	
	public CsOOperatoreSettore salvaOperatoreSettore(BaseDTO dto) throws Exception;
	
	public void deleteTipoOperatore(BaseDTO dto) throws Exception;
	
	public void deleteOperatoreSettore(BaseDTO dto) throws Exception;

	public List<CsOOperatore> getOperatoriAll(CeTBaseObject cet) throws Exception;
	
	public List<CsAmAnagraficaOperatore> getUtentiAmPerCs(OperatoriSearchCriteria cet);
	
	public CsOZonaSoc findZonaSocAbilitata(BaseDTO dto) ;
	
	public CsOOperatoreSettore findRespSettoreFirma(OperatoreDTO dto) throws Exception;
	
	public void resetFirmaTuttiRespSettore(BaseDTO dto);

	public CsOOperatoreBASIC findOperatoreBASICById(OperatoreDTO dto) throws Exception;

	public CsOOperatoreBASIC findOperatoreBASICByUsername(OperatoreDTO opDto) throws Exception;

	public CsOSettore findSettoreById(OperatoreDTO opDto) throws Exception;

	public void insertOrUpdateAlertConfig(BaseDTO input) throws Exception;
	
	public List<CsOOperatoreTipoOperatore> findTipiOperatore(BaseDTO operatoreSettoreIdDto) throws Exception;
	
	public LinkedHashMap<String, String> getCodificaRuoli(BaseDTO dto);

	public int countUtentiAmPerCs(OperatoriSearchCriteria criteria);
}
