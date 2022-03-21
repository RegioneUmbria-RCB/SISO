package it.umbriadigitale.argo.data.cs.data;

// Generated 26-ott-2015 13.12.17 by Hibernate Tools 4.0.0

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AR_O_ORGANIZZAZIONE_EST")
public class ArOOrganizzazioneEst implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private long id;
	private String tooltip;
	private Boolean abilitato;
	private String email;
	private String nome;
	private String belfiore;
	
	@Column(name="PIVA_CF")
	private String pivaCf;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getBelfiore() {
		return belfiore;
	}
	public void setBelfiore(String belfiore) {
		this.belfiore = belfiore;
	}
	public String getPivaCf() {
		return pivaCf;
	}
	public void setPivaCf(String pivaCf) {
		this.pivaCf = pivaCf;
	}
	
}
