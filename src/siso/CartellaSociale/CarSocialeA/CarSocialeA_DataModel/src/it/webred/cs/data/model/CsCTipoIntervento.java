package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="CS_C_TIPO_INTERVENTO")
@NamedQuery(name="CsCTipoIntervento.findAll", query="SELECT c FROM CsCTipoIntervento c")
public class CsCTipoIntervento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_C_TIPO_INTERVENTO_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_C_TIPO_INTERVENTO_ID_GENERATOR")
	private Long id;

	private Boolean abilitato;

	private String descrizione;

	private String tooltip;
	
	private String tipo;
	
	@Column(name="CODICE_MEMO")
	private String codiceMemo;
	
	public CsCTipoIntervento() {
	}

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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCodiceMemo() {
		return codiceMemo;
	}

	public void setCodiceMemo(String codiceMemo) {
		this.codiceMemo = codiceMemo;
	}
}