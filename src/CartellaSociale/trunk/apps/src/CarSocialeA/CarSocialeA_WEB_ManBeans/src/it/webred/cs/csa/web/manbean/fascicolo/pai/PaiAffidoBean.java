package it.webred.cs.csa.web.manbean.fascicolo.pai;

import it.webred.cs.csa.ejb.client.AccessTablePaiAffidoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSchedaSessionBeanRemote;
import it.webred.cs.csa.ejb.client.domini.AccessTableDominiPaiSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.pai.affido.CsPaiAffidoDTO;
import it.webred.cs.csa.ejb.dto.pai.affido.CsPaiAffidoDominioDTO;
import it.webred.cs.csa.ejb.dto.pai.affido.CsPaiAffidoSoggFamigliaDTO;
import it.webred.cs.csa.ejb.dto.pai.affido.CsPaiAffidoSoggettoDTO;
import it.webred.cs.csa.ejb.dto.pai.affido.PaiAffidoDominiEnum;
import it.webred.cs.csa.ejb.dto.pai.affido.PaiAffidoStatoEnum;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.data.model.CsAFamigliaGruppo;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.utils.type.Constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.faces.event.ValueChangeEvent;

public class PaiAffidoBean extends CsUiCompBaseBean {
	
	//SERVIZI
	protected AccessTablePaiAffidoSessionBeanRemote paiAffidoService = (AccessTablePaiAffidoSessionBeanRemote) getCarSocialeEjb("AccessTablePaiAffidoSessionBean");
	protected AccessTableSchedaSessionBeanRemote schedaService = (AccessTableSchedaSessionBeanRemote) getCarSocialeEjb( "AccessTableSchedaSessionBean");
	protected AccessTableDominiPaiSessionBeanRemote dominiPaiService = (AccessTableDominiPaiSessionBeanRemote) getCarSocialeEjb( "AccessTableDominiPaiSessionBean");
	
	private final Long GENITORE = new Long(44);
	private final Long GENITORE_ACQUISITO = new Long(45);
	private final Long GENITORE_AFFIDATARIO = new Long(46);
	
	//VARIABILI
	private CsPaiAffidoDTO affido;
	private Integer statoAffido;
	private Boolean ricaricaFamAff = false;
	private Boolean ricaricaFamOrig = false;
	//SOGGETTO
	private String cognome;
	private String nome;
	private String codiceRuolo;
	
	
	public PaiAffidoBean(){
		this.affido = null;
	}
	
	public void reset(){
		this.affido = null;
	}
	
		
	public void nuovo(){
		this.affido = new CsPaiAffidoDTO(PaiAffidoStatoEnum.VALUTAZIONE_CASO, PaiAffidoDominiEnum.NATURA_ACCOGLIENZA.name() + "_GIUDIZIALE");
		
		statoAffido =  affido.getCodiceStatoAttuale();
		setRicaricaFamAff(false);
		setRicaricaFamOrig(false);
		
		logger.debug("Inizializzato nuovo affido");
	}
	
