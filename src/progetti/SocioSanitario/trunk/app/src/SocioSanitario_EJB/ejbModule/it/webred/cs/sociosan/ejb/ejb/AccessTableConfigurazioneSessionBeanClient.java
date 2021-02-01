package it.webred.cs.sociosan.ejb.ejb;

import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.ejb.Stateless;
import javax.naming.NamingException;

import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.rest.InterventoDTO;
import it.webred.cs.csa.ejb.dto.rest.TabellaDecodificaBaseDTO;
import it.webred.cs.csa.ejb.dto.rest.TabellaDecodificaDTO;
import it.webred.cs.csa.ejb.dto.rest.TabellaDecodificaExtDTO;
import it.webred.cs.csa.ejb.dto.rest.TrascodificheResponseDTO;
import it.webred.cs.data.model.ArBiInviante;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsTbCondLavoro;
import it.webred.cs.data.model.CsTbGVulnerabile;
import it.webred.cs.data.model.CsTbStatoCivile;
import it.webred.cs.data.model.CsTbTipologiaFamiliare;
import it.webred.cs.data.model.CsTbTitoloStudio;
import it.webred.cs.sociosan.ejb.client.AccessTableConfigurazioneSessionBeanClientRemote;
import it.webred.cs.sociosan.ejb.utils.TableConfigurazioniCostanti;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;

@Stateless
public class AccessTableConfigurazioneSessionBeanClient   extends BaseSessionBean implements AccessTableConfigurazioneSessionBeanClientRemote {

	protected AccessTableConfigurazioneSessionBeanRemote sb;
	protected AccessTableInterventoSessionBeanRemote sb2;
	protected SsSchedaSessionBeanRemote sb3;
	protected String ente = "";
	protected int esito = 0;
	protected String msg = "Operazione conclusa correttamente.";
	
	
	private void setEsitoOperazione(int codice, String messaggio){
		this.esito = codice;
		this.msg = messaggio;
	}
	
	private void verificaInviante(String nomeInviante,
			Long idInviante) {
		
	 	BaseDTO dto = new BaseDTO();
		dto.setEnteId(ente);
		dto.setObj(nomeInviante);
		dto.setObj2(idInviante); 
	    ArBiInviante invianteResult = sb.findInviante(dto);
		
		
		if(invianteResult  == null)
			setEsitoOperazione(-10, "KO - Utente non presente.");
		else if(invianteResult.getAbilitato().equals("0")){
			setEsitoOperazione(-11, "KO - Utente non abilitato.");
		}
			
		setEsitoOperazione(0, "Utente abilitato.");
		 
	}
	
	private List<String> estraiInterventiSS(){
		try{
			it.webred.ss.ejb.dto.BaseDTO dto = new it.webred.ss.ejb.dto.BaseDTO();
			dto.setEnteId(ente);
			 
		return sb3.readInterventiTrascodifiche(dto );
		}catch(Exception ex){
			ex.printStackTrace();
			setEsitoOperazione(-12, "Eccezione reperimento interventi della scheda sociale.");
			return null;
		}
	}
	 
