package it.webred.cs.data.model.affido;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="CS_PAI_AFFIDO_SOGGETTO")
public class CsPaiAffidoSoggetto {

	@Id
	@Column(name="ID")
	@SequenceGenerator(name="CS_PAI_AFFIDO_ID_GENERATOR", sequenceName="SQ_PAI_AFFIDO",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_PAI_AFFIDO_ID_GENERATOR")
	private Long id;
	
	@Column(name="COGNOME")
	private String cognome;
	
	@Column(name="NOME")
	private String nome;
	
	@Column(name="CODICE_RUOLO")
	private String codiceRuolo;
	
	@Column(name="AFFIDO_ID", insertable=false, updatable=false)
	private Long affidoId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodiceRuolo() {
		return codiceRuolo;
	}

	public void setCodiceRuolo(String codiceRuolo) {
		this.codiceRuolo = codiceRuolo;
	}

	public Long getAffidoId() {
		return affidoId;
	}

	public void setAffidoId(Long affidoId) {
		this.affidoId = affidoId;
	}
	
}