	public void findAffidoByPai(Long diarioPaiId){
		
		BaseDTO bdto = new BaseDTO();
		bdto.setObj(diarioPaiId);
		fillEnte(bdto);
		
		try {
		    affido = paiAffidoService.findAffidoByDiarioPaiId(bdto);
			
		    if(affido == null){
			    nuovo();
		    }else{
		    	statoAffido =  affido.getCodiceStatoAttuale();
		    	setRicaricaFamAff(false);
				setRicaricaFamOrig(false);
		    	logger.debug("Inizializzato affido da PAI " + diarioPaiId);
		    }
		} catch (Exception e) {
			addError("Inizializzazione Affido", "Affido non inizializzato");
			logger.error("Errore inizializzazine affido",e);
		}
	}
	
		
	public String validaAffidoPai(Long idSoggetto,Date dataPaiInizio){
		
		//ricarica famiglia origine
//		if(ricaricaFamOrig){
//			affido.getFamigliaOrigine().setCsAComponenteIdMadre(null);
//			affido.getFamigliaOrigine().setCsAComponenteIdPadre(null);
//		}
		//ricarica famiglia affidataria
//		if(ricaricaFamAff){
//			affido.getFamigliaAffidataria().setCsAComponenteIdMadre(null);
//			affido.getFamigliaAffidataria().setCsAComponenteIdPadre(null);
//		}
		
				
		/* Non posso procedere al salvataggio se
		 * 1) lo stato selezionato è almeno famiglia individuata e non ci sono i genitori di affido in cartella sociale
		 */
		if(statoAffido >= PaiAffidoStatoEnum.FAMIGLIA_INDIVIDUATA.getValore() &&
		   ((affido.getFamigliaAffidataria().getCsAComponenteIdMadre() == null && affido.getFamigliaAffidataria().getCsAComponenteIdPadre() == null) ||
		   (affido.getFamigliaOrigine().getCsAComponenteIdMadre() == null && affido.getFamigliaOrigine().getCsAComponenteIdPadre() == null)))
		{
			//controllo che sono stati inseriti in cartella
			BaseDTO bdto = new BaseDTO();
			fillEnte(bdto);
			bdto.setObj(idSoggetto);
			bdto.setObj2(new CsAFamigliaGruppo());
			
			try{
				List<CsAFamigliaGruppo> famg = (List<CsAFamigliaGruppo>) schedaService.findCsBySoggettoId(bdto);
				List<CsAComponente> comps = new ArrayList<CsAComponente>();
				//prendo solo quello attivo
				for(CsAFamigliaGruppo fg : famg){
					if(fg.getDataFineApp().equals(DataModelCostanti.END_DATE)){
						comps.addAll(fg.getCsAComponentes());
					}
				}
				
				for(CsAComponente c : comps){
					if(c != null && c.getCsTbTipoRapportoCon().getId().equals(GENITORE_AFFIDATARIO)){
						
						if(affido.getFamigliaAffidataria().getCsAComponenteIdMadre() == null && c.getCsAAnagrafica().getSesso().equals("F")){
							affido.getFamigliaAffidataria().setCsAComponenteIdMadre(c.getId());
						}
						
						if(affido.getFamigliaAffidataria().getCsAComponenteIdPadre() == null && c.getCsAAnagrafica().getSesso().equals("M")){
							affido.getFamigliaAffidataria().setCsAComponenteIdPadre(c.getId());
						}
					}
					
//					if(c != null && (c.getCsTbTipoRapportoCon().getId().equals(GENITORE) || c.getCsTbTipoRapportoCon().getId().equals(GENITORE_ACQUISITO))){
//						if(affido.getFamigliaOrigine().getCsAComponenteIdMadre() == null && c.getCsAAnagrafica().getSesso().equals("F")){
//							affido.getFamigliaOrigine().setCsAComponenteIdMadre(c.getId());
//						}
//						
//						if(affido.getFamigliaOrigine().getCsAComponenteIdPadre() == null && c.getCsAAnagrafica().getSesso().equals("M")){
//							affido.getFamigliaOrigine().setCsAComponenteIdPadre(c.getId());
//						}
//					}
				}
			}catch(Exception e){
				return "Dati famiglia affidataria non presenti, Inserirli in Anagrafica RISORSE Cartella Sociale";
			}
			
			if(affido.getFamigliaAffidataria().getCsAComponenteIdMadre() == null && affido.getFamigliaAffidataria().getCsAComponenteIdPadre() == null){
				return "Dati famiglia affidataria non presenti, Inserirli in Anagrafica RISORSE Cartella Sociale";
			}
		}
		
		/* Non posso procedere al salvataggio se
		 * 1) il flag genitori sconosciuti è spento e non ci sono i genitori di origine in cartella sociale
		 */
		if(!affido.getFamigliaOrigine().getGenitoriSconosciuti() &&
			affido.getFamigliaOrigine().getCsAComponenteIdMadre() == null &&
			affido.getFamigliaOrigine().getCsAComponenteIdPadre() == null){
			
			//controllo che sono stati inseriti in cartella
			BaseDTO bdto = new BaseDTO();
			fillEnte(bdto);
			bdto.setObj(idSoggetto);
			bdto.setObj2(new CsAFamigliaGruppo());
			
			try{
				List<CsAFamigliaGruppo> famg = (List<CsAFamigliaGruppo>) schedaService.findCsBySoggettoId(bdto);
				List<CsAComponente> comps = new ArrayList<CsAComponente>();
				//prendo solo quello attivo
				for(CsAFamigliaGruppo fg : famg){
					if(fg.getDataFineApp().equals(DataModelCostanti.END_DATE)){
						comps.addAll(fg.getCsAComponentes());
					}
				}
				
				for(CsAComponente c : comps){					
					if(c != null && (c.getCsTbTipoRapportoCon().getId().equals(GENITORE) || c.getCsTbTipoRapportoCon().getId().equals(GENITORE_ACQUISITO))){
						if(affido.getFamigliaOrigine().getCsAComponenteIdMadre() == null && c.getCsAAnagrafica().getSesso().equals("F")){
							affido.getFamigliaOrigine().setCsAComponenteIdMadre(c.getId());
						}
						
						if(affido.getFamigliaOrigine().getCsAComponenteIdPadre() == null && c.getCsAAnagrafica().getSesso().equals("M")){
							affido.getFamigliaOrigine().setCsAComponenteIdPadre(c.getId());
						}
					}
				}
			}catch(Exception e){
				return "Dati famiglia origine non presenti, Inserirli in Anagrafica RISORSE Cartella Sociale";
			}
			
			if(affido.getFamigliaOrigine().getCsAComponenteIdMadre() == null && affido.getFamigliaOrigine().getCsAComponenteIdPadre() == null){
				return "Dati famiglia origine non presenti, Inserirli in Anagrafica RISORSE Cartella Sociale";
			}
		}
		//validazione flag genitori sconosciuti
		if(affido.getFamigliaOrigine().getCsAComponenteIdMadre() != null ||
		   affido.getFamigliaOrigine().getCsAComponenteIdPadre() != null){
				affido.getFamigliaOrigine().setGenitoriSconosciuti(Boolean.FALSE);
		}
		
		/* Non posso procedere al salvataggio se
		 * 1) affido è non adottabile e avanzo di stato
		 */
		if(statoAffido > affido.getCodiceStatoAttuale() && 
				((PaiAffidoDominiEnum.IDONEITA_AFFIDATARI.name() + "_NON_IDONEI_SOGGETTO").equals(affido.getFamigliaAffidataria().getCodiceIdoneita()) ||
				 (PaiAffidoDominiEnum.IDONEITA_AFFIDATARI.name() + "_NON_IDONEA_FAMIGLIA").equals(affido.getFamigliaAffidataria().getCodiceIdoneita()))
		  ){
			return "Non è possibile cambiare di stato se l'affido è non idoneo";
		}
		
		return null;
	}
	
