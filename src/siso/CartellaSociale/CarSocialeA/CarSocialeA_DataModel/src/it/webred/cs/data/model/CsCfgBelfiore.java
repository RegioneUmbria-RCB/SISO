package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the CS_CFG_ATTR database table.
 * 
 */
@Entity
@Table(name="CS_CFG_BELFIORE")
@NamedQuery(name="CsCfgBelfiore.findByRipTerritorialeId", query="SELECT c FROM CsCfgBelfiore c where c.codRipartizioneTerritoriale = :codRipartizioneTerritoriale")
public class CsCfgBelfiore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_CFG_BELFIORE_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_CFG_BELFIORE_ID_GENERATOR")
	private long id;

	@Column(name="COD_BELFIORE")
	private String codBelfiore;

	@Column(name="COD_RIPARTIZIONE_TERRITORIALE")
	private String codRipartizioneTerritoriale;

	@Column(name="DESCRIZIONE")
	private String descrizione;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCodBelfiore() {
		return codBelfiore;
	}

	public void setCodBelfiore(String codBelfiore) {
		this.codBelfiore = codBelfiore;
	}

	public String getCodRipartizioneTerritoriale() {
		return codRipartizioneTerritoriale;
	}

	public void setCodRipartizioneTerritoriale(String codRipartizioneTerritoriale) {
		this.codRipartizioneTerritoriale = codRipartizioneTerritoriale;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	 


}