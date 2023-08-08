package it.webred.cs.sociosan.ejb.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;

import it.umbriadigitale.argo.ejb.client.cs.bean.ArConfigurazioneService;
import it.webred.cs.csa.ejb.client.configurazione.AccessTableConfigurazioneEnteSessionBeanRemote;
import it.webred.cs.csa.ejb.client.configurazione.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.domini.AccessTableDominiSiruSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.rest.InterventoDTO;
import it.webred.cs.csa.ejb.dto.rest.TabellaDecodificaBaseDTO;
import it.webred.cs.csa.ejb.dto.rest.TabellaDecodificaDTO;
import it.webred.cs.csa.ejb.dto.rest.TabellaDecodificaExtDTO;
import it.webred.cs.csa.ejb.dto.rest.TrascodificheResponseDTO;
import it.webred.cs.csa.ejb.dto.siru.SiruDominioDTO;
import it.webred.cs.csa.ejb.enumeratori.SiruEnum;
import it.webred.cs.data.model.ArTClasse;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsTbCondLavoro;
import it.webred.cs.data.model.CsTbGVulnerabile;
import it.webred.cs.data.model.CsTbStatoCivile;
import it.webred.cs.data.model.CsTbTipologiaFamiliare;
import it.webred.cs.data.model.CsTbTitoloStudio;
import it.webred.cs.data.model.VLineaFin;
import it.webred.cs.sociosan.ejb.client.AccessTableConfigurazioneSessionBeanClientRemote;
import it.webred.cs.sociosan.ejb.utils.TableConfigurazioniCostanti;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;
import it.webred.ss.ejb.client.ConfigurazioneSessionBeanRemote;

@Stateless
public class AccessTableConfigurazioneSessionBeanClient   extends BaseSessionBean implements AccessTableConfigurazioneSessionBeanClientRemote {

	protected ArConfigurazioneService sbArgo;
	protected AccessTableConfigurazioneSessionBeanRemote sb;
	protected ConfigurazioneSessionBeanRemote sb3;
	protected AccessTableConfigurazioneEnteSessionBeanRemote sb4;
	protected AccessTableDominiSiruSessionBeanRemote sbArFse;
	protected String ente = "";
	protected int esito = 0;
	protected String msg = "Operazione conclusa correttamente";
	
	
	private void setEsitoOperazione(int codice, String messaggio){
		this.esito = codice;
		this.msg = messaggio;
	}
	
	private String verificaInviante(String nomeInviante, Long idInviante) {
	
		String invianteResult = sbArgo.findCodRoutingInviante(nomeInviante, idInviante);
		
		if(!StringUtils.isBlank(invianteResult))
			setEsitoOperazione(0, "Inviante abilitato");
		else if(invianteResult  == null)
			setEsitoOperazione(-10, "KO - Inviante non presente o non abilitato");
		
		return invianteResult;
	}
	
	private boolean isRicercaDecodifica(String tabella, String key){
		String[] lst = !StringUtils.isBlank(tabella) ? tabella.split(",") : new String[0];
		List<String> lista = new ArrayList<String>();
		for(String s : lst)
			lista.add(s.trim());
		
		return lista.isEmpty() || lista.contains(key);
	}
	
	private List<String> estraiInterventiSS() throws Throwable{
		try{
			it.webred.ss.ejb.dto.BaseDTO dto = new it.webred.ss.ejb.dto.BaseDTO();
			dto.setEnteId(ente);
			 
		return sb3.readInterventiTrascodifiche(dto );
		}catch(Throwable ex){
			setEsitoOperazione(-12, "Eccezione reperimento interventi della scheda sociale:"+ TableConfigurazioniCostanti.CS_TB_SERVIZI);
			throw ex;
		}
	}
	 
