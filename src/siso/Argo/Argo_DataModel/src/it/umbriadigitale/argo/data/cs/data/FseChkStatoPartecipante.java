package it.umbriadigitale.argo.data.cs.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="FSE_CHK_STATO_PARTECIPANTE")
public class FseChkStatoPartecipante implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String statoPartecipante;
	private String descrizioneStatoPartecipante;
	
	@Column(name ="STATO_PARTECIPANTE")
	public String getStatoPartecipante() {
		return statoPartecipante;
	}
	public void setStatoPartecipante(String statoPartecipante) {
		this.statoPartecipante = statoPartecipante;
	}
	@Column(name ="DESCRIZIONE_STATO_PARTECIPANTE")
	public String getDescrizioneStatoPartecipante() {
		return descrizioneStatoPartecipante;
	}
	public void setDescrizioneStatoPartecipante(String descrizioneStatoPartecipante) {
		this.descrizioneStatoPartecipante = descrizioneStatoPartecipante;
	}
	
	

}
