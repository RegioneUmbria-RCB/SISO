package it.webred.cs.csa.ejb.client.configurazione;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.csa.ejb.dto.OperatoriSearchCriteria;
import it.webred.cs.csa.ejb.dto.RelSettCatsocEsclusivaDTO;
import it.webred.cs.csa.ejb.dto.configurazione.CsOOperatoreSettoreEstesa;
import it.webred.cs.csa.ejb.dto.configurazione.SettoreCatSocialeDTO;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreBASIC;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOperatoreTipoOperatore;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsOSettoreBASIC;
import it.webred.cs.data.model.CsOZonaSoc;
import it.webred.cs.data.model.CsRelSettCsocTipoInter;
import it.webred.cs.data.model.CsRelSettoreStruttura;
import it.webred.cs.data.model.view.CsAmAnagraficaOperatore;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.LinkedHashMap;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface AccessTableConfigurazioneEnteSessionBeanRemote {
	
	public void salvaOrganizzazione(BaseDTO dto);

	public void updateOrganizzazione(BaseDTO dto);

	public void eliminaOrganizzazione(BaseDTO dto);

	public List<CsOOrganizzazione> getOrganizzazioniAll(CeTBaseObject cet);

	public List<CsOOrganizzazione> getOrganizzazioni(CeTBaseObject cet);

	public List<CsOOrganizzazione> getOrganizzazioniAccesso(CeTBaseObject cet);

	public List<CsOOrganizzazione> getOrganizzazioniByCodCatastale(BaseDTO dto);

	public CsOOrganizzazione getOrganizzazioneByCodFittizio(BaseDTO dto);

	public CsOOrganizzazione getOrganizzazioneById(BaseDTO dto);

	/* SISO-663 SM */
	public CsOOrganizzazione getOrganizzazioneCapofila(CeTBaseObject cet);
	/* -=- */

	public List<CsOSettoreBASIC> findSettoreBASICByOrganizzazione(BaseDTO dto);

	public List<SettoreCatSocialeDTO> findSettoreDTOByOrganizzazione(BaseDTO dto);

	public List<CsOSettore> getSettoreAll(CeTBaseObject cet);

	public List<CsOSettore> getSettoriDatiSociali(CeTBaseObject cet);

	public void salvaSettore(BaseDTO dto);

	public void updateSettore(BaseDTO dto);

	public void eliminaSettore(BaseDTO dto);

	public CsOSettore getSettoreById(BaseDTO dto);

	public List<KeyValueDTO> findSettoriByInterventoCustom(BaseDTO dto);
	
	public List<Long> findIdSettoriByInterventoISTATandInterventoCustom(BaseDTO dto);

	public List<CsOSettore> getSettoreRiunione(CeTBaseObject cet); //SISO-1481
	
	public CsOSettore findSettoreByRelStruttura(BaseDTO dto);
	
	public CsRelSettoreStruttura findSettoreByIdStruttura(BaseDTO dto);
	
	public CsRelSettCsocTipoInter findRelSettCsocTipoInterById(BaseDTO bdto);
	
	public Boolean existsRelSettCatsocEsclusivaByTipoDiarioId(RelSettCatsocEsclusivaDTO dto);

	public Boolean existsRelSettCatsocEsclusivaByIds(RelSettCatsocEsclusivaDTO dto);
	
	public List<CsOOperatoreSettore> findOperatoreSettori(OperatoreDTO dto) throws Exception;

	public CsOOperatore findOperatoreById(OperatoreDTO dto) throws Exception;
	
	public CsOOperatore findOperatoreByUsername(OperatoreDTO dto) throws Exception;

	public CsOOperatoreSettore findOperatoreSettoreById(OperatoreDTO dto) throws Exception;
	
	public List<CsOOperatoreSettore> findOperatoreSettoreByOperatore(OperatoreDTO dto) throws Exception;
	
	public List<CsOOperatoreSettoreEstesa> findOperatoreSettoreEstesaByOperatore(OperatoreDTO dto) throws Exception;
	
	public List<KeyValueDTO> findListaOperatoreSettoreBySettore(OperatoreDTO dto) throws Exception;
	
	public List<KeyValueDTO> findListaOperatoreBySettore(OperatoreDTO dto) throws Exception;
	
	public CsOSettoreBASIC findSettoreBASICById(OperatoreDTO dto) throws Exception;
		
	public List<KeyValueDTO> findSettoriByOrganizzazione(OperatoreDTO dto);
	
	public List<KeyValueDTO> findSettoriSecondoLivelloByOrganizzazione (OperatoreDTO dto) throws Exception; //SISO-812
	
	public List<KeyValueDTO> findSettoriSenzaSecondoLivelloByOrganizzazione (OperatoreDTO dto) throws Exception; //SISO-812

	public List<CsOOperatoreTipoOperatore> getOperatoriByTipoId(BaseDTO dto) throws Exception;
	
	public List<CsOOperatoreTipoOperatore> getOperatoriByTipoIdSettore(BaseDTO dto) throws Exception;
	
	public CsOOperatoreTipoOperatore getTipoByOperatoreSettore(OperatoreDTO dto) throws Exception;
	
	public void abilitaOperatoreSettore(BaseDTO dto) throws Exception;
	
	public void disabilitaOperatoreSettore(BaseDTO dto) throws Exception;
	
	public List<CsOOperatore> getOperatoriByTipoDescrizione(BaseDTO dto) throws Exception;
	
	public CsOOperatoreTipoOperatore getOperatoreTipoOpById(BaseDTO dto) throws Exception;
	
	public CsOOperatore salvaOperatore(BaseDTO dto) throws Exception;
	
	public CsOOperatoreTipoOperatore salvaTipoOperatore(BaseDTO dto) throws Exception;
	
	public CsOOperatoreSettore salvaOperatoreSettore(BaseDTO dto) throws Exception;
	
	public void deleteTipoOperatore(BaseDTO dto) throws Exception;
	
	public void deleteOperatoreSettore(BaseDTO dto) throws Exception;

	public List<CsOOperatore> getOperatoriAll(CeTBaseObject cet) throws Exception;
	
	public List<CsAmAnagraficaOperatore> getUtentiAmPerCs(OperatoriSearchCriteria cet);
	
	public CsOZonaSoc findZonaSocAbilitata(CeTBaseObject dto) ;
	
	public CsOOperatoreSettore findRespSettoreFirma(OperatoreDTO dto) throws Exception;
	
	public void resetFirmaTuttiRespSettore(BaseDTO dto);

	public CsOOperatoreBASIC findOperatoreBASICById(OperatoreDTO dto) throws Exception;

	public CsOOperatoreBASIC findOperatoreBASICByUsername(OperatoreDTO opDto) throws Exception;

	public CsOSettore findSettoreById(OperatoreDTO opDto) throws Exception;

	public void insertOrUpdateAlertConfig(BaseDTO input) throws Exception;
	
	public LinkedHashMap<String, String> getCodificaRuoli(BaseDTO dto);

	public int countUtentiAmPerCs(OperatoriSearchCriteria criteria);

	public List<KeyValueDTO> findOperatoreIdAnagraficaBySettore(OperatoreDTO oDto);
	
	public List<CsOOperatoreSettore> findOperatoreSettoreByCodStruttura(BaseDTO dto) throws Exception;

	public CsOOperatoreSettore findOperatoreSettore2LivByIdOperatore(BaseDTO dto) throws Exception;
	
	public CsOOperatoreSettore findOperatoreSettore2LivByUsername(BaseDTO dto) throws Exception;

}
