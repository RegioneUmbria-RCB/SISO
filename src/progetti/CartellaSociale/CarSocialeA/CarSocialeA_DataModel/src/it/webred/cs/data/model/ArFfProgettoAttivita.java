package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the AR_FF_PROGETTO_ATTIVITA database table.

 * 
 */

@Entity
@Table(name="AR_FF_PROGETTO_ATTIVITA")
public class ArFfProgettoAttivita implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	private long id;
	
	@Column(name="PROGETTO_ID")
	private Long progettoId;
	
	@Column(name="CODICE")
	private String codice;
	
	@Column(name="DESCRIZIONE")
	private String descrizione;
	
	@Column(name="TOOLTIP")
	private String tooltip;
	
	@Column(name="ABILITATO")
	private Boolean abilitato;
	
/*	//bi-directional many-to-one association to ArFfProgetto
	@ManyToOne
	@JoinColumn(name="PROGETTO_ID")
	private ArFfProgetto progetto;

*/
	
	public ArFfProgettoAttivita() {
	}
	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public Long getProgettoId() {
		return progettoId;
	}


	public void setProgettoId(Long progettoId) {
		this.progettoId = progettoId;
	}


	public String getCodice() {
		return codice;
	}


	public void setCodice(String codice) {
		this.codice = codice;
	}


	public String getDescrizione() {
		return descrizione;
	}


	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}


	public String getTooltip() {
		return tooltip;
	}


	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}


	public Boolean getAbilitato() {
		return abilitato;
	}


	public void setAbilitato(Boolean abilitato) {
		this.abilitato = abilitato;
	}


/*	public ArFfProgetto getProgetto() {
		return progetto;
	}


	public void setProgetto(ArFfProgetto progetto) {
		this.progetto = progetto;
	}
*/

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
