package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableAlertSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSchedaSegrSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.CatSocialeDAO;
import it.webred.cs.csa.ejb.dao.SchedaSegrDAO;
import it.webred.cs.csa.ejb.dao.SchedaSegrVistaCasiAltriDAO;
import it.webred.cs.csa.ejb.dao.SoggettoDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.SchedaSegrDTO;
import it.webred.cs.csa.ejb.dto.SegnalazioneDTO;
import it.webred.cs.csa.ejb.dto.udc.DatiSchedaAccessoDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.data.model.CsSchedeAltraProvenienza;
import it.webred.cs.data.model.CsSsSchedaSegr;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

@Stateless
public class AccessTableSchedaSegrSessionBean extends CarSocialeBaseSessionBean implements AccessTableSchedaSegrSessionBeanRemote  {

	private static final long serialVersionUID = 1L;

	public static Logger logger = Logger.getLogger("carsociale.log");
	
	@Autowired
	private SchedaSegrDAO schedaSegrDao;
	
	@Autowired
	private SoggettoDAO soggettoDao;

	@Autowired
	private SchedaSegrVistaCasiAltriDAO vistaCasiAltriDao;	// SISO-938

	@Autowired
	private CatSocialeDAO catSocialeDao;
	
	@EJB
	private AccessTableAlertSessionBeanRemote alertService;
	
	@Override
	public String findStatoSchedaSegrBySchedaIdProvenienza(BaseDTO dto) {	
		Long schedaId = (Long) dto.getObj();
		String provenienza = (String) dto.getObj2();
		
		CsSsSchedaSegr ss = schedaSegrDao.findSchedaSegrBySchedaIdProvenienza(schedaId, provenienza);
		return ss!=null ? ss.getStato(): null;
	}
	
	@Override
	public String findEnteToSchedaSegrBySchedaIdProvenienza(BaseDTO dto) {	
		Long schedaId = (Long) dto.getObj();
		String provenienza = (String) dto.getObj2();
		
		CsSsSchedaSegr ss = schedaSegrDao.findSchedaSegrBySchedaIdProvenienza(schedaId, provenienza);
		
		return ss!=null ? ss.getCodEnte(): null;
	}
	
	@Override
	public CsSsSchedaSegr findSchedaSegrBySchedaIdProvenienza(BaseDTO dto) {	
		Long schedaId = (Long) dto.getObj();
		String provenienza = (String) dto.getObj2();
		
		return schedaSegrDao.findSchedaSegrBySchedaIdProvenienza(schedaId, provenienza);
	}
	
	@Override
	public List<CsSsSchedaSegr> findSchedaSegrByIdAnagraficaNotSS(BaseDTO dto) {
		return schedaSegrDao.findSchedaSegrByIdAnagraficaNotSS((Long) dto.getObj());
	}

	@Override
	public CsSsSchedaSegr findSchedaSegrCreataByIdAnagrafica(BaseDTO dto) {	
		List<CsSsSchedaSegr> lista = schedaSegrDao.findSchedaSegrByIdAnagrafica((Long) dto.getObj(), DataModelCostanti.SchedaSegr.STATO_CREATA);
		if(lista != null && lista.size() > 0)
			return lista.get(0);	
		else return null;
	}

	@Override
	public List<DatiSchedaAccessoDTO> getSchedeSegr(SchedaSegrDTO dto) throws Throwable {	
		List<DatiSchedaAccessoDTO> lstIn = schedaSegrDao.getSchedeSegr(dto);
		for(DatiSchedaAccessoDTO sa : lstIn){
			if(!dto.isLoadListaUDCFascicolo()){ 
				if (!StringUtils.isBlank(sa.getCf()) && sa.isPropostaPIC() && !sa.isSoggettoAssociato()){
					/*Verifico e aggiorno lo stato ESISTENTE, se trovo una cartella associata al soggetto*/
					CsASoggettoLAZY csSogg = soggettoDao.getSoggettoByCF(sa.getCf());
					CsSsSchedaSegr csSs = schedaSegrDao.findCsSsSchedaSegr(sa.getCsSsId());
					if (csSogg != null && csSs.getCsASoggetto()==null) {
						if (csSs.getFlgEsistente() == null || !csSs.getFlgEsistente()) {
							csSs.setFlgEsistente(true);
							csSs = schedaSegrDao.updateSchedaSegr(csSs);
						}
						if (csSs.getCsASoggetto() == null) {
							csSs.setCsASoggetto(csSogg);
						}
						sa.aggiornaValoriCsSsSchedaSegr(csSs);
					}
				}
			}
			
		}
		return lstIn;

	}
	
	@Override
	public Integer getSchedeSegrCount(SchedaSegrDTO dto) {
		return schedaSegrDao.getSchedeSegrCount(dto.isOnlyNew(), dto);
	}
	
