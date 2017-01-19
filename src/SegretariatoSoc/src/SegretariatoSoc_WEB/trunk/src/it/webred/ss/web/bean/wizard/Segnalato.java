package it.webred.ss.web.bean.wizard;

import it.webred.cs.jsf.manbean.FormazioneLavoroMan;
import it.webred.cs.json.abitazione.IAbitazione;
import it.webred.cs.json.familiariConviventi.IFamConviventi;
import it.webred.cs.json.stranieri.IStranieri;
import it.webred.ct.config.luoghi.LuoghiService;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ct.data.access.basic.anagrafe.dto.IndirizzoAnagrafeDTO;
import it.webred.ejb.utility.ClientUtility;
import it.webred.jsf.bean.ComuneBean;
import it.webred.siso.ws.client.anag.client.PersonaFindResult;
import it.webred.ss.data.model.SsAnagrafica;
import it.webred.ss.data.model.SsSchedaSegnalato;

import java.math.BigDecimal;

import javax.naming.NamingException;


public class Segnalato {
	
	private Anagrafica anagrafica;
	
	private String medico;
	private String tel;
	private String cel;
	private String email;
	private String tesseraSanitaria;
	private boolean stp=false;
	private boolean invalidita=false;
	private Long percInvalidita;
	 
	private FormazioneLavoroMan formLavoroMan;
	
	private Indirizzo residenza = new Indirizzo("tipo1", "residenza");
	private Indirizzo domicilio = new Indirizzo("tipo2", "domicilio");
	private String strutturaAccoglienza;
	
	public Segnalato(){
		anagrafica = new Anagrafica();
		formLavoroMan = new FormazioneLavoroMan();
	}
		
