package it.webred.cs.csa.ejb.dto;

import it.webred.cs.data.model.CsDRelazione;
import it.webred.cs.data.model.CsRelRelazioneProbl;
import it.webred.cs.data.model.CsTbMacroAttivita;
import it.webred.cs.data.model.CsTbMicroAttivita;

import java.util.Date;


import com.fasterxml.jackson.annotation.JsonFormat;

public class CsRelRelazioneProblDTO extends CsRelRelazioneProbl{

	
	private String classe="";
	private String tipo="";
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dataRifAnalisi=null;
	
	private String tipoAttivita="";

	public String getClasse() {
		if(this.getCsTbProbl()!=null )
		{
			if(this.getCsTbProbl().getFlagMatImm().equalsIgnoreCase("M"))
			{
				classe="Materiale";
			}
			else if (this.getCsTbProbl().getFlagMatImm().equalsIgnoreCase("I"))
			{
				classe="Immateriale";
				
			}
		}
		return classe;
	}

	public String getTipo() {
		if(this.getCsTbProbl()!=null )
		{
			tipo=this.getCsTbProbl().getTipo();
		}
		return tipo;
	}

	public Date getDataRifAnalisi() {
		if(this.getCsDRelazioneRif()!=null && this.getCsDRelazioneRif().getCsDDiario()!=null)
		{
			dataRifAnalisi=this.getCsDRelazioneRif().getCsDDiario().getDtAmministrativa();
		}
		return dataRifAnalisi;
	}

	public String getTipoAttivita() {

		tipoAttivita=printTipoAttivitaProblematica();
		
		return tipoAttivita;
	}
	
	private String printTipoAttivitaProblematica() {
		// final String RELAZIONE="Relazione";
		// final String COLLOQUIO="Colloquio";
		CsRelRelazioneProbl item=this;

		if (item != null) {
			CsDRelazione relazioneToTest = item.getCsDRelazione();

			CsDRelazione relazioneRif = item.getCsDRelazioneRif();
			if (relazioneRif != null) {
				relazioneToTest = relazioneRif;
			}

			if (relazioneToTest != null) {
				CsTbMicroAttivita micro = relazioneToTest.getMicroAttivita();
				if (micro != null) {
					CsTbMacroAttivita macro = micro.getMacroAttivita();
					String macroDescr = (macro!=null) ? macro.getDescrizione(): "";
					return macroDescr +" "+ micro.getDescrizione();
				}
			}
		}
		
		return "--";		
		
	}

	public void setByModel(CsRelRelazioneProbl relProbl) {
		this.setCsDRelazione(relProbl.getCsDRelazione());
		this.setCsDRelazioneRif(relProbl.getCsDRelazioneRif());
		this.setCsTbProbl(relProbl.getCsTbProbl());
		this.setFlagRisolta(relProbl.getFlagRisolta());
		this.setFlagVerificata(relProbl.getFlagVerificata());
		this.setId(relProbl.getId());
		
	}
	
	
	
}
