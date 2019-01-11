package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the CS_D_DIARIO_DOC database table.
 * 
 */
@Entity
@Table(name="CS_D_DIARIO_DOC")
@NamedQuery(name="CsDDiarioDoc.findAll", query="SELECT c FROM CsDDiarioDoc c")
public class CsDDiarioDoc implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CsDDiarioDocPK id;
	
	//uni-directional many-to-one association to CsDDiario
	@ManyToOne
	@JoinColumn(name="DIARIO_ID", insertable=false, updatable=false)
	private CsDDiario csDDIario;

	//uni-directional many-to-one association to CsLoadDocumento
	@ManyToOne
	@JoinColumn(name="DOCUMENTO_ID", insertable=false, updatable=false)
	private CsLoadDocumento csLoadDocumento;

	public CsDDiarioDoc() {
	}

	public CsDDiarioDocPK getId() {
		return id;
	}

	public void setId(CsDDiarioDocPK id) {
		this.id = id;
	}

	public CsDDiario getCsDDIario() {
		return csDDIario;
	}

	public void setCsDDIario(CsDDiario csDDIario) {
		this.csDDIario = csDDIario;
	}

	public CsLoadDocumento getCsLoadDocumento() {
		return csLoadDocumento;
	}

	public void setCsLoadDocumento(CsLoadDocumento csLoadDocumento) {
		this.csLoadDocumento = csLoadDocumento;
	}

}