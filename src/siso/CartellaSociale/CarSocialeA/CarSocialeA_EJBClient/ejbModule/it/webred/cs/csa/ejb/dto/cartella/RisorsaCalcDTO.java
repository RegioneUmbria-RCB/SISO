package it.webred.cs.csa.ejb.dto.cartella;

import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.data.model.CsASoggettoLAZY;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class RisorsaCalcDTO implements Serializable {

	private Long anagraficaId;
	private String cognome;
	private String nome;
	private String cf;
	
	private String dateValidita;
	
	private CsASoggettoLAZY soggetto;
	private CsAComponente componente;
	
	private List<Long> famiglieSoggettoIds;
	
	public RisorsaCalcDTO(CsAAnagrafica a){
		this.anagraficaId = a.getId();
		this.cognome = a.getCognome();
		this.nome = a.getNome();
		this.cf = a.getCf();
	}
	
	public CsASoggettoLAZY getSoggetto() {
		return soggetto;
	}
	public void setSoggetto(CsASoggettoLAZY soggetto) {
		this.soggetto = soggetto;
	}
	public CsAComponente getComponente() {
		return componente;
	}
	public void setComponente(CsAComponente componente) {
		this.componente = componente;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCf() {
		return cf;
	}
	public void setCf(String cf) {
		this.cf = cf;
	}
	public Long getAnagraficaId() {
		return anagraficaId;
	}
	public void setAnagraficaId(Long anagraficaId) {
		this.anagraficaId = anagraficaId;
	}

	public List<Long> getFamiglieSoggettoIds() {
		return famiglieSoggettoIds;
	}

	public void setFamiglieSoggettoIds(List<Long> famiglieSoggettoIds) {
		this.famiglieSoggettoIds = famiglieSoggettoIds;
	}

	public String getDateValidita() {
		return dateValidita;
	}

	public void setDateValidita(String dateValidita) {
		this.dateValidita = dateValidita;
	}

}
