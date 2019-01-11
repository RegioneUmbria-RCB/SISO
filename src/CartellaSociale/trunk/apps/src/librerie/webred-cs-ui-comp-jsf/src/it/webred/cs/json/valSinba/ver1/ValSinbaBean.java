package it.webred.cs.json.valSinba.ver1;

import it.webred.cs.json.dto.JsonBaseBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ValSinbaBean extends JsonBaseBean {
	private static final String JsnonName = "ValMultidimensionaleBean";
	
	@JsonIgnore private Date dataValutazione;
	@JsonIgnore private String descrizioneScheda;
	
	
	public ValSinbaBean()
	{
		

	}

	public ValSinbaBean(ValSinbaBean jsonOriginal) {

		try {
			if (jsonOriginal != null) {
				
			}
			else
			{
				
			}

		} catch (Exception ex) {
			logger.error(ex);
			throw new Error(ex);
		}
	}

	

	public static String getJsnonname() {
		return JsnonName;
	}

	public Date getDataValutazione() {
		return dataValutazione;
	}

	public void setDataValutazione(Date dataValutazione) {
		this.dataValutazione = dataValutazione;
	}

	public String getDescrizioneScheda() {
		return descrizioneScheda;
	}

	public void setDescrizioneScheda(String descrizioneScheda) {
		this.descrizioneScheda = descrizioneScheda;
	}

	// /**JsonBaseBean Methods*///
	@Override
	public List<String> checkObbligatorieta() {
		List<String> messagges = new LinkedList<String>();
		
	/*	if(    !isViveAltri()
			&& !isViveConiuge()
			&& !isViveFamiliari()
			&& !isViveFigli()
			&& !isViveSolo() ) messagges.add("Tab Rete familiare: rete familiare è un campo obbligatorio");
		
		if(getValFamRating().intValue() == 0) 
			messagges.add("Tab Rete familiare: Sintesi valutazione rete familiare è un campo obbligatorio");
		
		if(getRelAltriSogg() == null || "".equals(getRelAltriSogg()))
			messagges.add("Tab Rete sociale: Relazioni con altri soggetti è un campo obbligatorio" );
		
		if(getRelAltriSoggRetr() == null || "".equals(getRelAltriSoggRetr()))
			messagges.add("Tab Rete sociale: Relazioni con altri soggetti retribuiti è un campo obbligatorio" );
		*/
		return messagges;
	}
	
	
}
