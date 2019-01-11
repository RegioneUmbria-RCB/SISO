package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;
import java.util.Set;


/**
 * The persistent class for the CS_D_CLOB database table.
 * 
 */
@Entity
@Table(name="CS_D_CLOB")
@NamedQuery(name="CsDClob.findAll", query="SELECT c FROM CsDClob c")
public class CsDClob implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_D_CLOB_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_D_CLOB_ID_GENERATOR")
	private long id;

	@Lob
	@Column(name="DATI_CLOB")
	private String datiClob;

	//bi-directional many-to-one association to CsDDiario
	@OneToMany(mappedBy="csDClob" , fetch = FetchType.LAZY)
	private Set<CsDDiario> csDDiarios;

	public CsDClob() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDatiClob() {
		return this.datiClob;
	}

	public void setDatiClob(String datiClob) {
		this.datiClob = datiClob;
	}

	public Set<CsDDiario> getCsDDiarios() {
		return this.csDDiarios;
	}

	public void setCsDDiarios(Set<CsDDiario> csDDiarios) {
		this.csDDiarios = csDDiarios;
	}

	public CsDDiario addCsDDiario(CsDDiario csDDiario) {
		getCsDDiarios().add(csDDiario);
		csDDiario.setCsDClob(this);

		return csDDiario;
	}

	public CsDDiario removeCsDDiario(CsDDiario csDDiario) {
		getCsDDiarios().remove(csDDiario);
		csDDiario.setCsDClob(null);

		return csDDiario;
	}

}