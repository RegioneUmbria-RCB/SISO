package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="CS_TB_SCHEDA_MULTIDIM")
@NamedQuery(name="CsTbSchedaMultidim.findAll", query="SELECT c FROM CsTbSchedaMultidim c")
public class CsTbSchedaMultidim implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String tipo;
	
	private String codice;
	
	private Boolean abilitato;

	private String descrizione;

	private String tooltip;
	
	@Column(name="N_ORD")
	private Integer nOrd;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getTipo() {
		return tipo;
	}

	public String getCodice() {
		return codice;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public Boolean getAbilitato() {
		return abilitato;
	}

	public void setAbilitato(Boolean abilitato) {
		this.abilitato = abilitato;
	}

	public Integer getnOrd() {
		return nOrd;
	}

	public void setnOrd(Integer nOrd) {
		this.nOrd = nOrd;
	}

}