	public void salva(Long diarioPaiId){
		affido.setDiarioPaiId(diarioPaiId);
		BaseDTO bdto = new BaseDTO();
		bdto.setObj(affido);
		bdto.setObj2(statoAffido);
		fillEnte(bdto);
		
		try {
			
			
			this.paiAffidoService.saveAffido(bdto);
			//reset
			this.affido = null;
			
			addInfo("Salvataggio Affido", "Affido Salvato correttamente");
			logger.debug("Affido Salvato correttamente");
		} catch (Exception e) {
			addError("Salvataggio Affido", "Errore salvataggio affido");
			logger.error("Errore salvataggio affido",e);
		}
	}
		
	public List<CsPaiAffidoSoggFamigliaDTO> getFamigliaAff(){
		List<CsPaiAffidoSoggFamigliaDTO> famiglia = new ArrayList<CsPaiAffidoSoggFamigliaDTO>();
		if(affido.getFamigliaAffidataria().getMadre() != null){
			famiglia.add(affido.getFamigliaAffidataria().getMadre());
		}
		if(affido.getFamigliaAffidataria().getPadre() != null){
			famiglia.add(affido.getFamigliaAffidataria().getPadre());
		}
		
		return famiglia;
	}
	
	public List<CsPaiAffidoSoggFamigliaDTO> getFamigliaOrig(){
		List<CsPaiAffidoSoggFamigliaDTO> famiglia = new ArrayList<CsPaiAffidoSoggFamigliaDTO>();
		if(affido.getFamigliaOrigine().getMadre() != null){
			famiglia.add(affido.getFamigliaAffidataria().getMadre());
		}
		if(affido.getFamigliaOrigine().getPadre() != null){
			famiglia.add(affido.getFamigliaOrigine().getPadre());
		}
		
		return famiglia;
	}
	
	public Boolean isEntitaAffidoRequired(){
		if(statoAffido >= PaiAffidoStatoEnum.AFFIDO_ATTIVO.getValore()){
			return true;
		}
		return false;
	}
	
	public Boolean isTipoAccoglienzaRequired(){
		if(statoAffido >= PaiAffidoStatoEnum.AFFIDO_ATTIVO.getValore()){
			return true;
		}
		return false;
	}
	
	public Boolean isCollocamentoRequired(){
		if(statoAffido >= PaiAffidoStatoEnum.AFFIDO_ATTIVO.getValore()){
			return true;
		}
		return false;
	}
		