	private List<InterventoDTO> estraiInterventiCustom() throws Throwable{
		try{
			BaseDTO dto = new BaseDTO();
			dto.setEnteId(ente);
			dto.setObj(Long.parseLong("0")); // il find all non legge questo valore
			List<InterventoDTO> listInterventoDTO =  sb.findTipiIntCustomConfigurazione(dto);
			return listInterventoDTO;
		}catch(Throwable ex){
			setEsitoOperazione(-13, "Eccezione reperimento "+TableConfigurazioniCostanti.CS_TB_PRESTAZIONI);
			throw ex;
		}
	}
	private List<TabellaDecodificaBaseDTO> estraiTbTitoloStudio() throws Throwable{
		try{
			BaseDTO dto = new BaseDTO();
			dto.setEnteId(ente);
			dto.setObj(Long.parseLong("0")); // il find all non legge questo valore
			List<CsTbTitoloStudio> listTitoloStudio =  sb.getTbTitoloStudioAbilitato(dto);
			List<TabellaDecodificaBaseDTO> listTabDecodificaDTO = new ArrayList<TabellaDecodificaBaseDTO>();
			for(CsTbTitoloStudio csTbTs : listTitoloStudio) {
				TabellaDecodificaDTO  tabDTO = new TabellaDecodificaDTO();
				tabDTO.setDescrizione(csTbTs.getDescrizione());
				tabDTO.setTooltip(csTbTs.getTooltip());
				tabDTO.setId(String.valueOf(csTbTs.getId()));
				listTabDecodificaDTO.add(tabDTO);
			}
		return listTabDecodificaDTO;
		}catch(Throwable ex){
			setEsitoOperazione(-13, "Eccezione reperimento "+TableConfigurazioniCostanti.CS_TB_TITOLO_STUDIO);
			throw ex;
		}
	}
	private List<TabellaDecodificaBaseDTO> estraiTipologieFamiliari() throws Throwable{
		try{
	//		BaseDTO dto = new BaseDTO();
	//		dto.setEnteId(ente);
	//		dto.setObj(Long.parseLong("0")); // il find all non legge questo valore
			CeTBaseObject cetBase = new CeTBaseObject();
			cetBase.setEnteId(ente);
			  
			List<CsTbTipologiaFamiliare> listTipologiaFamiliare =  sb.getTipologieFamiliari(cetBase);
			List<TabellaDecodificaBaseDTO> listTabDecodificaDTO = new ArrayList<TabellaDecodificaBaseDTO>();
			for(CsTbTipologiaFamiliare csTbTf : listTipologiaFamiliare) {
				TabellaDecodificaDTO tabDTO = new TabellaDecodificaDTO();
				tabDTO.setDescrizione(csTbTf.getDescrizione());
				tabDTO.setTooltip(csTbTf.getTooltip());
				tabDTO.setId(String.valueOf(csTbTf.getId()));
				listTabDecodificaDTO.add(tabDTO);
			}
			return listTabDecodificaDTO;
		}catch(Throwable ex){
			setEsitoOperazione(-13, "Eccezione reperimento "+TableConfigurazioniCostanti.CS_TB_TIPOLOGIA_FAMILIARE);
			throw ex;
		}
	}
	//getGrVulnerabilita
	private List<TabellaDecodificaDTO> estraiGrVulnerabilita() throws Throwable{
		try{
		BaseDTO dto = new BaseDTO();
		dto.setEnteId(ente);
		dto.setObj(Long.parseLong("0")); // il find all non legge questo valore
		List<CsTbGVulnerabile> listGruppoVulnerabile =  sb.getGruppiVulnerab(dto);
		List<TabellaDecodificaDTO> listTabDecodificaDTO = new ArrayList<TabellaDecodificaDTO>();
		for(CsTbGVulnerabile csTbTf : listGruppoVulnerabile) {
			TabellaDecodificaDTO tabDTO = new TabellaDecodificaDTO();
			tabDTO.setDescrizione(csTbTf.getDescrizione());
			tabDTO.setId(csTbTf.getId());
			tabDTO.setTooltip(csTbTf.getTooltip());
			listTabDecodificaDTO.add(tabDTO);
		}
		return listTabDecodificaDTO;
		}catch(Throwable ex){
			setEsitoOperazione(-13, "Eccezione reperimento "+TableConfigurazioniCostanti.CS_TB_GRUPPO_VULNERABILE);
			throw ex;
		}
	}
	
	private List<TabellaDecodificaExtDTO> estraiStatoCivile() throws Throwable{
		try{
		BaseDTO dto = new BaseDTO();
		dto.setEnteId(ente);
		dto.setObj(Long.parseLong("0")); // il find all non legge questo valore
		List<CsTbStatoCivile> listStatoCivile =  sb.getStatoCivile(dto);
		List<TabellaDecodificaExtDTO> listTabDecodificaDTO = new ArrayList<TabellaDecodificaExtDTO>();
		for(CsTbStatoCivile csTbSC : listStatoCivile) {
			TabellaDecodificaExtDTO tabDTO = new TabellaDecodificaExtDTO();
			tabDTO.setDescrizione(csTbSC.getDescrizione());
			tabDTO.setCod(csTbSC.getCod());
			tabDTO.setTooltip(csTbSC.getTooltip());
			listTabDecodificaDTO.add(tabDTO);
		}
		return listTabDecodificaDTO;
		}catch(Throwable ex){
			setEsitoOperazione(-13, "Eccezione reperimento "+TableConfigurazioniCostanti.CS_TB_STATO_CIVILE);
			throw ex;
		}
	}
	