	private List<InterventoDTO> estraiInterventiCustom(){
		try{
		BaseDTO dto = new BaseDTO();
		dto.setEnteId(ente);
		dto.setObj(Long.parseLong("0")); // il find all non legge questo valore
		List<InterventoDTO> listInterventoDTO =  sb2.findTipiIntCustomConfigurazione(dto);
		return listInterventoDTO;
		}
		catch(Exception ex){
			setEsitoOperazione(-13, "Eccezione reperimento interventi custom.");
		}
		return null;
	}
	private List<TabellaDecodificaBaseDTO> estraiTbTitoloStudio(){
		try{
		BaseDTO dto = new BaseDTO();
		dto.setEnteId(ente);
		dto.setObj(Long.parseLong("0")); // il find all non legge questo valore
		List<CsTbTitoloStudio> listTitoloStudio =  sb.getTbTitoloStudioAbilitato(dto);
		List<TabellaDecodificaBaseDTO> listTabDecodificaDTO = new ArrayList<TabellaDecodificaBaseDTO>();
		for(CsTbTitoloStudio csTbTs : listTitoloStudio) {
			TabellaDecodificaBaseDTO  tabDTO = new TabellaDecodificaBaseDTO();
			tabDTO.setDescrizione(csTbTs.getDescrizione());
			//tabDTO.setId(csTbTs.getId());
			tabDTO.setTooltip(csTbTs.getTooltip());
			listTabDecodificaDTO.add(tabDTO);
		}
		return listTabDecodificaDTO;
		}
		catch(Exception ex){
			setEsitoOperazione(-13, "Eccezione reperimento titolo di studio.");
		}
		return null;
	}
	private List<TabellaDecodificaBaseDTO> estraiTipologieFamiliari(){
		try{
//		BaseDTO dto = new BaseDTO();
//		dto.setEnteId(ente);
//		dto.setObj(Long.parseLong("0")); // il find all non legge questo valore
		CeTBaseObject cetBase = new CeTBaseObject();
		cetBase.setEnteId(ente);
		  
		List<CsTbTipologiaFamiliare> listTipologiaFamiliare =  sb.getTipologieFamiliari(cetBase);
		List<TabellaDecodificaBaseDTO> listTabDecodificaDTO = new ArrayList<TabellaDecodificaBaseDTO>();
		for(CsTbTipologiaFamiliare csTbTf : listTipologiaFamiliare) {
			TabellaDecodificaBaseDTO tabDTO = new TabellaDecodificaBaseDTO();
			tabDTO.setDescrizione(csTbTf.getDescrizione());
			tabDTO.setTooltip(csTbTf.getTooltip());
			listTabDecodificaDTO.add(tabDTO);
		}
		return listTabDecodificaDTO;
		}
		catch(Exception ex){
			setEsitoOperazione(-13, "Eccezione reperimento tipologie familiari.");
		}
		return null;
	}
	//getGrVulnerabilita
	private List<TabellaDecodificaDTO> estraiGrVulnerabilita(){
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
		}
		catch(Exception ex){
			setEsitoOperazione(-13, "Eccezione reperimento gruppo vulnerabilità.");
		}
		return null;
	}
	
	private List<TabellaDecodificaExtDTO> estraiStatoCivile(){
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
		}
		catch(Exception ex){
			setEsitoOperazione(-13, "Eccezione reperimento stato Civile.");
		}
		return null;
	}
	
	//getCondLavoro(CeTBaseObject cet)
	private List<TabellaDecodificaBaseDTO> estraiCondLavoro(){
		try{
		BaseDTO dto = new BaseDTO();
		dto.setEnteId(ente);
		dto.setObj(Long.parseLong("0")); // il find all non legge questo valore
		List<CsTbCondLavoro> listStatoCivile =  sb.getCondLavoro(dto);
		List<TabellaDecodificaBaseDTO> listTabDecodificaDTO = new ArrayList<TabellaDecodificaBaseDTO>();
		for(CsTbCondLavoro csTbCL : listStatoCivile) {
			TabellaDecodificaBaseDTO tabDTO = new TabellaDecodificaBaseDTO();
			tabDTO.setDescrizione(csTbCL.getDescrizione());
			tabDTO.setTooltip(csTbCL.getTooltip());
			listTabDecodificaDTO.add(tabDTO);
		}
		return listTabDecodificaDTO;
		}
		catch(Exception ex){
			setEsitoOperazione(-13, "Eccezione reperimento stato Civile.");
		}
		return null;
	}
	
	public TrascodificheResponseDTO estraiTabellaConfigurazione( it.webred.cs.csa.ejb.dto.rest.TrascodificheRequestDTO input){
		 
		 
		Long idInviante = input.getIdInviante();
		String nomeInviante = input.getNomeInviante();
		String nomeTabella = input.getNomeTabella();
		
		getEnteCapofila();
		
		this.verificaInviante(nomeInviante, idInviante);
		
		TrascodificheResponseDTO responseDTO = new TrascodificheResponseDTO();
		

		responseDTO.setEsito(  this.esito);
		responseDTO.setDescrizione(this.msg);
		if(esito == 0){
			if(nomeTabella == null || nomeTabella.equals("") || nomeTabella.equals("TUTTE")) {
				responseDTO.setInterventoCustom(estraiInterventiCustom());
				responseDTO.setIntervento(estraiInterventiSS());
				responseDTO.setTitoloStudio(estraiTbTitoloStudio());
				responseDTO.setTipologiaFamiglia(estraiTipologieFamiliari());
				responseDTO.setGruppoVulnerabile(estraiGrVulnerabilita());
				responseDTO.setStatoCivile(estraiStatoCivile());
				responseDTO.setCondLavoro(estraiCondLavoro());
					
			}else {
				if(nomeTabella.equalsIgnoreCase(TableConfigurazioniCostanti.CS_TB_SERVIZI)) {
					responseDTO.setIntervento(estraiInterventiSS());
				
				}else if(nomeTabella.equalsIgnoreCase(TableConfigurazioniCostanti.CS_TB_PRESTAZIONI)) {
					responseDTO.setInterventoCustom(estraiInterventiCustom());
				
				}
				else if(nomeTabella.equalsIgnoreCase(TableConfigurazioniCostanti.CS_TB_TITOLO_STUDIO)) {
					responseDTO.setTitoloStudio(estraiTbTitoloStudio());
				
				}
				else if(nomeTabella.equalsIgnoreCase(TableConfigurazioniCostanti.CS_TB_TIPOLOGIA_FAMILIARE)) {
					responseDTO.setTitoloStudio(estraiTipologieFamiliari());
				
				}
				else if(nomeTabella.equalsIgnoreCase(TableConfigurazioniCostanti.CS_TB_GRUPPO_VULNERABILE)) {
					responseDTO.setGruppoVulnerabile(estraiGrVulnerabilita());
				
				}
				else if(nomeTabella.equalsIgnoreCase(TableConfigurazioniCostanti.CS_TB_STATO_CIVILE)) {
					responseDTO.setStatoCivile(estraiStatoCivile());
				
				}
				else if(nomeTabella.equalsIgnoreCase(TableConfigurazioniCostanti.CS_TB_COND_LAVORO)) {
					responseDTO.setCondLavoro(estraiCondLavoro());
				
				}
			}
			
		}
		
		return responseDTO;
	}
	
	 
	public AccessTableConfigurazioneSessionBeanClient() throws NamingException {
		super();
		sb = (AccessTableConfigurazioneSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
		sb2 =  (AccessTableInterventoSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableInterventoSessionBean");	
		sb3 =  (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface("SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");	
		
	}

	
	 private void getEnteCapofila() {
		
		//recupero tutti gli enti dal properties per far girare il task su tutti i DB
		String path = System.getProperty("jboss.server.config.dir") + "\\datarouter.properties";
		String newpath = "file:///" + path.replaceAll("\\\\", "/");
		URL url;
		try {
			
			
			url = new URL(newpath);

			Properties props = new Properties();
			props.load(url.openStream());
			Enumeration e = props.propertyNames();
			
		
			List<String> capofila = new ArrayList<String>();
		
			while (e.hasMoreElements()) {
		    	//Recupero il capofila per ogni CS
		    	String enteId = (String) e.nextElement();
		    	CsOOrganizzazione org = null;
		    	try{
			    	CeTBaseObject dto = new CeTBaseObject();
			    	dto.setEnteId(enteId);
			    	org = sb.getOrganizzazioneCapofila(dto);
		    	}catch(Exception err){
		    		logger.error("Errore recupero capofila per:"+enteId, err);
		    	}
		    	
		    	if(org!=null && !capofila.contains(org.getCodRouting())) 
		    		capofila.add(org.getCodRouting());
		    }
		    	
			this.ente = capofila.get(0);
		    
		    
		} catch (Exception e) {
			logger.error("Lettura Tabelle di configurazione: Eccezione: " + e.getMessage(), e);
		}
	
	}

}

