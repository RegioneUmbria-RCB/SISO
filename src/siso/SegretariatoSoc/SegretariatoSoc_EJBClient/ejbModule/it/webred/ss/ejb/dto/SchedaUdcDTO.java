package it.webred.ss.ejb.dto;

import it.webred.ss.data.model.SsDiario;
import it.webred.ss.data.model.SsInterventoEconomico;
import it.webred.ss.data.model.SsScheda;
import it.webred.ss.data.model.SsSchedaRiferimento;
import it.webred.ss.data.model.SsSchedaSegnalato;
import it.webred.ss.ejb.dto.report.DatiPrivacyPdfDTO;

import java.io.Serializable;
import java.util.List;

public class SchedaUdcDTO extends SchedaUdcBaseDTO  {

	private List<String> listaMotivazioni;
	private List<String> listaDiari;
	private List<SsInterventoEconomico> listaInterventiEconomici;
	private List<SsSchedaRiferimento> listaRiferimenti;
	private List<String> listaInterventi;
	private List<SsDiario> noteDiario;
	private DatiPrivacyPdfDTO datiPrivacyPDF;
	
	
	public List<String> getListaMotivazioni() {
		return listaMotivazioni;
	}
	public void setListaMotivazioni(List<String> listaMotivazioni) {
		this.listaMotivazioni = listaMotivazioni;
	}
	public List<String> getListaDiari() {
		return listaDiari;
	}
	public void setListaDiari(List<String> listaDiari) {
		this.listaDiari = listaDiari;
	}
	public List<SsInterventoEconomico> getListaInterventiEconomici() {
		return listaInterventiEconomici;
	}
	public void setListaInterventiEconomici(
			List<SsInterventoEconomico> listaInterventiEconomici) {
		this.listaInterventiEconomici = listaInterventiEconomici;
	}
	public List<SsSchedaRiferimento> getListaRiferimenti() {
		return listaRiferimenti;
	}
	public void setListaRiferimenti(List<SsSchedaRiferimento> listaRiferimenti) {
		this.listaRiferimenti = listaRiferimenti;
	}
	public List<String> getListaInterventi() {
		return listaInterventi;
	}
	public void setListaInterventi(List<String> listaInterventi) {
		this.listaInterventi = listaInterventi;
	}
	public List<SsDiario> getNoteDiario() {
		return noteDiario;
	}
	public void setNoteDiario(List<SsDiario> noteDiario) {
		this.noteDiario = noteDiario;
	}
	public DatiPrivacyPdfDTO getDatiPrivacyPDF() {
		return datiPrivacyPDF;
	}
	public void setDatiPrivacyPDF(DatiPrivacyPdfDTO datiPrivacyPDF) {
		this.datiPrivacyPDF = datiPrivacyPDF;
	}
}
