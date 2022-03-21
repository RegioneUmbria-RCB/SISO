package it.webred.ss.data.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the CS_O_ORGANIZZAZIONE database table.
 * 
 */
@Entity
@Table(name="CS_O_ORGANIZZAZIONE")
@NamedQuery(name="SsOOrganizzazione.findAll", query="SELECT c FROM SsOOrganizzazione c")
public class SsOOrganizzazione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private Boolean abilitato;

	private String email;

	private String nome;
	
	private String tooltip;
	
	/*CODICE FITTIZIO UNIVOCO (eventualmente coincidente con il CODICE CATASTALE), 
	 * valorizzato solo per le organizzazioni abilitate ad accedere al sistema */
	@Column(name="COD_ROUTING")
	private String codRouting; 
	
	/*CODICE CATASTALE REALE DEL COMUNE (VALORIZZATO SOLO PER COMUNI), non è univoco (es. Municipi del Comune di Roma)*/
	@Column(name="COD_CATASTALE")
	private String codCatastale;   
	
	// SISO-663 SM
	@Column(name = "FLAG_CAPOFILA")
	private Boolean flagCapofila;

	@Column(name = "FLAG_RICEVI_SCHEDE_ACCOGLIENZA")
	private Boolean flagRiceviSchedeAccoglienza;


	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getAbilitato() {
		return abilitato;
	}

	public void setAbilitato(Boolean abilitato) {
		this.abilitato = abilitato;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTooltip() {
		return this.tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public Boolean getFlagRiceviSchedeAccoglienza() {
		return flagRiceviSchedeAccoglienza;
	}

	public void setFlagRiceviSchedeAccoglienza(Boolean flagRiceviSchedeAccoglienza) {
		this.flagRiceviSchedeAccoglienza = flagRiceviSchedeAccoglienza;
	}

	public String getCodCatastale() {
		return codCatastale;
	}

	public void setCodCatastale(String codCatastale) {
		this.codCatastale = codCatastale;
	}

	public String getCodRouting() {
		return codRouting;
	}

	public void setCodRouting(String codRouting) {
		this.codRouting = codRouting;
	}

	public Boolean getFlagCapofila() {
		return flagCapofila;
	}

	public void setFlagCapofila(Boolean flagCapofila) {
		this.flagCapofila = flagCapofila;
	}

}