package it.webred.cs.json.barthel.ver1;

import it.webred.cs.json.dto.JsonBaseBean;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonBarthelPatologiePsichiatricheDataBean extends JsonBaseBean{

	protected boolean deterioramentoCognitivo;
	protected boolean ansia; 	
	protected boolean depressione; 	
	protected boolean deliri;
	protected boolean allucinazioni; 	
	protected boolean agitazionePsicoMotoriaDiurna; 	
	protected boolean agitazionePsicoMotoriaNotturna; 	
	protected boolean deambulazioneIncessante; 	
	protected boolean affacendamentoAfinalistico; 	
	protected boolean inversioneDelRitmoSonnoVeglia; 	
	protected boolean aggressivita; 	
	protected boolean urlaLamentazioniVocalizzazioniRipetitive;
	protected Date dataDiValutazione;

	@JsonIgnore protected String deterioramentoCognitivoLabel;
	@JsonIgnore protected String ansiaLabel;
	@JsonIgnore protected String depressioneLabel; 	
	@JsonIgnore protected String deliriLabel;
	@JsonIgnore protected String allucinazioniLabel;
	@JsonIgnore protected String agitazionePsicoMotoriaDiurnaLabel;
	@JsonIgnore protected String agitazionePsicoMotoriaNotturnaLabel; 	
	@JsonIgnore protected String deambulazioneIncessanteLabel;
	@JsonIgnore protected String affacendamentoAfinalisticoLabel;
	@JsonIgnore protected String inversioneDelRitmoSonnoVegliaLabel;
	@JsonIgnore protected String aggressivitaLabel;
	@JsonIgnore protected String urlaLamentazioniVocalizzazioniRipetitiveLabel;
	@JsonIgnore protected String dataDiValutazioneLabel;

	public JsonBarthelPatologiePsichiatricheDataBean(){
		deterioramentoCognitivo = false;
		ansia = false;
		depressione = false; 	
		deliri = false;
		allucinazioni = false;
		agitazionePsicoMotoriaDiurna = false;
		agitazionePsicoMotoriaNotturna = false;
		deambulazioneIncessante = false;
		affacendamentoAfinalistico = false;
		inversioneDelRitmoSonnoVeglia = false;
		aggressivita = false;
		urlaLamentazioniVocalizzazioniRipetitive = false;
		dataDiValutazione = new Date();
		
		deterioramentoCognitivoLabel = "Deterioramento cognitivo";
		ansiaLabel = "Ansia";
		depressioneLabel = "Depressione"; 	
		deliriLabel = "Deliri";
		allucinazioniLabel = "Allucinazioni";
		agitazionePsicoMotoriaDiurnaLabel = "Agitazione psico-motoria diurna";
		agitazionePsicoMotoriaNotturnaLabel = "Agitazione psico-motoria notturna";
		deambulazioneIncessanteLabel = "Deambulazione incessante";
		affacendamentoAfinalisticoLabel = "Affacendamento afinalistico";
		inversioneDelRitmoSonnoVegliaLabel = "Inversione del ritmo sonno - veglia";
		aggressivitaLabel = "Aggressività";
		urlaLamentazioniVocalizzazioniRipetitiveLabel = "Urla, lamentazioni, vocalizzazioni ripetitive";
		dataDiValutazioneLabel = "Data di Valutazione";
	}
	
	public List<String> checkObbligatorieta() {
		
		List<String> messagges = new LinkedList<String>();
		if( dataDiValutazione == null ) messagges.add("- Data di valutazione" );
	
		return messagges;
	}

	public boolean isDeterioramentoCognitivo() {
		return deterioramentoCognitivo;
	}
	public void setDeterioramentoCognitivo(boolean deterioramentoCognitivo) {
		this.deterioramentoCognitivo = deterioramentoCognitivo;
	}
	public boolean isAnsia() {
		return ansia;
	}
	public void setAnsia(boolean ansia) {
		this.ansia = ansia;
	}
	public boolean isDepressione() {
		return depressione;
	}
	public void setDepressione(boolean depressione) {
		this.depressione = depressione;
	}
	public boolean isDeliri() {
		return deliri;
	}
	public void setDeliri(boolean deliri) {
		this.deliri = deliri;
	}
	public boolean isAllucinazioni() {
		return allucinazioni;
	}
	public void setAllucinazioni(boolean allucinazioni) {
		this.allucinazioni = allucinazioni;
	}
	public boolean isAgitazionePsicoMotoriaDiurna() {
		return agitazionePsicoMotoriaDiurna;
	}
	public void setAgitazionePsicoMotoriaDiurna(boolean agitazionePsicoMotoriaDiurna) {
		this.agitazionePsicoMotoriaDiurna = agitazionePsicoMotoriaDiurna;
	}
	public boolean isAgitazionePsicoMotoriaNotturna() {
		return agitazionePsicoMotoriaNotturna;
	}
	public void setAgitazionePsicoMotoriaNotturna(
			boolean agitazionePsicoMotoriaNotturna) {
		this.agitazionePsicoMotoriaNotturna = agitazionePsicoMotoriaNotturna;
	}
	public boolean isDeambulazioneIncessante() {
		return deambulazioneIncessante;
	}
	public void setDeambulazioneIncessante(boolean deambulazioneIncessante) {
		this.deambulazioneIncessante = deambulazioneIncessante;
	}
	public boolean isAffacendamentoAfinalistico() {
		return affacendamentoAfinalistico;
	}
	public void setAffacendamentoAfinalistico(boolean affacendamentoAfinalistico) {
		this.affacendamentoAfinalistico = affacendamentoAfinalistico;
	}
	public boolean isInversioneDelRitmoSonnoVeglia() {
		return inversioneDelRitmoSonnoVeglia;
	}
	public void setInversioneDelRitmoSonnoVeglia(
			boolean inversioneDelRitmoSonnoVeglia) {
		this.inversioneDelRitmoSonnoVeglia = inversioneDelRitmoSonnoVeglia;
	}
	public boolean isAggressivita() {
		return aggressivita;
	}
	public void setAggressivita(boolean aggressivita) {
		this.aggressivita = aggressivita;
	}
	public boolean isUrlaLamentazioniVocalizzazioniRipetitive() {
		return urlaLamentazioniVocalizzazioniRipetitive;
	}
	public void setUrlaLamentazioniVocalizzazioniRipetitive(
			boolean urlaLamentazioniVocalizzazioniRipetitive) {
		this.urlaLamentazioniVocalizzazioniRipetitive = urlaLamentazioniVocalizzazioniRipetitive;
	}
	public String getDeterioramentoCognitivoLabel() {
		return deterioramentoCognitivoLabel;
	}
	
	public Date getDataDiValutazione() {
		return dataDiValutazione;
	}

	public void setDataDiValutazione(Date dataDiValutazione) {
		this.dataDiValutazione = dataDiValutazione;
	}

	public String getAnsiaLabel() {
		return ansiaLabel;
	}
	public String getDepressioneLabel() {
		return depressioneLabel;
	}
	public String getDeliriLabel() {
		return deliriLabel;
	}
	public String getAllucinazioniLabel() {
		return allucinazioniLabel;
	}
	public String getAgitazionePsicoMotoriaDiurnaLabel() {
		return agitazionePsicoMotoriaDiurnaLabel;
	}
	public String getAgitazionePsicoMotoriaNotturnaLabel() {
		return agitazionePsicoMotoriaNotturnaLabel;
	}
	public String getDeambulazioneIncessanteLabel() {
		return deambulazioneIncessanteLabel;
	}
	public String getAffacendamentoAfinalisticoLabel() {
		return affacendamentoAfinalisticoLabel;
	}
	public String getInversioneDelRitmoSonnoVegliaLabel() {
		return inversioneDelRitmoSonnoVegliaLabel;
	}
	public String getAggressivitaLabel() {
		return aggressivitaLabel;
	}
	public String getUrlaLamentazioniVocalizzazioniRipetitiveLabel() {
		return urlaLamentazioniVocalizzazioniRipetitiveLabel;
	}
	public String getDataDiValutazioneLabel() {
		return dataDiValutazioneLabel;
	}

	public String fillReport(){
		String s = "";
		
		if(this.deterioramentoCognitivo)
			s+=this.deterioramentoCognitivoLabel+"<br/>";
		if(this.ansia)
			s+=this.ansiaLabel+"<br/>";
		if(this.depressione)
			s+=this.depressioneLabel+"<br/>";
		if(this.deliri)
			s+=this.deliriLabel+"<br/>";
		if(this.allucinazioni)
			s+=this.allucinazioniLabel+"<br/>";
		if(this.agitazionePsicoMotoriaDiurna)
			s+=this.agitazionePsicoMotoriaDiurnaLabel+"<br/>";
		if(this.agitazionePsicoMotoriaNotturna)
			s+=this.agitazionePsicoMotoriaNotturnaLabel+"<br/>";
		if(this.deambulazioneIncessante)
			s+=this.deambulazioneIncessanteLabel+"<br/>";
		if(this.affacendamentoAfinalistico)
			s+=this.affacendamentoAfinalisticoLabel+"<br/>";
		if(this.inversioneDelRitmoSonnoVeglia)
			s+=this.inversioneDelRitmoSonnoVegliaLabel+"<br/>";
		if(this.aggressivita)
			s+=this.aggressivitaLabel+"<br/>";
		if(this.urlaLamentazioniVocalizzazioniRipetitive)
			s+=this.urlaLamentazioniVocalizzazioniRipetitiveLabel+"<br/>";
			
		return s;
	}
}
