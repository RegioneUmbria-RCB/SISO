package it.webred.ss.ejb.dto;

import java.util.ArrayList;
import java.util.List;

import it.webred.ss.data.model.SsInterventoEconomico;
import it.webred.ss.data.model.SsSchedaRiferimento;
import it.webred.ss.ejb.dto.report.DatiPrivacyPdfDTO;

public class SchedaUdcDTO extends SchedaUdcBaseDTO  {

	private List<String> listaMotivazioni;
	
	private List<SsInterventoEconomico> listaInterventiEconomici;
	private List<SsSchedaRiferimento> listaRiferimenti;
	private List<String> listaInterventi;
	private List<NotaDTO> noteDiario;
	private DatiPrivacyPdfDTO datiPrivacyPDF;
	
	
	public List<String> getListaMotivazioni() {
		return listaMotivazioni;
	}
	public void setListaMotivazioni(List<String> listaMotivazioni) {
		this.listaMotivazioni = listaMotivazioni;
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
	public List<NotaDTO> getNoteDiario() {
		return noteDiario;
	}
	public void setNoteDiario(List<NotaDTO> noteDiario) {
		this.noteDiario = noteDiario;
	}
	public DatiPrivacyPdfDTO getDatiPrivacyPDF() {
		return datiPrivacyPDF;
	}
	public void setDatiPrivacyPDF(DatiPrivacyPdfDTO datiPrivacyPDF) {
		this.datiPrivacyPDF = datiPrivacyPDF;
	}
	public List<String> getListaDiari(){
		List<String> diari = new ArrayList<String>();
		for(NotaDTO d : noteDiario)
			diari.add(d.getNota());
		return diari;
	}
}