	@Override
	public void salvaSchedaSegr(SchedaSegrDTO dto) throws Exception{
		String statoScheda = null;
		CsSsSchedaSegr scheda = null; 
		BaseDTO bdto = new BaseDTO();
		copiaCsTBaseObject(dto, bdto);
		if (dto.getId() != null) {
			scheda = schedaSegrDao.findSchedaSegrBySchedaIdProvenienza(dto.getId(), dto.getProvenienza());
			statoScheda = scheda!=null ? scheda.getStato(): null;
		}
		// Modifico CS_SS_SCHEDA_SEGR solo se non è presa in carico in CS
		if (StringUtils.isBlank(statoScheda)) {
			if (dto.isTipoSchedaPropostaPic()){
				//Inserimento
				if (scheda==null || dto.isNuovoInserimento()) {
					scheda = new CsSsSchedaSegr();
					scheda.setSchedaId(dto.getId());
					scheda.setProvenienza(dto.getProvenienza());
					scheda.setFlgStato(DataModelCostanti.SchedaSegr.STATO_INS);
					scheda.setUserIns(dto.getUserId());
					scheda.setDtIns(new Date());
					scheda.setCodEnte(dto.getEnteDestinatario());
					schedaSegrDao.saveSchedaSegr(scheda);
					
					dto.setObj(dto.getCf());
					dto.setObj2(dto.getEnteSchedaAccessoId());
					dto.setObj3(DataModelCostanti.TipiAlertCod.UDC);
					dto.setObj4("una nuova scheda UDC");
					alertService.addAlertNuovoInserimentoToResponsabileCaso(dto);
					
				} else {
					scheda.setFlgStato(DataModelCostanti.SchedaSegr.STATO_MOD);
					scheda.setUsrMod(dto.getUserId());
					scheda.setDtMod(new Date());
					
					if(dto.getEnteDestinatario()!=null)
						scheda.setCodEnte(dto.getEnteDestinatario());
					
					schedaSegrDao.updateSchedaSegr(scheda);
				}
			}else
				deleteSchedaSegr(dto);
		}
	}
	
	@Override
	public void updateSchedaSegr(BaseDTO dto) {
		schedaSegrDao.updateSchedaSegr((CsSsSchedaSegr) dto.getObj());
	}
	
	@Override
	public CsSsSchedaSegr respingiScheda(BaseDTO dto) {
		Long id = (Long) dto.getObj();
		String note = (String) dto.getObj2();
		CsSsSchedaSegr scheda = schedaSegrDao.findCsSsSchedaSegr(id);
		if(scheda!=null){
			scheda.setNotaStato(note);
			scheda.setStato(DataModelCostanti.SchedaSegr.STATO_RESPINTA);
			scheda = schedaSegrDao.updateSchedaSegr(scheda);
		}
		return scheda;
	}
	
	@Override
	public CsSsSchedaSegr vediScheda(BaseDTO dto) {
		Long id = (Long) dto.getObj();
		String note = (String) dto.getObj2();
		CsSsSchedaSegr scheda = schedaSegrDao.findCsSsSchedaSegr(id);
		if(scheda!=null){
			scheda.setNotaStato(note);
			scheda.setStato(DataModelCostanti.SchedaSegr.STATO_VISTA);
			scheda = schedaSegrDao.updateSchedaSegr(scheda);
		}
		return scheda;
	}
	
	@Override
	public void deleteSchedaSegr(SchedaSegrDTO dto) throws Exception {
		schedaSegrDao.deleteSchedaSegr(dto.getId(), dto.getProvenienza());
	}
	
