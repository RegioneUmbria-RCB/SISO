package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the CS_C_TIPO_INTERVENTO_CUSTOM database table.
 * 
 */
@Entity
@Table(name="CS_C_TIPO_INTERVENTO_CUSTOM")
@NamedQuery(name="CsCTipoInterventoCustom.findAll", query="SELECT ic FROM CsCTipoInterventoCustom ic order by ic.descrizione")
public class CsCTipoInterventoCustom implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_C_TIPO_INTERVENTO_CUSTOM_ID_GENERATOR", sequenceName="SQ_ID_ARGO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_C_TIPO_INTERVENTO_CUSTOM_ID_GENERATOR")
	private Long id;

	private String abilitato;

	private String descrizione;

	private String tooltip;

	//SISO-1110 Inizio
	
	private Long codice_1;
	private Long codice_2;
	private Long codice_3;
	private Long codice_4;
	
	@Column(name="CODICE_MEMO")
	private String codiceMemo;

	
	
	public String getCodiceMemo() {
		return codiceMemo;
	}

	public void setCodiceMemo(String codiceMemo) {
		this.codiceMemo = codiceMemo;
	}

	public Long getCodice_1() {
		return codice_1;
	}

	public void setCodice_1(Long codice_1) {
		this.codice_1 = codice_1;
	}

	public Long getCodice_2() {
		return codice_2;
	}

	public void setCodice_2(Long codice_2) {
		this.codice_2 = codice_2;
	}

	public Long getCodice_3() {
		return codice_3;
	}

	public void setCodice_3(Long codice_3) {
		this.codice_3 = codice_3;
	}

	public Long getCodice_4() {
		return codice_4;
	}

	public void setCodice_4(Long codice_4) {
		this.codice_4 = codice_4;
	}

	//SISO-1110 Fine
	
	
	public CsCTipoInterventoCustom() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAbilitato() {
		return abilitato;
	}

	public void setAbilitato(String abilitato) {
		this.abilitato = abilitato;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getTooltip() {
		return this.tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	

}