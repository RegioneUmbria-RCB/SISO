package it.webred.ss.data.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the SS_SCHEDA_MOTIVAZIONE database table.
 * 
 */
@Entity
@Table(name="SS_SCHEDA_MOTIVAZIONE")
@NamedQuery(name="SsSchedaMotivazione.findAll", query="SELECT c FROM SsSchedaMotivazione c")
public class SsSchedaMotivazione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SS_SCHEDA_MOTIVAZIONE_ID_GENERATOR", sequenceName="SQ_SS_SCHEDA_MOTIVAZIONE", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SS_SCHEDA_MOTIVAZIONE_ID_GENERATOR")
	private Long id;
	
	private String altro;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAltro() {
		return altro;
	}

	public void setAltro(String altro) {
		this.altro = altro;
	}

}