	private Long id;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Indirizzo getResidenza() {
		return residenza;
	}
	public void setResidenza(Indirizzo residenza) {
		this.residenza = residenza;
	}
	public Indirizzo getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(Indirizzo domicilio) {
		this.domicilio = domicilio;
	}
	public String getMedico() {
		return medico;
	}
	public void setMedico(String medico) {
		this.medico = medico;
	}
	
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getCel() {
		return cel;
	}
	public void setCel(String cel) {
		this.cel = cel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTesseraSanitaria() {
		return tesseraSanitaria;
	}
	public void setTesseraSanitaria(String tesseraSanitaria) {
		this.tesseraSanitaria = tesseraSanitaria;
	}
	
/*	public CsTbTipologiaFamiliare getTipologiaFamiliare() {
		return tipologiaFamiliare;
	}
	public void setTipologiaFamiliare(CsTbTipologiaFamiliare tipologiaFamiliare) {
		this.tipologiaFamiliare = tipologiaFamiliare;
	}
	
	public String getCodTipologiaFam() {
		return codTipologiaFam;
	}
	public void setCodTipologiaFam(String codTipologiaFam) {
		this.codTipologiaFam = codTipologiaFam;
	}
	
	*/
	
	public void domicilioComeResidenzaChecked(){
    	if(domicilio.isComeResidenza())
    		domicilio.reset();
    }
	
			
	public void initFromAnagraficaSanitaria(PersonaFindResult p) throws NamingException {
		anagrafica.initFromAnagraficaSanitaria(p);
		
		tesseraSanitaria = p.getNumeroTesseraSanitaria();
		stp=false;
		residenza = new Indirizzo();
		domicilio = new Indirizzo();
	
		LuoghiService luoghiService = (LuoghiService)  ClientUtility.getEjbInterface("CT_Service", "CT_Config_Manager", "LuoghiServiceBean");

		//residenza
		String istatComRes = p.getIstatComResidenza();
		AmTabComuni comRes = luoghiService.getComuneItaByIstat(istatComRes);
		if(comRes!=null) {
			p.setIstatComResidenza(comRes.getCodIstatComune());
			ComuneBean comuneBean = new ComuneBean(p.getIstatComResidenza(),comRes.getDenominazione(), comRes.getSiglaProv());
			residenza.setComune(comuneBean); 
		}
		String indirizzo = p.getIndirizzoDomicilio();
		indirizzo+= p.getCivicoDomicilio()!=null ? ", "+p.getCivicoDomicilio() : "";
		residenza.setVia(indirizzo);
		//residenza.setVia(p.getIndirizzoResidenza());
		//residenza.setNumero(p.getCivicoResidenza()!=null ? p.getCivicoResidenza() : null);
		
		//domicilio
		boolean stessoResidenza = istatComRes!=null && istatComRes.equals(p.getIstatComDomicilio()) &&
				                  p.getIndirizzoResidenza()!=null && p.getIndirizzoResidenza().equalsIgnoreCase(p.getIndirizzoDomicilio()) &&
				                  p.getCivicoResidenza()!=null && p.getCivicoResidenza().equals(p.getCivicoDomicilio());
		
		domicilio.setComeResidenza(stessoResidenza);
        if(!domicilio.isComeResidenza()){
			AmTabComuni comDom = luoghiService.getComuneItaByIstat(p.getIstatComDomicilio());
			if(comDom!=null) {
				p.setIstatComResidenza(comDom.getCodIstatComune());
				ComuneBean comuneBean = new ComuneBean(p.getIstatComDomicilio(),comRes.getDenominazione(), comRes.getSiglaProv());
				domicilio.setComune(comuneBean); 
			}
			indirizzo = p.getIndirizzoDomicilio();
			indirizzo+= p.getCivicoDomicilio()!=null ? ", "+p.getCivicoDomicilio() : "";
			domicilio.setVia(indirizzo);
			//domicilio.setVia(p.getIndirizzoDomicilio());
			//domicilio.setNumero(p.getCivicoDomicilio()!=null ? p.getCivicoDomicilio() : null);
		}
	}
	

	
	public void initFromModel(SsSchedaSegnalato segnalato, boolean copia) {
		if(segnalato != null){
			anagrafica.initFromAnagrafica(segnalato.getAnagrafica(), copia);
			anagrafica.setDisableAnagrafica(true);
			
			if(segnalato.getResidenza() != null)
				residenza.initFromModel(segnalato.getResidenza());
			if(segnalato.getDomicilio() != null){ 
				if(segnalato.getResidenza() != null && segnalato.getDomicilio().getId().longValue()!=segnalato.getResidenza().getId().longValue())
					domicilio.initFromModel(segnalato.getDomicilio());
				else{
					domicilio = new Indirizzo();
					domicilio.setComeResidenza(true);
				}
					
			}
			
			if(!copia) id = segnalato.getId();
		
			medico = segnalato.getMedico();
			tel = segnalato.getTelefono();
			cel = segnalato.getCel();
			email = segnalato.getEmail();
			tesseraSanitaria = segnalato.getTessera_sanitaria();
			stp=segnalato.getStp()!=null && segnalato.getStp() ? true : false;
			invalidita = segnalato.getInvalidita()!=null;
			percInvalidita = segnalato.getInvalidita();
			strutturaAccoglienza=segnalato.getStrutturaAccoglienza();
			
			//codTipologiaFam = segnalato.getTipologia_familiare();
			formLavoroMan.setIdCondLavorativa(segnalato.getLavoro()!=null ? new BigDecimal(segnalato.getLavoro()) : null);
			formLavoroMan.setIdProfessione(segnalato.getProfessione()!=null ? new BigDecimal(segnalato.getProfessione()) : null);
			formLavoroMan.setIdSettoreImpiego(segnalato.getSettImpiegoId()!=null ? segnalato.getSettImpiegoId() : null);
			formLavoroMan.setIdTitoloStudio(segnalato.getTitoloStudioId()!=null ? segnalato.getTitoloStudioId() : null);
		}
	}
	
	public void fillAnagraficaModel(SsAnagrafica ana){
		anagrafica.fillModel(ana);
	}
	
	public void fillModel(SsSchedaSegnalato segnalatoModel) {
		segnalatoModel.setId(id);
	
		SsAnagrafica ssanagrafica = new SsAnagrafica();
		fillAnagraficaModel(ssanagrafica);
		segnalatoModel.setAnagrafica(ssanagrafica);
		
		segnalatoModel.setMedico(medico!=null ? medico.toUpperCase() : null);
		segnalatoModel.setTelefono(tel);
		segnalatoModel.setCel(cel);
		segnalatoModel.setEmail(email);
		segnalatoModel.setTessera_sanitaria(tesseraSanitaria);
		segnalatoModel.setStp(stp);
		segnalatoModel.setInvalidita(this.invalidita ? this.percInvalidita : null);
		segnalatoModel.setStrutturaAccoglienza(strutturaAccoglienza);
		
		//segnalatoModel.setTipologia_familiare(codTipologiaFam);
		segnalatoModel.setLavoro(formLavoroMan.getIdCondLavorativa()!=null && formLavoroMan.getIdCondLavorativa().longValue()>0 ? formLavoroMan.getIdCondLavorativa().toString() : null);
		segnalatoModel.setProfessione(formLavoroMan.getIdProfessione()!=null && formLavoroMan.getIdProfessione().longValue()>0 ? formLavoroMan.getIdProfessione().toString() : null);	
	    segnalatoModel.setSettImpiegoId(formLavoroMan.getIdSettoreImpiego()!=null && formLavoroMan.getIdSettoreImpiego().longValue()>0 ? formLavoroMan.getIdSettoreImpiego() : null);
	    segnalatoModel.setTitoloStudioId(formLavoroMan.getIdTitoloStudio()!=null && formLavoroMan.getIdTitoloStudio().longValue()>0 ? formLavoroMan.getIdTitoloStudio() : null); 
	}
	
	
	public void setResidenza(IndirizzoAnagrafeDTO indirizzo, String belfiore){
		if (indirizzo!=null) {
			//residenza.setNumero(indirizzo.getCivico());
			residenza.setVia(indirizzo.getIndirizzoCompleto());
		}
		if(residenza.getVia() != null && !residenza.getVia().isEmpty())
			residenza.setComune(residenza.getComuneByBelfiore(belfiore));
	}

	public Anagrafica getAnagrafica() {
		return anagrafica;
	}
	public void setAnagrafica(Anagrafica anagrafica) {
		this.anagrafica = anagrafica;
	}
	public FormazioneLavoroMan getFormLavoroMan() {
		return formLavoroMan;
	}
	public void setFormLavoroMan(FormazioneLavoroMan formLavoroMan) {
		this.formLavoroMan = formLavoroMan;
	}
	public boolean isStp() {
		return stp;
	}
	public void setStp(boolean stp) {
		this.stp = stp;
	}
	public String getStrutturaAccoglienza() {
		return strutturaAccoglienza;
	}
	public void setStrutturaAccoglienza(String strutturaAccoglienza) {
		this.strutturaAccoglienza = strutturaAccoglienza;
	}
	public void onChangeStruttura(){
		Indirizzo indStruttura = new Indirizzo("tipo1", "residenza");
		//TODO:Recupero indirizzo struttura
		if(indStruttura.getVia()!=null && indStruttura.getComune()!=null)
			residenza = indStruttura;
	}
	
	public void onChangeInvalidita(){
		if(this.invalidita)
			this.percInvalidita=0L;
		else 
			this.percInvalidita=null;
	}
	public boolean isInvalidita() {
		return invalidita;
	}
	public Long getPercInvalidita() {
		return percInvalidita;
	}
	public void setInvalidita(boolean invalidita) {
		this.invalidita = invalidita;
	}
	public void setPercInvalidita(Long percInvalidita) {
		this.percInvalidita = percInvalidita;
	}


}
