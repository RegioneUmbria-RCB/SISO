package it.webred.ss.web.bean.wizard;

import java.math.BigDecimal;

import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;

import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsTbStatoCivile;
import it.webred.cs.jsf.manbean.FormazioneLavoroMan;
import it.webred.ct.data.access.basic.anagrafe.dto.IndirizzoAnagDTO;
import it.webred.jsf.bean.ComuneBean;
import it.webred.siso.ws.ricerca.dto.PersonaDettaglio;
import it.webred.ss.data.model.SsAnagrafica;
import it.webred.ss.data.model.SsIndirizzo;
import it.webred.ss.data.model.SsSchedaSegnalato;


public class Segnalato {
	
	private Anagrafica anagrafica;
	
	private String medico;
	private String tel;
	private String titTel;
	
	private String cel;
	private String titCel;
	
	private String email;
	private String titEmail;
	
	private String tesseraSanitaria;
	private boolean stp=false;
	private boolean invalidita=false;
	private Long percInvalidita;
	 
	private FormazioneLavoroMan formLavoroMan;
	
	private boolean senzaFissaDimora;
    private Indirizzo residenza = new Indirizzo(DataModelCostanti.TipoIndirizzo.RESIDENZA);
	private Indirizzo domicilio = new Indirizzo(DataModelCostanti.TipoIndirizzo.DOMICILIO);
	private boolean domicilioAsResidenza = true;
	private String noteDomicilio;
	
	
	public Segnalato(){
		anagrafica = new Anagrafica();
		formLavoroMan = new FormazioneLavoroMan();
		senzaFissaDimora=false;
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
	
	public void domicilioComeResidenzaChecked(){
    	if(this.domicilioAsResidenza){
    		domicilio = null;
    	}else
    		domicilio= new Indirizzo(DataModelCostanti.TipoIndirizzo.DOMICILIO);
    }
	
	public void initFromAnagraficaEsterna(PersonaDettaglio p, CsTbStatoCivile statoCivile) throws NamingException {
		anagrafica.initFromAnagraficaEsterna(p, statoCivile);
		
		residenza = new Indirizzo(DataModelCostanti.TipoIndirizzo.RESIDENZA);
		domicilio = new Indirizzo(DataModelCostanti.TipoIndirizzo.DOMICILIO);
		senzaFissaDimora=false;
	
		//residenza
		if(p.getComuneResidenza()!=null){
			ComuneBean comune = new ComuneBean(p.getComuneResidenza());
			residenza.setComune(comune);
		}else if(p.getNazioneResidenza()!=null)
			residenza.setNazione(p.getNazioneResidenza());
		
    	residenza.setVia(p.getIndirizzoCivicoResidenza());
	
		//domicilio
		boolean esisteDomicilio = p.getComuneDomicilio()!=null || !StringUtils.isBlank(p.getIndirizzoDomicilio());
		
		String istatComRes = p.getComuneResidenza()!=null ? p.getComuneResidenza().getCodIstatComune() : null;
		String istatComDom = p.getComuneDomicilio()!=null ? p.getComuneDomicilio().getCodIstatComune() : null;
		
		String istatNazRes = p.getNazioneResidenza()!=null ? p.getNazioneResidenza().getCodIstatNazione() : null;
		String istatNazDom = p.getNazioneDomicilio()!=null ? p.getNazioneDomicilio().getCodIstatNazione() : null;
		
		boolean stessoComune = StringUtils.equals(istatComRes, istatComDom) || StringUtils.equals(istatNazRes, istatNazDom);
		
		boolean stessaVia = !StringUtils.isBlank(p.getIndirizzoResidenza()) && p.getIndirizzoResidenza().equalsIgnoreCase(p.getIndirizzoDomicilio());
		
		boolean stessaResidenza = stessaVia && stessoComune;
		
		domicilioAsResidenza = !esisteDomicilio || stessaResidenza;
		this.domicilioComeResidenzaChecked();
		
        if(!domicilioAsResidenza){
			if(p.getComuneDomicilio()!=null) {
				ComuneBean comuneBean = new ComuneBean(p.getComuneDomicilio());
				domicilio.setComune(comuneBean); 
			}else if(p.getNazioneDomicilio()!=null)
				domicilio.setNazione(p.getNazioneDomicilio());
			
			String indirizzoDom = !StringUtils.isBlank(p.getIndirizzoDomicilio()) ? p.getIndirizzoDomicilio() : "";
			indirizzoDom+= p.getCivicoDomicilio()!=null ? ", "+p.getCivicoDomicilio() : "";
			domicilio.setVia(indirizzoDom);
        }

        this.tesseraSanitaria=p.getDocumentoSanitario();
        
	}

	public void initFromModel(SsSchedaSegnalato segnalato, boolean copia) {
		if(segnalato != null){
			
			if(segnalato.getAnagrafica()!=null){
				anagrafica.initFromAnagrafica(segnalato.getAnagrafica(), copia);
				anagrafica.setDisableAnagrafica(true);
			}else
				anagrafica.getComuneNazioneNascitaMan().setComuneValue();
			
			if(segnalato.getResidenza() != null)
				residenza.initFromModel(segnalato.getResidenza(), copia);
			
			domicilioAsResidenza = segnalato.getDomicilio()==null || 
					              (segnalato.getResidenza()!=null && segnalato.getDomicilio().getId().longValue()==segnalato.getResidenza().getId().longValue());
			this.domicilioComeResidenzaChecked();
			
			if(!domicilioAsResidenza)
			    domicilio.initFromModel(segnalato.getDomicilio(), copia);
			
			if(!copia) id = segnalato.getId();
		
			medico = segnalato.getMedico();
			tel = segnalato.getTelefono();
			titTel = segnalato.getTitolareTelefono();
			
			cel = segnalato.getCel();
			titCel = segnalato.getTitolareCellulare();
			
			email = segnalato.getEmail();
			titEmail = segnalato.getTitolareEmail();
			
			tesseraSanitaria = segnalato.getTessera_sanitaria();
			stp=segnalato.getStp()!=null && segnalato.getStp() ? true : false;
			invalidita = segnalato.getInvalidita()!=null;
			percInvalidita = segnalato.getInvalidita();
			
			senzaFissaDimora = (segnalato.getSenzaFissaDimora()!=null && segnalato.getSenzaFissaDimora());
			noteDomicilio = segnalato.getNoteDomicilio();
			
			
			//codTipologiaFam = segnalato.getTipologia_familiare();
			formLavoroMan.setIdCondLavorativa(segnalato.getLavoro()!=null ? new BigDecimal(segnalato.getLavoro()) : null);
			formLavoroMan.setIdProfessione(segnalato.getProfessione()!=null ? new BigDecimal(segnalato.getProfessione()) : null);
			formLavoroMan.setIdSettoreImpiego(segnalato.getSettImpiegoId()!=null ? segnalato.getSettImpiegoId() : null);
			formLavoroMan.setIdTitoloStudio(segnalato.getTitoloStudioId()!=null ? segnalato.getTitoloStudioId() : null);
		}
	}
	
	public void fillModel(SsSchedaSegnalato segnalatoModel) {
		segnalatoModel.setId(id);
	
		segnalatoModel.setAnagrafica(getAnagraficaModel());
		
		segnalatoModel.setMedico(medico!=null ? medico.toUpperCase() : null);
		segnalatoModel.setTelefono(tel);
		segnalatoModel.setTitolareTelefono(titTel);
		segnalatoModel.setCel(cel);
		segnalatoModel.setTitolareCellulare(titCel);
		segnalatoModel.setEmail(email);
		segnalatoModel.setTitolareEmail(titEmail);
		segnalatoModel.setTessera_sanitaria(tesseraSanitaria);
		segnalatoModel.setStp(stp);
		segnalatoModel.setInvalidita(this.invalidita ? this.percInvalidita : null);
		
		segnalatoModel.setSenzaFissaDimora(senzaFissaDimora);
		segnalatoModel.setNoteDomicilio(noteDomicilio);
		
		//segnalatoModel.setTipologia_familiare(codTipologiaFam);
		segnalatoModel.setLavoro(formLavoroMan.getIdCondLavorativa()!=null && formLavoroMan.getIdCondLavorativa().longValue()>0 ? formLavoroMan.getIdCondLavorativa().toString() : null);
		segnalatoModel.setProfessione(formLavoroMan.getIdProfessione()!=null && formLavoroMan.getIdProfessione().longValue()>0 ? formLavoroMan.getIdProfessione().toString() : null);	
	    segnalatoModel.setSettImpiegoId(formLavoroMan.getIdSettoreImpiego()!=null && formLavoroMan.getIdSettoreImpiego().longValue()>0 ? formLavoroMan.getIdSettoreImpiego() : null);
	    segnalatoModel.setTitoloStudioId(formLavoroMan.getIdTitoloStudio()!=null && formLavoroMan.getIdTitoloStudio().longValue()>0 ? formLavoroMan.getIdTitoloStudio() : null); 
	
		// save/update residenza e domicilio
		SsIndirizzo residenzaModel = new SsIndirizzo();
		residenza.fillModel(residenzaModel); // segnalato.isSenzaFissaDimora()

		SsIndirizzo domicilioModel = null;
		if (!this.isDomicilioAsResidenza() && domicilio.isValorizzato()){
			domicilioModel = new SsIndirizzo();
			domicilio.fillModel(domicilioModel);//, segnalato.isSenzaFissaDimora());
		}
		
		segnalatoModel.setResidenza(residenzaModel);
		segnalatoModel.setDomicilio(domicilioModel);
	
	}
	
	
	public void setResidenza(IndirizzoAnagDTO indirizzo, String belfiore){
		if (indirizzo!=null) {
			//residenza.setNumero(indirizzo.getCivico());
			residenza.setVia(indirizzo.getIndirizzoCompleto());
		}
		if(!StringUtils.isBlank(residenza.getVia()))
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
	public boolean isSenzaFissaDimora() {
		return senzaFissaDimora;
	}
	public void setSenzaFissaDimora(boolean senzaFissaDimora) {
		this.senzaFissaDimora = senzaFissaDimora;
	}
	public String getNoteDomicilio() {
		return noteDomicilio;
	}
	public void setNoteDomicilio(String noteDomicilio) {
		this.noteDomicilio = noteDomicilio;
	}
	
	public void onChangeSenzaFissaDimora(){
		if(this.senzaFissaDimora){
			this.residenza.reset(DataModelCostanti.TipoIndirizzo.RESIDENZA); 
			
			//Resetto il check per disabilitare
			this.setDomicilioAsResidenza(true);
			this.domicilioComeResidenzaChecked();
		}
	}
	public String getTitTel() {
		return titTel;
	}
	public String getTitCel() {
		return titCel;
	}
	public String getTitEmail() {
		return titEmail;
	}
	public void setTitTel(String titTel) {
		this.titTel = titTel;
	}
	public void setTitCel(String titCel) {
		this.titCel = titCel;
	}
	public void setTitEmail(String titEmail) {
		this.titEmail = titEmail;
	}
	public boolean isDomicilioAsResidenza() {
		return domicilioAsResidenza;
	}
	public void setDomicilioAsResidenza(boolean domicilioAsResidenza) {
		this.domicilioAsResidenza = domicilioAsResidenza;
	}
	
	public SsAnagrafica getAnagraficaModel(){
		SsAnagrafica ssanagrafica = null;
		if(!StringUtils.isBlank(anagrafica.getCodiceFiscale())){
			ssanagrafica = new SsAnagrafica();
			anagrafica.fillModel(ssanagrafica);
		}
		return ssanagrafica;
	}
}
