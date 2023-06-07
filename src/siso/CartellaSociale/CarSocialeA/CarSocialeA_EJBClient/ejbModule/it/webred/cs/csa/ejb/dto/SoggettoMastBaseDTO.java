package it.webred.cs.csa.ejb.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsASoggettoLAZY;
 

public class SoggettoMastBaseDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected CsASoggettoLAZY csASoggetto;
	protected String nome;
	protected String cognome;
	protected String cf; //cf
	protected String cittadinanza;
	protected String cittadinanza2; //secondaCittadinanza
	protected Integer annoNascita;
	protected String sesso; 
	
	protected String viaResidenza;
	protected String comuneResidenza; //JSON
	protected String nazioneResidenza;//COD.ISTAT
	
	protected String comuneNascita; //JSON
	protected String nazioneNascita;//COD.ISTAT
	
	protected boolean datiValidi=false;
	
	protected boolean allineatoResidenza;
	protected boolean allineatoAnagrafica;
	
	protected List<String> tooltipDifferenzeAnagrafica;
	protected List<String> tooltipDifferenzeResidenza;
	
	//SISO-1021
	protected boolean nazioneResidenzaNonDefinita=false;

	protected boolean riferimento=false;
	
	public SoggettoMastBaseDTO() {
		this.cognome="";
		this.nome="";
		this.cf="";
		this.annoNascita = null;
		this.sesso = null;
		
		this.cittadinanza=null;
		this.cittadinanza2=null;
		
		this.nazioneResidenza=null;
		this.comuneResidenza=null;
		this.viaResidenza="";
		
		this.comuneNascita = null;
		this.nazioneNascita = null;
		this.riferimento = false;
	}

	public SoggettoMastBaseDTO(boolean riferimento){
		this();
		this.riferimento=riferimento;
	}
	
	public SoggettoMastBaseDTO(String cognome, String nome, String cf, String cittadinanza, Date dataNascita, 
								  String viaResidenza, String comuneResidenza, String nazioneResidenza, 
								  String comuneNascita, String nazioneNascita, 
								  String sesso, boolean riferimento){
		this.cognome=cognome;
		this.nome = nome;
		this.cf = cf;
		this.cittadinanza=cittadinanza;
		this.annoNascita = this.getAnno(dataNascita);
		this.riferimento = riferimento;
		
		this.viaResidenza = viaResidenza;
		this.comuneNascita = comuneNascita;
		this.nazioneNascita = nazioneNascita;
		
		this.comuneResidenza = comuneResidenza;
		this.nazioneResidenza = nazioneResidenza;
		this.sesso = sesso; //SISO-1138
		//this.datiValidi=true;
		this.datiValidi = isValorizzato(); // Se manca qualcosa devo inserirlo a mano, quindi non posso ritenerlo valido
	}
	
	
	//SISO-962 Inizio
	public SoggettoMastBaseDTO( CsASoggettoLAZY soggetto, String viaResidenza, String jsonLuogoResidenza, String statoEsteroResidenza, String jsonComuneNascita, boolean riferimento) {
		csASoggetto = soggetto;

		this.nazioneNascita = soggetto.getCsAAnagrafica().getStatoNascitaCod();
		this.comuneNascita = jsonComuneNascita;
	
		this.sincronizzaDatiAnagrafici(jsonComuneNascita);
		
		this.sincronizzaResidenza(viaResidenza, jsonLuogoResidenza, statoEsteroResidenza);
		
		//this.verificaDatiValidi(viaResidenza, jsonLuogoResidenza, statoEsteroResidenza );

		this.riferimento = riferimento;
	}
	
	public void integraDatiMancanti(CsASoggettoLAZY soggetto, String viaResidenza, String jsonLuogoResidenza, String nazioneResidenza,  String jsonComuneNascita){
		csASoggetto = soggetto;
		if(csASoggetto!=null){
			CsAAnagrafica ana = csASoggetto.getCsAAnagrafica();
			cognome = this.integraDatoMancante(cognome, ana.getCognome());
			nome = this.integraDatoMancante(nome, ana.getNome());
			cf = this.integraDatoMancante(cf, ana.getCf());
			Integer anno = getAnno(csASoggetto.getCsAAnagrafica().getDataNascita());
			if(annoNascita==null && anno!=null && anno>0)
				this.annoNascita = anno;
			cittadinanza = this.integraDatoMancante(cittadinanza, csASoggetto.getCsAAnagrafica().getCittadinanza());
			cittadinanza2 = this.integraDatoMancante(cittadinanza2, csASoggetto.getCsAAnagrafica().getCittadinanza2());
			sesso = this.integraDatoMancante(sesso, csASoggetto.getCsAAnagrafica().getSesso());		
			this.viaResidenza = this.integraDatoMancante(this.viaResidenza, viaResidenza);
			if(StringUtils.isBlank(this.comuneResidenza) && StringUtils.isBlank(this.nazioneResidenza)) {
				this.comuneResidenza = this.integraDatoMancante(this.comuneResidenza, jsonLuogoResidenza);
				this.nazioneResidenza = this.integraDatoMancante(this.nazioneResidenza, nazioneResidenza);
			}
			
			if(StringUtils.isBlank(this.comuneNascita) && StringUtils.isBlank(this.nazioneNascita)) {
				this.comuneNascita = this.integraDatoMancante(this.comuneNascita, jsonComuneNascita);
				this.nazioneNascita = this.integraDatoMancante(this.nazioneNascita, csASoggetto.getCsAAnagrafica().getStatoNascitaCod());
			}
			this.verificaDatiValidi(viaResidenza, jsonLuogoResidenza, nazioneResidenza, this.verificaAllineamentoAnagrafica(jsonComuneNascita));
			this.datiValidi = this.isValorizzato();
		}
	}
	//SISO-962 Fine
	
	protected String integraDatoMancante(String dest, String orig){
		if(StringUtils.isBlank(dest) && !StringUtils.isBlank(orig))
			dest = orig;
		return dest;
	}
	
	public void verificaAllineamentoCaso(String viaResidenza, String jsonLuogoResidenza, String statoEsteroResidenza, String jsonComuneNascita) {
		this.verificaAllineamentoAnagrafica(jsonComuneNascita);
		this.verificaAllineamentoResidenza(viaResidenza, jsonLuogoResidenza, statoEsteroResidenza);
	}
	
	
	public boolean verificaAllineamentoAnagrafica(String comuneNascita){
		allineatoAnagrafica = true;
		tooltipDifferenzeAnagrafica = new ArrayList<String>();
		if(csASoggetto!=null){
			CsAAnagrafica ana = csASoggetto.getCsAAnagrafica();
			
			verificaAllineamento(this.cf,ana.getCf(), "Cod.fiscale", tooltipDifferenzeAnagrafica, allineatoAnagrafica);
			verificaAllineamento(this.cognome, ana.getCognome(), "Cognome", tooltipDifferenzeAnagrafica, allineatoAnagrafica);
			verificaAllineamento(this.nome, ana.getNome(), "Nome", tooltipDifferenzeAnagrafica, allineatoAnagrafica);
			verificaAllineamento(this.nazioneNascita, ana.getStatoNascitaCod(), "Nazione Nascita (Cod.ISTAT)", tooltipDifferenzeAnagrafica, allineatoAnagrafica);
			verificaAllineamento(this.comuneNascita, comuneNascita, "Comune Nascita", tooltipDifferenzeAnagrafica, allineatoAnagrafica);	
			verificaAllineamento(this.cittadinanza, ana.getCittadinanza(), "Cittadinanza", tooltipDifferenzeAnagrafica, allineatoAnagrafica);
			verificaAllineamento(this.cittadinanza2, ana.getCittadinanza2(), "Seconda Cittadinanza", tooltipDifferenzeAnagrafica, allineatoAnagrafica);
			verificaAllineamento(this.sesso, ana.getSesso(), "Sesso", tooltipDifferenzeAnagrafica, allineatoAnagrafica);
			
			Integer annoNascita = getAnno(ana.getDataNascita());
			if(!annoNascita.equals(this.annoNascita)) {
				allineatoAnagrafica=false;
				this.tooltipDifferenzeAnagrafica.add("Anno di nascita: "+annoNascita);
			}	
		}
		return allineatoAnagrafica;
	}
	
	private void verificaAllineamento(String last, String old, String label, List<String> tooltip, boolean allineato) {
		if(!StringUtils.equals(last,old)){
			allineato = false;
			tooltip.add(label+": "+ (old==null ? "-assente-" : old));
		}
	}
	
	public boolean verificaAllineamentoResidenza(String viaResidenza, String jsonLuogoResidenza, String nazioneResidenza){
		allineatoResidenza = true;
		tooltipDifferenzeResidenza = new ArrayList<String>();
		if(csASoggetto!=null){
			CsAAnagrafica ana = csASoggetto.getCsAAnagrafica();
			
			String viaReplaced = viaResidenza!=null ? viaResidenza.replaceAll(",","") : viaResidenza;
		
			verificaAllineamento(this.cf,ana.getCf(), "Cod.fiscale", tooltipDifferenzeResidenza, allineatoResidenza);
			verificaAllineamento(this.comuneResidenza,jsonLuogoResidenza, "Comune Residenza", tooltipDifferenzeResidenza, allineatoResidenza);
			verificaAllineamento(this.nazioneResidenza, nazioneResidenza, "Nazione Residenza", tooltipDifferenzeResidenza, allineatoResidenza);
		
			if(!StringUtils.equalsIgnoreCase(this.viaResidenza,viaResidenza) && !StringUtils.equalsIgnoreCase(this.viaResidenza, viaReplaced)) {
				//Alcune vie nel mast sono scritte senza virgola
				allineatoResidenza = false;
				tooltipDifferenzeResidenza.add("Indirizzo residenza: "+ viaResidenza);
			}
		}
		return allineatoResidenza;
	}
	
	public void sincronizzaDatiAnagrafici(String comuneNascita){
		if(csASoggetto!=null){
			nome = csASoggetto.getCsAAnagrafica().getNome();
			cognome = csASoggetto.getCsAAnagrafica().getCognome();
			cf = csASoggetto.getCsAAnagrafica().getCf();
			annoNascita = getAnno(csASoggetto.getCsAAnagrafica().getDataNascita());
			cittadinanza = csASoggetto.getCsAAnagrafica().getCittadinanza();
			cittadinanza2 = csASoggetto.getCsAAnagrafica().getCittadinanza2();			
			sesso =  csASoggetto.getCsAAnagrafica().getSesso(); //SISO-1138-
			this.comuneNascita = comuneNascita;
			this.nazioneNascita = csASoggetto.getCsAAnagrafica().getStatoNascitaCod();
		}
		verificaDatiValidi(null, null, null, this.verificaAllineamentoAnagrafica(comuneNascita));
	}
	
	protected void verificaDatiValidi(String viaResidenza, String jsonLuogoResidenza, String nazioneResidenza, boolean allineatoAnagrafica){
		if((viaResidenza != null && (jsonLuogoResidenza != null || nazioneResidenza!=null)) || StringUtils.equalsIgnoreCase(viaResidenza, DataModelCostanti.SENZA_FISSA_DIMORA))
			this.verificaAllineamentoResidenza(viaResidenza, jsonLuogoResidenza, nazioneResidenza);
		
		if(this.csASoggetto!=null && (allineatoAnagrafica & this.allineatoResidenza))
			this.datiValidi=true;
	}
	
	public void sincronizzaResidenza(String viaResidenza, String jsonLuogoResidenza, String nazioneResidenza){
		this.comuneResidenza = jsonLuogoResidenza;
		this.viaResidenza = viaResidenza;
		this.nazioneResidenza = !"1".equals(nazioneResidenza) ? nazioneResidenza : null;
		this.verificaDatiValidi(viaResidenza, jsonLuogoResidenza, nazioneResidenza, allineatoAnagrafica);
	}
	
	public boolean isValorizzato() {
		boolean validato =
			   !StringUtils.isBlank(nome) && 
			   !StringUtils.isBlank(cognome) && 
			   !StringUtils.isBlank(cf) && !"0000000000000000".equals(cf) &&
			   !StringUtils.isBlank(cittadinanza) &&
			   (annoNascita!=null && annoNascita>0);
		
		if(this.riferimento){
			
			validato = validato && !StringUtils.isBlank(sesso);
			
			boolean statoValido = !StringUtils.isBlank(this.nazioneResidenza) || nazioneResidenzaNonDefinita;
			boolean comuneValido = !StringUtils.isBlank(this.comuneResidenza);
			boolean viaValida = !StringUtils.isBlank(viaResidenza);
			
			boolean statoNValido = !StringUtils.isBlank(this.nazioneNascita);
			boolean comuneNValido = !StringUtils.isBlank(this.comuneNascita);
			
			   //SISO-962
			 validato = validato && (statoNValido || comuneNValido) &&
					 (statoValido || (comuneValido && viaValida) || StringUtils.equalsIgnoreCase(DataModelCostanti.SENZA_FISSA_DIMORA, viaResidenza));
			   //SISO-962 Fine
		}
	    return validato;	
	}
	 
	protected Integer getAnno(Date data){
		if(data!=null){
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(data);
			return calendar1.get(Calendar.YEAR);
		}else return null;
	}
	
	
	public CsASoggettoLAZY getCsASoggetto() {
		return csASoggetto;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public boolean isRiferimento() {
		return riferimento;
	}

	public void setCsASoggetto(CsASoggettoLAZY csASoggetto) {
		this.csASoggetto = csASoggetto;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public void setRiferimento(boolean riferimento) {
		this.riferimento = riferimento;
	}

	public String getCittadinanza() {
		return cittadinanza;
	}

	
	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}

	public Integer getAnnoNascita() {
		return annoNascita;
	}

	public void setAnnoNascita(Integer annoNascita) {
		this.annoNascita = annoNascita;
	}

	
	
	public Boolean getDatiValidi() {
		return datiValidi;
	}

	public void setDatiValidi(Boolean datiValidi) {
		this.datiValidi = datiValidi;
	}
	
	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public boolean isDisabilita(){
		return this.datiValidi && this.isValorizzato();
	}

	public boolean isAllineatoResidenza() {
		if(csASoggetto==null)
			allineatoResidenza=true;
		return allineatoResidenza;
	}

	public boolean isAllineatoAnagrafica() {
		if(csASoggetto==null)
			allineatoAnagrafica=true;
		return allineatoAnagrafica;
	}

	//SISO-1021
	public boolean isNazioneResidenzaNonDefinita() {
		return nazioneResidenzaNonDefinita;
	}

	public void setNazioneResidenzaNonDefinita(boolean nazioneResidenzaNonDefinita) {
		this.nazioneResidenzaNonDefinita = nazioneResidenzaNonDefinita;
	}

	public String getCittadinanza2() {
		return cittadinanza2;
	}

	public void setCittadinanza2(String cittadinanza2) {
		this.cittadinanza2 = cittadinanza2;
	}

	public String getViaResidenza() {
		return viaResidenza;
	}

	public void setViaResidenza(String viaResidenza) {
		this.viaResidenza = viaResidenza;
	}

	public void setDatiValidi(boolean datiValidi) {
		this.datiValidi = datiValidi;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getComuneResidenza() {
		return comuneResidenza;
	}

	public void setComuneResidenza(String comuneResidenza) {
		this.comuneResidenza = comuneResidenza;
	}

	public String getNazioneResidenza() {
		return nazioneResidenza;
	}

	public void setNazioneResidenza(String nazioneResidenza) {
		this.nazioneResidenza = nazioneResidenza;
	}

	public String getComuneNascita() {
		return comuneNascita;
	}

	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}

	public String getNazioneNascita() {
		return nazioneNascita;
	}

	public void setNazioneNascita(String nazioneNascita) {
		this.nazioneNascita = nazioneNascita;
	}

	public List<String> getTooltipDifferenzeAnagrafica() {
		return tooltipDifferenzeAnagrafica;
	}

	public List<String> getTooltipDifferenzeResidenza() {
		return tooltipDifferenzeResidenza;
	}
}