	public Boolean isAffidoGiudiziale(){
		if(affido.getCodiceNaturaAccoglienza() != null && affido.getCodiceNaturaAccoglienza().equals(PaiAffidoDominiEnum.NATURA_ACCOGLIENZA.name() + "_GIUDIZIALE")){
			return true;
		}
		return false;
	}
	
	public Boolean isAffidoParziale(){
		if(affido.getCodiceEntitaAffido() != null && affido.getCodiceEntitaAffido().endsWith("_PARZIALE")){
			return true;
		}
		return false;
	}
	
	public Boolean isEsitoAffidoAltro(){
		if(affido.getCodiceEsitoAffido() != null && affido.getCodiceEsitoAffido().equals(PaiAffidoDominiEnum.ESITO_AFFIDO.name() + "_ALTRO")){
			return true;
		}
		return false;
	}
	
	public void cambioStato(ValueChangeEvent event){
		statoAffido = (Integer) event.getNewValue();
	}
	
	public Boolean isStato(Integer value){
		if(statoAffido != null && statoAffido.compareTo(value) >= 0){
			return true;
		}
		return false;
	}
	
	public Boolean mostraFamigliaAffBase(){
		if(affido.getFamigliaAffidataria().getCsAComponenteIdMadre() == null && affido.getFamigliaAffidataria().getCsAComponenteIdPadre() == null){
			return true;
		}
		return false;
	}
	
	public void aggiungiSoggetto(){
		CsPaiAffidoSoggettoDTO soggetto = new CsPaiAffidoSoggettoDTO();
		soggetto.setCognome(cognome);
		soggetto.setNome(nome);
		soggetto.setCodiceRuolo(codiceRuolo);
		
		BaseDTO bdto = new BaseDTO();
		bdto.setObj(PaiAffidoDominiEnum.RUOLO_SOGGETTO.name());
		bdto.setObj2(codiceRuolo);
		fillEnte(bdto);
		soggetto.setDescrizioneRuolo(paiAffidoService.getDescrizioneDominio(bdto));
		
		affido.getSoggettiAffido().add(soggetto);
		
		cognome = null;
		nome = null;
		codiceRuolo = null;
	}
	
	public Boolean isStatoDisabilitato(Integer valore){
		return (affido.getCodiceStatoAttuale() - valore) > 0;
	}
	
	public Boolean isAffidoAccoglienza(){
		return (PaiAffidoDominiEnum.TIPO_AFFIDO.name() + "_ACCOGLIENZA").equalsIgnoreCase(affido.getCodiceTipoAffido());
	}
	
	public List<PaiAffidoStatoEnum> getListaStati(){
		return Arrays.asList(PaiAffidoStatoEnum.values());
	}
	
	public List<CsPaiAffidoDominioDTO> getListaNaturaAccoglienza(){
		BaseDTO b = new BaseDTO();
		b.setObj(PaiAffidoDominiEnum.NATURA_ACCOGLIENZA.name());
		fillEnte(b);
		return dominiPaiService.findByDominio(b);
	}

	public List<CsPaiAffidoDominioDTO> getListaTipoAccoglienza(){
		BaseDTO b = new BaseDTO();
		b.setObj(PaiAffidoDominiEnum.TIPO_ACCOGLIENZA.name());
		fillEnte(b);
		return dominiPaiService.findByDominio(b);
	}
	
	public List<CsPaiAffidoDominioDTO> getListaCollocamento(){
		BaseDTO b = new BaseDTO();
		b.setObj(PaiAffidoDominiEnum.COLLOCAMENTO.name());
		fillEnte(b);
		return dominiPaiService.findByDominio(b);
	}
	
	public List<CsPaiAffidoDominioDTO> getListaEntitaAffido(){
		BaseDTO b = new BaseDTO();
		b.setObj(PaiAffidoDominiEnum.ENTITA_AFFIDO.name());
		fillEnte(b);
		return dominiPaiService.findByDominio(b);
	}
	
	public List<CsPaiAffidoDominioDTO> getListaSituazioniParticolari(){
		BaseDTO b = new BaseDTO();
		b.setObj(PaiAffidoDominiEnum.SITUAZIONI_PARTICOLARI.name());
		fillEnte(b);
		return dominiPaiService.findByDominio(b);
	}
	