	private List<TabellaDecodificaExtDTO> estraiPrestazioniIstat() throws Throwable{
		try{
		BaseDTO dto = new BaseDTO();
		dto.setEnteId(ente);
		List<ArTClasse> lst =  sb.findArTClasseAll(dto);
		List<TabellaDecodificaExtDTO> listTabDecodificaDTO = new ArrayList<TabellaDecodificaExtDTO>();
		for(ArTClasse csTbSC : lst) {
			TabellaDecodificaExtDTO tabDTO = new TabellaDecodificaExtDTO();
			tabDTO.setDescrizione(csTbSC.getDescrizione());
			tabDTO.setCod(csTbSC.getCodiceMemo());
			tabDTO.setTooltip(csTbSC.getDescrizione2());
			listTabDecodificaDTO.add(tabDTO);
		}
		return listTabDecodificaDTO;
		}catch(Throwable ex){
			setEsitoOperazione(-13, "Eccezione reperimento "+TableConfigurazioniCostanti.CS_TB_TI_COD_ISTAT);
			throw ex;
		}
	}
	
	private List<TabellaDecodificaBaseDTO> estraiCondLavoro() throws Throwable{
		try{
		BaseDTO dto = new BaseDTO();
		dto.setEnteId(ente);
		dto.setObj(Long.parseLong("0")); // il find all non legge questo valore
		List<CsTbCondLavoro> lst =  sb.getCondLavoro(dto);
		List<TabellaDecodificaBaseDTO> listTabDecodificaDTO = new ArrayList<TabellaDecodificaBaseDTO>();
		for(CsTbCondLavoro csTbCL : lst) {
			TabellaDecodificaDTO tabDTO = new TabellaDecodificaDTO();
			tabDTO.setDescrizione(csTbCL.getDescrizione());
			tabDTO.setTooltip(csTbCL.getTooltip());
			tabDTO.setId(String.valueOf(csTbCL.getId()));
			listTabDecodificaDTO.add(tabDTO);
		}
		return listTabDecodificaDTO;
		}catch(Throwable ex){
			setEsitoOperazione(-13, "Eccezione reperimento "+TableConfigurazioniCostanti.CS_TB_COND_LAVORO);
			throw ex;
		}

	}
	
	private List<TabellaDecodificaBaseDTO> estraiFonteFinanziamento() throws Throwable{
		try{
			BaseDTO dto = new BaseDTO();
			dto.setEnteId(ente);
			List<VLineaFin> lst =  sb.findAllOrigineFinanziamenti(dto);
			List<TabellaDecodificaBaseDTO> listTabDecodificaDTO = new ArrayList<TabellaDecodificaBaseDTO>();
			for(VLineaFin s : lst) {
				if(s.getAbilitato()!=null && s.getAbilitato()){
					TabellaDecodificaBaseDTO tabDTO = new TabellaDecodificaBaseDTO();
					tabDTO.setDescrizione(s.getDescrizione());
					listTabDecodificaDTO.add(tabDTO);
				}
			}
			return listTabDecodificaDTO;
		}catch(Throwable ex){
			setEsitoOperazione(-13, "Eccezione reperimento "+TableConfigurazioniCostanti.AR_FONTE_FINANZIAMENTO);
			throw ex;
		}
	}
	
	private List<TabellaDecodificaBaseDTO> estraiProgetti(String enteInviante) throws Throwable{
		try{
		BaseDTO dto = new BaseDTO();
		dto.setEnteId(ente);
		dto.setObj(enteInviante);
		List<KeyValueDTO> lst =  sb.findProgettiByBelfioreOrganizzazione(dto);
		List<TabellaDecodificaBaseDTO> listTabDecodificaDTO = new ArrayList<TabellaDecodificaBaseDTO>();
		for(KeyValueDTO s : lst) {
			if(s.isAbilitato()){
				TabellaDecodificaBaseDTO tabDTO = new TabellaDecodificaBaseDTO();
				tabDTO.setDescrizione(s.getDescrizione());
				listTabDecodificaDTO.add(tabDTO);
			}
		}
		return listTabDecodificaDTO;
		}catch(Throwable ex){
			setEsitoOperazione(-13, "Eccezione reperimento "+TableConfigurazioniCostanti.AR_FF_PROGETTO);
			throw ex;
		}
	}
	
