/**
 * 
 */
package it.webred.cs.csa.web.manbean.report.dto.fascicolo;

import it.webred.cs.csa.utils.bean.report.dto.ReportPdfDTO;

/**
 * @author Lucia Davalli
 *
 */
public class ProblematichePdfDTO extends ReportPdfDTO{

	private String classe = EMPTY_VALUE; //Materiale o immateriale
	private String tipo = EMPTY_VALUE;
	private String descrizione = EMPTY_VALUE;
	private String rifAnalisi = EMPTY_VALUE; //item.csDRelazioneRif.csDDiario.dtAmministrativa 
	private String tipoAttivita = EMPTY_VALUE; //cc.attrs.iRelazioni.printTipoAttivitaProblematica(item)
	private String risolta = EMPTY_VALUE;
	private String verificata = EMPTY_VALUE;
	
	public String getClasse() {
		return classe;
	}
	public void setClasse(String classe) {
		this.classe = classe;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getRifAnalisi() {
		return rifAnalisi;
	}
	public void setRifAnalisi(String rifAnalisi) {
		this.rifAnalisi = rifAnalisi;
	}
	public String getTipoAttivita() {
		return tipoAttivita;
	}
	public void setTipoAttivita(String tipoAttivita) {
		this.tipoAttivita = tipoAttivita;
	}
	public String getRisolta() {
		return risolta;
	}
	public void setRisolta(String risolta) {
		this.risolta = risolta;
	}
	public String getVerificata() {
		return verificata;
	}
	public void setVerificata(String verificata) {
		this.verificata = verificata;
	}
	
}