	public List<CsPaiAffidoDominioDTO> getListaBancaFamiglie(){
		BaseDTO b = new BaseDTO();
		b.setObj(PaiAffidoDominiEnum.BANCA_DATI_FAMIGLIE.name());
		fillEnte(b);
		return dominiPaiService.findByDominio(b);
	}
	
	public List<CsPaiAffidoDominioDTO> getListaIdoneita(){
		BaseDTO b = new BaseDTO();
		b.setObj(PaiAffidoDominiEnum.IDONEITA_AFFIDATARI.name());
		fillEnte(b);
		return dominiPaiService.findByDominio(b);
	}
	
	public List<CsPaiAffidoDominioDTO> getListaFrequenzaContatti(){
		BaseDTO b = new BaseDTO();
		b.setObj(PaiAffidoDominiEnum.FREQUENZA_CONTATTI.name());
		fillEnte(b);
		return dominiPaiService.findByDominio(b);
	}
	
	public List<CsPaiAffidoDominioDTO> getListaAffidatari(){
		BaseDTO b = new BaseDTO();
		b.setObj(PaiAffidoDominiEnum.AFFIDATARI.name());
		fillEnte(b);
		return dominiPaiService.findByDominio(b);
	}
	
	public List<CsPaiAffidoDominioDTO> getListaConvivenzaOrigAff(){
		BaseDTO b = new BaseDTO();
		b.setObj(PaiAffidoDominiEnum.CONVIVENZA_ORIG_AFF.name());
		fillEnte(b);
		return dominiPaiService.findByDominio(b);
	}
	
	public List<CsPaiAffidoDominioDTO> getListaEsitoAffido(){
		BaseDTO b = new BaseDTO();
		b.setObj(PaiAffidoDominiEnum.ESITO_AFFIDO.name());
		fillEnte(b);
		return dominiPaiService.findByDominio(b);
	}
	
	public List<CsPaiAffidoDominioDTO> getListaRuoliSoggetto(){
		BaseDTO b = new BaseDTO();
		b.setObj(PaiAffidoDominiEnum.RUOLO_SOGGETTO.name());
		fillEnte(b);
		return dominiPaiService.findByDominio(b);
	}
	
	public List<CsPaiAffidoDominioDTO> getListaTipoAffido(){
		BaseDTO b = new BaseDTO();
		b.setObj(PaiAffidoDominiEnum.TIPO_AFFIDO.name());
		fillEnte(b);
		return dominiPaiService.findByDominio(b);
	}
	
	public List<CsPaiAffidoDominioDTO> getListaAdottabile(){
		BaseDTO b = new BaseDTO();
		b.setObj(PaiAffidoDominiEnum.ADOTTABILE.name());
		fillEnte(b);
		return dominiPaiService.findByDominio(b);
	}
	
	public List<CsPaiAffidoDominioDTO> getListaOrigineInterventi(){
		BaseDTO b = new BaseDTO();
		b.setObj(PaiAffidoDominiEnum.FAM_ORIG_INTERVENTI.name());
		fillEnte(b);
		return dominiPaiService.findByDominio(b);
	}
	
	
	
	public CsPaiAffidoDTO getAffido() {
		return affido;
	}

	public void setAffido(CsPaiAffidoDTO affido) {
		this.affido = affido;
	}

	public AccessTablePaiAffidoSessionBeanRemote getPaiAffidoService() {
		return paiAffidoService;
	}

	public void setPaiAffidoService(
			AccessTablePaiAffidoSessionBeanRemote paiAffidoService) {
		this.paiAffidoService = paiAffidoService;
	}

	public Integer getStatoAffido() {
		return statoAffido;
	}

	public void setStatoAffido(Integer statoAffido) {
		this.statoAffido = statoAffido;
	}


	public String getCognome() {
		return cognome;
	}


	public void setCognome(String cognome) {
		this.cognome = cognome;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getCodiceRuolo() {
		return codiceRuolo;
	}


	public void setCodiceRuolo(String codiceRuolo) {
		this.codiceRuolo = codiceRuolo;
	}


	public Boolean getRicaricaFamOrig() {
		return ricaricaFamOrig;
	}


	public void setRicaricaFamOrig(Boolean ricaricaFamOrig) {
		this.ricaricaFamOrig = ricaricaFamOrig;
	}


	public Boolean getRicaricaFamAff() {
		return ricaricaFamAff;
	}


	public void setRicaricaFamAff(Boolean ricaricaFamAff) {
		this.ricaricaFamAff = ricaricaFamAff;
	}

}
