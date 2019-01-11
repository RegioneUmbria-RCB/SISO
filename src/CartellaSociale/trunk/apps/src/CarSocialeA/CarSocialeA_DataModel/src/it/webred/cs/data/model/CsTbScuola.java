package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the CS_TB_SCUOLA database table.
 * 
 */
@Entity
@Table(name="CS_TB_SCUOLA")
@NamedQuery(name="CsTbScuola.findAll", query="SELECT c FROM CsTbScuola c")
public class CsTbScuola implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_TB_SCUOLA_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_TB_SCUOLA_ID_GENERATOR")
	private Long id;

	private String abilitato;

	private String descrizione;

	private String tooltip;
	
	@Column(name="ANNO_SCOLASTICO")
	private String annoScolastico;
	
	@Column(name="TIPO_SCUOLA_ID")
	private Long tipoScuolaId;
	
	private String codscuola;
	private String indirizzo;
	private String comune;
	private String cap;
	private String telefono;
	private String fax;
	private String email;
	private String pec;
	private String sitoweb;
	private String codistituto;

	
	public CsTbScuola() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAbilitato() {
		return this.abilitato;
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

	public String getAnnoScolastico() {
		return annoScolastico;
	}

	public void setAnnoScolastico(String annoScolastico) {
		this.annoScolastico = annoScolastico;
	}

	public Long getTipoScuolaId() {
		return tipoScuolaId;
	}

	public void setTipoScuolaId(Long tipoScuolaId) {
		this.tipoScuolaId = tipoScuolaId;
	}

	public String getCodscuola() {
		return codscuola;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public String getComune() {
		return comune;
	}

	public String getCap() {
		return cap;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getFax() {
		return fax;
	}

	public String getEmail() {
		return email;
	}

	public String getPec() {
		return pec;
	}

	public String getSitoweb() {
		return sitoweb;
	}

	public void setCodscuola(String codscuola) {
		this.codscuola = codscuola;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPec(String pec) {
		this.pec = pec;
	}

	public void setSitoweb(String sitoweb) {
		this.sitoweb = sitoweb;
	}

	public String getCodistituto() {
		return codistituto;
	}

	public void setCodistituto(String codistituto) {
		this.codistituto = codistituto;
	}
	
}