	private List<TabellaDecodificaBaseDTO> estraiArFseChk(String tabella, String key) throws Throwable{
		try{
			BaseDTO dto = new BaseDTO();
			dto.setEnteId(ente);
			dto.setObj(Long.parseLong("0")); // il find all non legge questo valore
			List<SiruDominioDTO> lista =  sbArFse.findAll(key);
			List<TabellaDecodificaBaseDTO> listTabDecodificaDTO = new ArrayList<TabellaDecodificaBaseDTO>();
			for(SiruDominioDTO s : lista) {
				TabellaDecodificaDTO tabDTO = new TabellaDecodificaDTO();
				tabDTO.setDescrizione(s.getDescrizione());
				tabDTO.setId(s.getCodiceSiru());
				listTabDecodificaDTO.add(tabDTO);
			}
			return listTabDecodificaDTO;
		}catch(Throwable ex){
			setEsitoOperazione(-13, "Eccezione reperimento:"+tabella);
			throw ex;
		}
	}
	
	public TrascodificheResponseDTO estraiTabellaConfigurazione( it.webred.cs.csa.ejb.dto.rest.TrascodificheRequestDTO input){
		
		TrascodificheResponseDTO responseDTO = new TrascodificheResponseDTO();
		
		try {
		 	
			Long idInviante = input.getIdInviante();
			String nomeInviante = input.getNomeInviante();
			String nomeTabella = input.getNomeTabella();
			
			String codRoutingInviante = this.verificaInviante(nomeInviante, idInviante);
			
			if(esito == 0){	
			
				fillEnteCapofila(codRoutingInviante);
				if(!StringUtils.isBlank(ente)){
					if(isRicercaDecodifica(nomeTabella, TableConfigurazioniCostanti.CS_TB_SERVIZI)) 
						responseDTO.setTbServizi(estraiInterventiSS());
					
					if(isRicercaDecodifica(nomeTabella, TableConfigurazioniCostanti.CS_TB_PRESTAZIONI)) 
						responseDTO.setTbPrestazioni(estraiInterventiCustom());
					
					if(isRicercaDecodifica(nomeTabella, TableConfigurazioniCostanti.CS_TB_TITOLO_STUDIO)) 
						responseDTO.setTbTitoloStudio(estraiTbTitoloStudio());
					
					if(isRicercaDecodifica(nomeTabella, TableConfigurazioniCostanti.CS_TB_TIPOLOGIA_FAMILIARE)) 
						responseDTO.setTbTipologiaFamiliare(estraiTipologieFamiliari());
					
					if(isRicercaDecodifica(nomeTabella, TableConfigurazioniCostanti.CS_TB_GRUPPO_VULNERABILE)) 
						responseDTO.setTbGruppoVulnerabile(estraiGrVulnerabilita());
					
					if(isRicercaDecodifica(nomeTabella, TableConfigurazioniCostanti.CS_TB_STATO_CIVILE)) 
						responseDTO.setTbStatoCivile(estraiStatoCivile());
					
					if(isRicercaDecodifica(nomeTabella, TableConfigurazioniCostanti.CS_TB_COND_LAVORO)) 
						responseDTO.setTbCondLavoro(estraiCondLavoro());
					
					if(isRicercaDecodifica(nomeTabella, TableConfigurazioniCostanti.CS_TB_TI_COD_ISTAT))
						responseDTO.setTbTiCodIstat(estraiPrestazioniIstat());
					
				 /* Per estrarre queste informazioni serve l'ente TITOLARE, non è sufficiente il capofila*/
					if(isRicercaDecodifica(nomeTabella, TableConfigurazioniCostanti.AR_FONTE_FINANZIAMENTO)) 
						responseDTO.setTbFonteFinanziamento(estraiFonteFinanziamento());
					
					if(isRicercaDecodifica(nomeTabella, TableConfigurazioniCostanti.AR_FF_PROGETTO)) 
						responseDTO.setTbProgetto(estraiProgetti(codRoutingInviante));
						
					if(this.isRicercaDecodifica(nomeTabella, TableConfigurazioniCostanti.AR_FSE_COND_ING_MERCATO_LAVORO))
						responseDTO.setTbFseCondIngMercatoLavoro(estraiArFseChk(TableConfigurazioniCostanti.AR_FSE_COND_ING_MERCATO_LAVORO, SiruEnum.CONDIZIONE_MERCATO.name()));
					
					if(this.isRicercaDecodifica(nomeTabella, TableConfigurazioniCostanti.AR_FSE_AZ_DIMENSIONE))
						responseDTO.setTbFseAzDimensione(estraiArFseChk(TableConfigurazioniCostanti.AR_FSE_AZ_DIMENSIONE, SiruEnum.DIMENSIONE_AZIENDA.name()));
					
					if(this.isRicercaDecodifica(nomeTabella, TableConfigurazioniCostanti.AR_FSE_DURATA_RICERCA_LAVORO))
						responseDTO.setTbFseDurataRicercaLavoro(estraiArFseChk(TableConfigurazioniCostanti.AR_FSE_DURATA_RICERCA_LAVORO, SiruEnum.DURATA_RICERCA.name()));
					
					if(this.isRicercaDecodifica(nomeTabella, TableConfigurazioniCostanti.AR_FSE_GRUPPO_VULNERABILE))
						responseDTO.setTbFseGruppoVulnerabile(estraiArFseChk(TableConfigurazioniCostanti.AR_FSE_GRUPPO_VULNERABILE, SiruEnum.GRUPPO_VUL_PART.name()));
					
					if(this.isRicercaDecodifica(nomeTabella, TableConfigurazioniCostanti.AR_FSE_ORARIO_LAVORO))
						responseDTO.setTbFseOrarioLavoro(estraiArFseChk(TableConfigurazioniCostanti.AR_FSE_ORARIO_LAVORO, SiruEnum.TIPO_ORARIO_LAVORO.name()));
					
					if(this.isRicercaDecodifica(nomeTabella, TableConfigurazioniCostanti.AR_FSE_TIPOLOGIA_LAVORO))
						responseDTO.setTbFseTipologiaLavoro(estraiArFseChk(TableConfigurazioniCostanti.AR_FSE_TIPOLOGIA_LAVORO, SiruEnum.TIPOLOGIA_LAVORO.name()));
					
					if(this.isRicercaDecodifica(nomeTabella, TableConfigurazioniCostanti.AR_FSE_TITOLO_STUDIO))
						responseDTO.setTbFseTitoloStudio(estraiArFseChk(TableConfigurazioniCostanti.AR_FSE_TITOLO_STUDIO, SiruEnum.TITOLO_STUDIO.name()));		
				}else
					setEsitoOperazione(-12, "Errore di elaborazione: ente capofila non trovato");
			}
		

		}catch(Throwable e){
			logger.error(e.getMessage(), e);
		}finally{
			responseDTO.setEsito(this.esito);
			responseDTO.setDescrizione(this.msg);
		}
		
		return responseDTO;
	}
	
	 
	public AccessTableConfigurazioneSessionBeanClient() throws NamingException {
		super();
		sb = (AccessTableConfigurazioneSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
		sb3 =  (ConfigurazioneSessionBeanRemote) ClientUtility.getEjbInterface("SegretariatoSoc", "SegretariatoSoc_EJB", "ConfigurazioneSessionBean");	
		sb4 = (AccessTableConfigurazioneEnteSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneEnteSessionBean");
		sbArFse = (AccessTableDominiSiruSessionBeanRemote)ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB","AccessTableDominiSiruSessionBean");
		sbArgo = (ArConfigurazioneService)ClientUtility.getEjbInterface("Argo", "Argo_EJB","ArConfigurazioneServiceBean");
 
	}

	private void fillEnteCapofila(String belfiore) {
		//Recupero il capofila per ogni CS
    	CsOOrganizzazione org = null;
    	try{
	    	CeTBaseObject dto = new CeTBaseObject();
	    	dto.setEnteId(belfiore);
	    	logger.debug("Ricerca capofila per ente: "+belfiore);
	    	org = sb4.getOrganizzazioneCapofila(dto);
	    	
	    	ente = org!=null ? org.getCodRouting() : null;
	    	
    	}catch(Exception err){
    		logger.error("Errore recupero capofila per:"+belfiore, err);
    	}	
	}
}