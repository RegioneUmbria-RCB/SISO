package it.webred.cs.data.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="AR_BI_INVIANTE")
@NamedQuery(name="ArBiInviante.findAll", query="SELECT a FROM ArBiInviante a")
public class ArBiInviante implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	private String abilitato;

	@Column(name="EMAIL")
	private String email; 
	
	@Column(name="NOME_INVIANTE")
	private String nomeInviante;

	@Column(name="ZONA_NOME")
	private String zonaNome;

	@Column(name="ID_INVIANTE")
	private Long idInviante;

	@Column(name="ORA_SCHEMA_NAME")
	private String oraSchemaName;

	@Column(name="TIPO_INVIANTE")
	private String tipoInviante;

	@Column(name="CRITERI_ACCETTAZIONE")
	private String criteriAccettazione;
	
	@Column(name="COD_ROUTING")
	private String codRouting;


	public ArBiInviante() {
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getAbilitato() {
		return abilitato;
	}


	public void setAbilitato(String abilitato) {
		this.abilitato = abilitato;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getNomeInviante() {
		return nomeInviante;
	}


	public void setNomeInviante(String nomeInviante) {
		this.nomeInviante = nomeInviante;
	}


	public String getZonaNome() {
		return zonaNome;
	}


	public void setZonaNome(String zonaNome) {
		this.zonaNome = zonaNome;
	}


	public Long getIdInviante() {
		return idInviante;
	}


	public void setIdInviante(Long idInviante) {
		this.idInviante = idInviante;
	}


	public String getOraSchemaName() {
		return oraSchemaName;
	}


	public void setOraSchemaName(String oraSchemaName) {
		this.oraSchemaName = oraSchemaName;
	}

	public String getTipoInviante() {
		return tipoInviante;
	}


	public void setTipoInviante(String tipoInviante) {
		this.tipoInviante = tipoInviante;
	}


	public String getCriteriAccettazione() {
		return criteriAccettazione;
	}


	public void setCriteriAccettazione(String criteriAccettazione) {
		this.criteriAccettazione = criteriAccettazione;
	}


	public String getCodRouting() {
		return codRouting;
	}


	public void setCodRouting(String codRouting) {
		this.codRouting = codRouting;
	}

}