	@Override
	public void agganciaCartellaASchedaUDC(BaseDTO dto) throws Exception {
		try{
		    CsASoggettoLAZY soggetto = (CsASoggettoLAZY)dto.getObj3();
			CsSsSchedaSegr csSsSchedaSegr = schedaSegrDao.findSchedaSegrBySchedaIdProvenienza((Long)dto.getObj(), (String) dto.getObj2());
			//csSsSchedaSegr.setCsASoggetto((CsASoggettoLAZY)dto.getObj2());
			csSsSchedaSegr.setSoggettoId(soggetto.getAnagraficaId());
			csSsSchedaSegr.setStato(DataModelCostanti.SchedaSegr.STATO_CREATA);
			csSsSchedaSegr.setDtMod(new Date());
			csSsSchedaSegr.setUsrMod(dto.getUserId());
			
			schedaSegrDao.updateSchedaSegr(csSsSchedaSegr);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public Long findSchedaAggiornataUDCSoggetto(BaseDTO dto) {
		return schedaSegrDao.findSchedaAggiornataUDCSoggetto((String)dto.getObj());
	}
	
	// SISO-938
	@Override
	public CsSchedeAltraProvenienza findVistaCasiAltriBySchedaIdProvenienza(BaseDTO dto) {
		Long idSchedaSegr = (Long) dto.getObj();
		String provenienza = (String) dto.getObj2();
		
		return vistaCasiAltriDao.findVistaCasiAltriBySchedaIdProvenienza(idSchedaSegr, provenienza);
	}

	// SISO-938
	@Override
	public CsSchedeAltraProvenienza saveSegnalazioneSerena(SegnalazioneDTO segnalazioneDTO) {
		// da dto ricevo i dati della segnalazione; li salvo in variabili locali per comodità
		String comuneResidenza = segnalazioneDTO.getComuneResidenza();
		String cognomeMinore = segnalazioneDTO.getCognome();
		String nomeMinore = segnalazioneDTO.getNome();
		String dataNascitaMinore = segnalazioneDTO.getDataNascita();
		String cfOperatore = segnalazioneDTO.getCfOperatore();
		String nomeOperatore = segnalazioneDTO.getNomeOperatore();
		String cognomeOperatore = segnalazioneDTO.getCognomeOperatore();
		String ufficioOperatore = segnalazioneDTO.getUfficio();
		
		Date dataInserimento = new Date();
		
		
		// Step 1: creo un nuovo record su CS_SS_SCHEDA_SEGR; usare l'ID restituito per fare la successiva INSERT
		CsSsSchedaSegr csSsSchedaSegr = new CsSsSchedaSegr();
		
		// ID non va inserito, ci pensa la sequence
		// SCHEDA_ID lo aggiungo dopo
		csSsSchedaSegr.setProvenienza(DataModelCostanti.SchedaSegr.PROVENIENZA_SERENA);	// default
		csSsSchedaSegr.setFlgStato(DataModelCostanti.SchedaSegr.STATO_INS);	// default
		csSsSchedaSegr.setUserIns("SERENA");	// TODO
		csSsSchedaSegr.setDtIns(dataInserimento);
		// USR_MOD va lasciato null
		// DT_MOD va lasciata null
		// SOGGETTO_ID va lasciato null
		csSsSchedaSegr.setCodEnte(comuneResidenza);	// TODO corretto?
		//csSsSchedaSegr.setCsCCategoriaSociale(catSocialeDao.getCategoriaSocialeById(new Long(1)));	// default
		// NOTA_STATO va lasciata null
		// STATO va lasciato null
		// FLG_ESISTENTE va lasciato null
		
		csSsSchedaSegr = schedaSegrDao.updateSchedaSegr(csSsSchedaSegr);

		/* Inserito il record, la Sequence ha generato l'ID; lo utilizzo subito per salvarlo anche sul campo SCHEDA_ID
		 * che per PROVENIENZA="SERENA" corrisponde a ID (l'aggiornamento su DB viene poi fatto dall'EntityManager in automatico).
		 * 
		 * Poi lo userò anche per l'INSERT su CS_VISTA_CASI_SS_ALTRI. */
		long nuovoId = csSsSchedaSegr.getId();
		
		csSsSchedaSegr.setSchedaId(nuovoId);
		
		
		// Step 2: creo un nuovo record su CS_VISTA_CASI_SS_ALTRI (usando l'ID della precedente INSERT)
		CsSchedeAltraProvenienza csVistaCasiSSAltri = new CsSchedeAltraProvenienza();
		
		csVistaCasiSSAltri.setIdSchedaSegr(nuovoId);
		csVistaCasiSSAltri.setProvenienza(DataModelCostanti.SchedaSegr.PROVENIENZA_SERENA);
		csVistaCasiSSAltri.setAccessoData(dataInserimento);	// uso la stessa data inserita in CS_SS_SCHEDA_SEGR
		
		csVistaCasiSSAltri.setAccesso_Oper_Cf(cfOperatore);
		csVistaCasiSSAltri.setAccesso_Oper_Cognome(cognomeOperatore);
		csVistaCasiSSAltri.setAccesso_Oper_Nome(nomeOperatore);
		csVistaCasiSSAltri.setAccesso_Ufficio(ufficioOperatore);
		
		csVistaCasiSSAltri.setSegnalatoCognome(cognomeMinore);
		csVistaCasiSSAltri.setSegnalatoNome(nomeMinore);
		
		csVistaCasiSSAltri.setSegnalatoCodiceFiscale("");
		csVistaCasiSSAltri.setTipoIntervento(DataModelCostanti.SchedaSegr.TIPO_PROPOSTA_PRESA_IN_CARICO);	// default
		CsCCategoriaSociale cCategoriaSociale = new CsCCategoriaSociale();
		cCategoriaSociale.setId(1); // famiglia e minori, default
		csVistaCasiSSAltri.setCsCCategoriaSociale(cCategoriaSociale);
		
		
		return vistaCasiAltriDao.saveVistaCasiAltri(csVistaCasiSSAltri);
	}

}
