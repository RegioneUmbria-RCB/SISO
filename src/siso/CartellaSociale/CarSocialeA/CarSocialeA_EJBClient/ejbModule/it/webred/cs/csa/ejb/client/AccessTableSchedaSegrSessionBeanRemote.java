package it.webred.cs.csa.ejb.client;

import java.util.List;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.SchedaSegrDTO;
import it.webred.cs.csa.ejb.dto.SegnalazioneDTO;
import it.webred.cs.csa.ejb.dto.udc.ListaDatiSchedaUdC;
import it.webred.cs.data.model.CsSsSchedaSegr;
import it.webred.cs.data.model.CsSchedeAltraProvenienza;

import javax.ejb.Remote;


@Remote
public interface AccessTableSchedaSegrSessionBeanRemote {
	
	public void salvaSchedaSegr(SchedaSegrDTO dto) throws Exception;
	
	/**
	 * <code>dto</code> deve contenere <code>SCHEDA_ID</code> in <code>obj</code> e <code>PROVENIENZA</code> in <code>obj2</code>
	 */
	public CsSsSchedaSegr findSchedaSegrBySchedaIdProvenienza(BaseDTO dto);

	
	
	/**
	 * <code>dto</code> deve contenere <code>CS_A_ANAGRAFICA.ID</code> in <code>obj</code>.
	 * <p>
	 * <i>Ci si può arrivare passando per CS_A_SOGGETTO.ANAGRAFICA_ID</i>
	 */
	public CsSsSchedaSegr findSchedaSegrCreataByIdAnagrafica(BaseDTO dto);

	public List<ListaDatiSchedaUdC> getListaSchedeUdC(SchedaSegrDTO dto) throws Throwable;
	
	public Integer getListaSchedeUdcCount(SchedaSegrDTO dto) throws Throwable;

	public void updateSchedaSegr(BaseDTO dto);
	
	public CsSsSchedaSegr respingiScheda(BaseDTO dto);
	
	public CsSsSchedaSegr vediScheda(BaseDTO dto);

	/**
	 * <code>dto</code> deve contenere <code>SCHEDA_ID</code> in <code>obj</code> e <code>PROVENIENZA</code> in <code>obj2</code>
	 */
	public String findStatoSchedaSegrBySchedaIdProvenienza(BaseDTO dto);
	
	public String findEnteToSchedaSegrBySchedaIdProvenienza(BaseDTO dto);

	public void deleteSchedaSegr(SchedaSegrDTO dto) throws Exception;

	public void agganciaCartellaASchedaUDC(BaseDTO dto) throws Exception;
	
	public Long findSchedaAggiornataUDCSoggetto(BaseDTO dto);
	
	// SISO-938
	/**
	 * <code>dto</code> deve contenere <code>CS_A_ANAGRAFICA.ID</code> in
	 * <code>obj</code>.
	 * <p>
	 * <i>Ci si può arrivare passando per CS_A_SOGGETTO.ANAGRAFICA_ID</i>
	 */
	public List<CsSsSchedaSegr> findSchedaSegrByIdAnagraficaNotSS(BaseDTO dto);

	/**
	 * <code>dto</code> deve contenere <code>ID_SCHEDA_SEGR</code> in
	 * <code>obj</code> e <code>PROVENIENZA</code> in <code>obj2</code>
	 */
	public CsSchedeAltraProvenienza findVistaCasiAltriBySchedaIdProvenienza(BaseDTO dto);

	public CsSchedeAltraProvenienza saveSegnalazioneSerena(SegnalazioneDTO dto);
	
}