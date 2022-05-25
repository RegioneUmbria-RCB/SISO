package it.webred.cs.json.stranieri.ver1;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import it.webred.cs.json.dto.JsonBaseBean;
import it.webred.cs.json.dto.KeyValueDTO;
import it.webred.cs.json.stranieri.StranieriManBaseBean.ARRIVO_IN_ITALIA;
import it.webred.jsf.bean.ComuneBean;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StranieriBean extends JsonBaseBean {

	private KeyValueDTO nazioneOrigine; //id=codice istat nazione
	private boolean minoreNonAccompagnato=false;
	
	private boolean linguaItaAttestato=false;
	private boolean etaNonScolastica  =false;
	private String  liguaItaLivello;
	private Integer linguaItaComprensione;
	private Integer linguaItaParlato;
	private Integer linguaItaLettura;
	private Integer linguaItaScrittura;
	private String  altreLingue;
	private String  istitutoRilascio;
    private ComuneBean comuneRilascio;
	
	/*Arrivo in Italia*/
	private String arrivoItalia;
	private String annoPrimoPermSogg;
	private String annoPrimoArrivoITA;
	private boolean daSoloPrimoArrivoITA=false;
	private ComuneBean comuneValicoFrontiera;
	private KeyValueDTO ultimaNazioneProvenienza;
	
	/*Arrivo in Regione*/
	private String arrivoRegione;
	private String annoPrimoArrivoREG;
	private boolean protezioneInternazionale=false;
	private ComuneBean comuneUltimoArrivoREG;
	private String nomeStruttAccoglienza;
	private String indirizzoStruttAccoglienza;
	
	/*Condizione Giuridica*/
	private KeyValueDTO status;
	private boolean presenteDaOltre3Mesi=false;
	private String statoPermessoSogg;
	private Date scadPermessoSogg;
	private KeyValueDTO permesso;
	private String idPraticaPermesso;
	private KeyValueDTO assenzaPermessoSoggiorno; //SISO - 792
	
	@JsonIgnore
	private boolean validaConoscenzaLingua=true;
	
	@JsonIgnore
	private boolean validaDatiImmigrazione=true;
	
	@JsonIgnore
	private boolean validaProfugoMigrante=false;
	
	public StranieriBean(){
		nazioneOrigine = new KeyValueDTO();
		ultimaNazioneProvenienza = new KeyValueDTO();
		status = new KeyValueDTO();
		permesso = new KeyValueDTO();
		assenzaPermessoSoggiorno=new KeyValueDTO();
	}
	
	@Override
	public StranieriBean autoClone() throws Exception{
		StranieriBean clone = (StranieriBean)super.autoClone();
		
		//clone = (StranieriBean)SerializationUtils.clone(this); //Alternativa più lenta (dice) del blocco successivo
		
		clone.setNazioneOrigine((KeyValueDTO)BeanUtils.cloneBean(this.nazioneOrigine));
		clone.setUltimaNazioneProvenienza((KeyValueDTO)BeanUtils.cloneBean(this.ultimaNazioneProvenienza));
		clone.setStatus((KeyValueDTO)BeanUtils.cloneBean(this.status));
		clone.setPermesso((KeyValueDTO)BeanUtils.cloneBean(this.permesso));
		clone.setAssenzaPermessoSoggiorno((KeyValueDTO)BeanUtils.cloneBean(this.assenzaPermessoSoggiorno));
		clone.setComuneValicoFrontiera(this.comuneValicoFrontiera!=null ? (ComuneBean)BeanUtils.cloneBean(this.comuneValicoFrontiera) : null);
		clone.setComuneUltimoArrivoREG(this.comuneUltimoArrivoREG!=null ? (ComuneBean)BeanUtils.cloneBean(this.comuneUltimoArrivoREG) : null);
		clone.setComuneRilascio(this.comuneRilascio!=null ? (ComuneBean)BeanUtils.cloneBean(this.comuneRilascio) : null);
		
		return clone;
	}
	
	public Boolean getMinoreNonAccompagnato() {
		return minoreNonAccompagnato;
	}

	public String getArrivoItalia() {
		return arrivoItalia;
	}

	public String getArrivoRegione() {
		return arrivoRegione;
	}

	public String getAnnoPrimoArrivoREG() {
		return annoPrimoArrivoREG;
	}

	public String getStatoPermessoSogg() {
		return statoPermessoSogg;
	}

	public Date getScadPermessoSogg() {
		return scadPermessoSogg;
	}

	public void setMinoreNonAccompagnato(Boolean minoreNonAccompagnato) {
		this.minoreNonAccompagnato = minoreNonAccompagnato;
	}

	public void setArrivoItalia(String arrivoItalia) {
		this.arrivoItalia = arrivoItalia;
	}

	public void setArrivoRegione(String arrivoRegione) {
		this.arrivoRegione = arrivoRegione;
	}

	public void setAnnoPrimoArrivoREG(String annoPrimoArrivoREG) {
		this.annoPrimoArrivoREG = annoPrimoArrivoREG;
	}

	public void setStatoPermessoSogg(String statoPermessoSogg) {
		this.statoPermessoSogg = statoPermessoSogg;
	}

	public void setScadPermessoSogg(Date scadPermessoSogg) {
		this.scadPermessoSogg = scadPermessoSogg;
	}

	public String getNomeStruttAccoglienza() {
		return nomeStruttAccoglienza;
	}

	public String getIndirizzoStruttAccoglienza() {
		return indirizzoStruttAccoglienza;
	}

	public void setNomeStruttAccoglienza(String nomeStruttAccoglienza) {
		this.nomeStruttAccoglienza = nomeStruttAccoglienza;
	}

	public void setIndirizzoStruttAccoglienza(String indirizzoStruttAccoglienza) {
		this.indirizzoStruttAccoglienza = indirizzoStruttAccoglienza;
	}

	public List<String> checkObbligatorieta(){
		List<String> messages = new LinkedList<String>();
		if(this.validaDatiImmigrazione){
		/*	if(this.nazioneOrigine.getCodice()==null || this.nazioneOrigine.getCodice().isEmpty()) 
				 messages.add("'Paese di Origine Nucleo Familiare' è un campo obbligatorio");    */
			if(this.status.getCodice()==null || this.status.getCodice().isEmpty()) 
				 messages.add("'Status' è un campo obbligatorio");
		/*	if(this.statoPermessoSogg==null || this.statoPermessoSogg.isEmpty())
				messages.add("Selezionare una condizione relativa al permesso di soggiorno");*/
			
			boolean permesso = "in attesa di permesso".equalsIgnoreCase(this.statoPermessoSogg)||
					           "in possesso di permesso".equalsIgnoreCase(this.statoPermessoSogg)||
					           "in attesa di rinnovo".equalsIgnoreCase(this.statoPermessoSogg);
			if(permesso && (this.permesso.getCodice()==null || this.permesso.getCodice().isEmpty()))
				messages.add("'Tipo permesso' è un campo obbligatorio");
		
		/*	if(this.minoreNonAccompagnato && (this.nomeStruttAccoglienza==null || this.nomeStruttAccoglienza.isEmpty()))
					messages.add("'Struttura di accoglienza' è un campo obbligatorio");*/
		
			if(this.protezioneInternazionale && this.comuneUltimoArrivoREG==null)
				messages.add("'Comune titolare SPRAR' è un campo obbligatorio");
		
		}
		
		if(this.validaDatiImmigrazione || this.validaConoscenzaLingua){
			if(!this.etaNonScolastica){
				if(this.linguaItaAttestato && (this.liguaItaLivello==null || this.liguaItaLivello.isEmpty()))
					messages.add("Inserire il livello di conoscenza della lingua italiana");
				else if(!this.linguaItaAttestato){
					if(this.linguaItaComprensione==null || this.linguaItaComprensione.intValue()<=0)
						messages.add("Autovalutare la capacità di comprendere la lingua italiana");
					if(this.linguaItaLettura==null || this.linguaItaLettura.intValue()<=0)
						messages.add("Autovalutare la capacità di leggere in lingua italiana");
					if(this.linguaItaParlato==null || this.linguaItaParlato.intValue()<=0)
						messages.add("Autovalutare la capacità di parlare in lingua italiana");
					if(this.linguaItaScrittura==null || this.linguaItaScrittura.intValue()<=0)
						messages.add("Autovalutare la capacità di scrivere in lingua italiana");
				}
			}
		}
		
		if(this.validaProfugoMigrante) {
			String cond = "per il gruppo vulnerabile selezionato";
			if(this.ultimaNazioneProvenienza == null || StringUtils.isBlank(ultimaNazioneProvenienza.getCodice()))
			   messages.add("'Ultimo paese di provenienza' è un campo obbligatorio "+cond);
			
			if(ARRIVO_IN_ITALIA.DALLA_NASCITA.getCodice().equals(arrivoItalia))
				messages.add("Arrivo in Italia '"+ ARRIVO_IN_ITALIA.DALLA_NASCITA.getDescrizione() +"' non ammesso "+cond);
		}
		
		return messages;
		
	}

	public ComuneBean getComuneValicoFrontiera() {
		return comuneValicoFrontiera;
	}

	public void setComuneValicoFrontiera(ComuneBean comuneValicoFrontiera) {
		this.comuneValicoFrontiera = comuneValicoFrontiera;
	}

	public ComuneBean getComuneUltimoArrivoREG() {
		return comuneUltimoArrivoREG;
	}

	public void setComuneUltimoArrivoREG(ComuneBean comuneUltimoArrivoREG) {
		this.comuneUltimoArrivoREG = comuneUltimoArrivoREG;
	}

	public KeyValueDTO getNazioneOrigine() {
		return nazioneOrigine;
	}

	public String getIdPraticaPermesso() {
		return idPraticaPermesso;
	}

	public void setIdPraticaPermesso(String idPraticaPermesso) {
		this.idPraticaPermesso = idPraticaPermesso;
	}

	public KeyValueDTO getUltimaNazioneProvenienza() {
		return ultimaNazioneProvenienza;
	}

	public void setNazioneOrigine(KeyValueDTO nazioneOrigine) {
		this.nazioneOrigine = nazioneOrigine;
	}

	public void setUltimaNazioneProvenienza(KeyValueDTO ultimaNazioneProvenienza) {
		this.ultimaNazioneProvenienza = ultimaNazioneProvenienza;
	}

	public KeyValueDTO getStatus() {
		return status;
	}

	public KeyValueDTO getPermesso() {
		return permesso;
	}

	public void setStatus(KeyValueDTO status) {
		this.status = status;
	}

	public void setPermesso(KeyValueDTO permesso) {
		this.permesso = permesso;
	}

	public Integer getLinguaItaComprensione() {
		return linguaItaComprensione;
	}

	public Integer getLinguaItaParlato() {
		return linguaItaParlato;
	}

	public Integer getLinguaItaLettura() {
		return linguaItaLettura;
	}

	public Integer getLinguaItaScrittura() {
		return linguaItaScrittura;
	}

	public void setLinguaItaComprensione(Integer linguaItaComprensione) {
		this.linguaItaComprensione = linguaItaComprensione;
	}

	public void setLinguaItaParlato(Integer linguaItaParlato) {
		this.linguaItaParlato = linguaItaParlato;
	}

	public void setLinguaItaLettura(Integer linguaItaLettura) {
		this.linguaItaLettura = linguaItaLettura;
	}

	public void setLinguaItaScrittura(Integer linguaItaScrittura) {
		this.linguaItaScrittura = linguaItaScrittura;
	}

	public String getLiguaItaLivello() {
		return liguaItaLivello;
	}

	public String getAltreLingue() {
		return altreLingue;
	}

	public void setLiguaItaLivello(String liguaItaLivello) {
		this.liguaItaLivello = liguaItaLivello;
	}

	public void setAltreLingue(String altreLingue) {
		this.altreLingue = altreLingue;
	}

	public String getIstitutoRilascio() {
		return istitutoRilascio;
	}

	public ComuneBean getComuneRilascio() {
		return comuneRilascio;
	}

	public void setIstitutoRilascio(String istitutoRilascio) {
		this.istitutoRilascio = istitutoRilascio;
	}

	public void setComuneRilascio(ComuneBean comuneRilascio) {
		this.comuneRilascio = comuneRilascio;
	}

	public String getAnnoPrimoPermSogg() {
		return annoPrimoPermSogg;
	}

	public void setAnnoPrimoPermSogg(String annoPrimoPermSogg) {
		this.annoPrimoPermSogg = annoPrimoPermSogg;
	}

	public String getAnnoPrimoArrivoITA() {
		return annoPrimoArrivoITA;
	}

	public void setAnnoPrimoArrivoITA(String annoPrimoArrivoITA) {
		this.annoPrimoArrivoITA = annoPrimoArrivoITA;
	}

	public boolean isPresenteDaOltre3Mesi() {
		return presenteDaOltre3Mesi;
	}

	public void setPresenteDaOltre3Mesi(boolean presenteDaOltre3Mesi) {
		this.presenteDaOltre3Mesi = presenteDaOltre3Mesi;
	}

	public boolean isLinguaItaAttestato() {
		return linguaItaAttestato;
	}

	public boolean isEtaNonScolastica() {
		return etaNonScolastica;
	}

	public void setEtaNonScolastica(boolean etaNonScolastica) {
		this.etaNonScolastica = etaNonScolastica;
	}

	public boolean isDaSoloPrimoArrivoITA() {
		return daSoloPrimoArrivoITA;
	}

	public boolean isProtezioneInternazionale() {
		return protezioneInternazionale;
	}

	public void setLinguaItaAttestato(boolean linguaItaAttestato) {
		this.linguaItaAttestato = linguaItaAttestato;
	}

	public void setDaSoloPrimoArrivoITA(boolean daSoloPrimoArrivoITA) {
		this.daSoloPrimoArrivoITA = daSoloPrimoArrivoITA;
	}

	public void setProtezioneInternazionale(boolean protezioneInternazionale) {
		this.protezioneInternazionale = protezioneInternazionale;
	}

	public void setValidaConoscenzaLingua(boolean valida) {
		this.validaConoscenzaLingua=valida;
	}

	public void setValidaDatiImmigrazione(boolean validaDatiImmigrazione) {
		this.validaDatiImmigrazione=validaDatiImmigrazione;
	}
	
	public void setValidaProfugoMigrante(boolean valida) {
		this.validaProfugoMigrante = valida;
	}

	public KeyValueDTO getAssenzaPermessoSoggiorno() {
		return assenzaPermessoSoggiorno;
	}

	public void setAssenzaPermessoSoggiorno(KeyValueDTO assenzaPermessoSoggiorno) {
		this.assenzaPermessoSoggiorno = assenzaPermessoSoggiorno;
	}
	
	
	

	
}
