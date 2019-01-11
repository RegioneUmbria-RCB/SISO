package it.webred.cs.csa.ejb.client;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreAnagrafica;
import it.webred.cs.data.model.CsOOperatoreBASIC;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOperatoreTipoOperatore;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsOSettoreBASIC;
import it.webred.cs.data.model.CsOZonaSoc;
import it.webred.ct.support.datarouter.CeTBaseObject;

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
	
	public List<CsOOperatoreSettore> findOperatoreSettoreBySettore(OperatoreDTO dto) throws Exception;
	
	public List<CsOOperatoreAnagrafica> findAllOperatoreAnagrafica() throws Exception;
	
	public CsOSettoreBASIC findSettoreBASICById(OperatoreDTO dto) throws Exception;
	
	public List<CsOSettoreBASIC> findSettoreBASICByOrganizzazione(OperatoreDTO dto) throws Exception;
	
	public CsOOrganizzazione findOrganizzazioneById(OperatoreDTO dto) throws Exception;
	
	public List<CsOOperatoreTipoOperatore> getOperatoriByTipoId(BaseDTO dto) throws Exception;
	
	public List<CsOOperatoreTipoOperatore> getOperatoriByTipoIdSettore(BaseDTO dto) throws Exception;
	
	public CsOOperatoreTipoOperatore getTipoByOperatoreSettore(OperatoreDTO dto) throws Exception;
	
	public List<CsOOperatore> getOperatoriByTipoDescrizione(BaseDTO dto) throws Exception;
	
	public CsOOperatoreTipoOperatore getOperatoreTipoOpById(BaseDTO dto) throws Exception;
	
	public List<CsOOperatore> getOperatoriByCatSociale(BaseDTO dto) throws Exception;
	
	public CsOOperatore insertOrUpdateOperatore(BaseDTO dto) throws Exception;
	
	public CsOOperatoreTipoOperatore insertOrUpdateTipoOperatore(BaseDTO dto) throws Exception;
	
	public CsOOperatoreSettore insertOrUpdateOperatoreSettore(BaseDTO dto) throws Exception;
	
	public void deleteTipoOperatore(BaseDTO dto) throws Exception;
	
	public void deleteOperatoreSettore(BaseDTO dto) throws Exception;

	public List<CsOOperatore> getOperatoriAll(CeTBaseObject cet) throws Exception;
	
	public List<CsOZonaSoc> findZoneSocAbilitate(BaseDTO dto) throws Exception;

	public CsOOperatoreSettore findRespSettoreFirma(OperatoreDTO dto) throws Exception;
	
	public void resetFirmaTuttiRespSettore(BaseDTO dto);

	public CsOOperatoreBASIC findOperatoreBASICById(OperatoreDTO dto) throws Exception;

	public CsOOperatoreBASIC findOperatoreBASICByUsername(OperatoreDTO opDto) throws Exception;

	public CsOSettore findSettoreById(OperatoreDTO opDto) throws Exception;

	public void insertOrUpdateAlertConfig(BaseDTO input) throws Exception;
	
	public List<CsOOperatoreTipoOperatore> findTipiOperatore(BaseDTO operatoreSettoreIdDto) throws Exception;
}
