package it.webred.ct.data.access.basic.segnalazionequalificata.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.model.SelectItem;
import it.webred.ct.data.model.segnalazionequalificata.*;
import it.webred.ct.support.datarouter.CeTBaseObject;

public class PraticaSegnalazioneDTO extends CeTBaseObject{
	private static final long serialVersionUID = 1L;
	
	private SOfPratica pratica;
	private List<String> fonti;
	private List<String> ambitiAccertamento;
	private List<SOfPratAllegato> allegati;
	private String descAccComuNasc;
	private String accStatoDecoded;
	private String accFinalitaDecoded;
	private String accAzioneDecoded;
	private String adeStatoDecoded;

	public PraticaSegnalazioneDTO(){
		setFonti(new ArrayList<String>());
		setAmbitiAccertamento(new ArrayList<String>());
		allegati = new ArrayList<SOfPratAllegato>();
	}

	public SOfPratica getPratica() {
		return pratica;
	}

	public void setPratica(SOfPratica pratica) {
		this.pratica = pratica;
	}

	public List<SOfPratAllegato> getAllegati() {
		return allegati;
	}

	public void setAllegati(List<SOfPratAllegato> allegati) {
		this.allegati = allegati;
	}

	public void setDescAccComuNasc(String descAccComuNasc) {
		this.descAccComuNasc = descAccComuNasc;
	}

	public String getDescAccComuNasc() {
		return descAccComuNasc;
	}

	public String getAccStatoDecoded(){
		if(pratica!=null)
			return getAccStatoDecoded(pratica.getAccStato());
		else return "";
	}
	
	private String getAccStatoDecoded(String stato){
		accStatoDecoded = "";
		if("0".equalsIgnoreCase(stato))
			accStatoDecoded = "Istruttoria Comunale in corso";
		else if("1".equalsIgnoreCase(stato))
			accStatoDecoded = "Segnalazione in corso presso Agenzia delle Entrate";
		else if ("2".equalsIgnoreCase(stato))
			accStatoDecoded = "Iter concluso";
		return accStatoDecoded;
	}
	
	public String getAccFinalitaDecoded(){
		if(pratica!=null)
			return getAccFinalitaDecoded(pratica.getAccFinalita());
		else return "";
	}
	
	private String getAccFinalitaDecoded(String f){
		accFinalitaDecoded = "";
		if("TFL".equalsIgnoreCase(f))
			accFinalitaDecoded = "Tributi fiscali locali";
		else if("TFE".equalsIgnoreCase(f))
			accFinalitaDecoded = "Tributi fiscali erariali";
		return accFinalitaDecoded;
	}

	public void setFonti(List<String> fonti) {
		this.fonti = fonti;
	}

	public List<String> getFonti() {
		return fonti;
	}

	public void setAmbitiAccertamento(List<String> ambitiAccertamento) {
		this.ambitiAccertamento = ambitiAccertamento;
	}

	public List<String> getAmbitiAccertamento() {
		return ambitiAccertamento;
	}

	public String getAccAzioneDecoded() {
		if(pratica!=null)
			return getAccAzioneDecoded(pratica.getAccAzione());
		return accAzioneDecoded;
	}
	
	private String getAccAzioneDecoded(String a){
		accAzioneDecoded="";
		if ("DIRETTA AC".equalsIgnoreCase(a))
			accAzioneDecoded = "Azione diretta amministrazione comunale";
		else if("SEGNALAZIONE ADE".equalsIgnoreCase(a))
			accAzioneDecoded = "Segnalazione qualificata presso Agenzia delle Entrate";
		else if("NESSUNA".equalsIgnoreCase(a))
			accAzioneDecoded = "Nessuna Azione";
		return accAzioneDecoded;
	}

	private String getAdeStatoDecoded(String s) {
		adeStatoDecoded = "";
		if("ATTESA DI IMMISSIONE".equalsIgnoreCase(s))
				adeStatoDecoded = "In Attesa di Immissione";
		else if("IMMESSA".equalsIgnoreCase(s))
			adeStatoDecoded = "Immessa";
		else if ("INVIATA".equalsIgnoreCase(s))
			adeStatoDecoded = "Inviata";
		else if("PRESA IN CARICO".equalsIgnoreCase(s))
			adeStatoDecoded = "Presa in carico";
		else if("CONCLUSA".equalsIgnoreCase(s))
			adeStatoDecoded = "Conclusa";
		return adeStatoDecoded;
	}

	public String getAdeStatoDecoded() {
		if(pratica!=null)
			return getAdeStatoDecoded(pratica.getAdeStato());
		return adeStatoDecoded;
	}
	


}
