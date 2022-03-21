package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the CS_REL_SOTTOCART_DOC_PROT database table.
 * 
 */
@Entity
@Table(name="CS_REL_SOTTOCART_DOC_PROT")
@NamedQuery(name="CsRelSottocartDocProt.findCodiceDocumentoGed", query="SELECT c.codiceDocumentoGed FROM CsRelSottocartDocProt c WHERE c.codRoutingOrgId = :codRoutingOrgId")
public class CsRelSottocartDocProt implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name="COD_ROUTING_ORG_ID")
	private String codRoutingOrgId;

	@Column(name="CODICE_DOCUMENTO_GED")
	private String codiceDocumentoGed;

	@Column(name="CODICE_UFFICIO_GED")
	private String codiceUfficioGed;

	@Column(name="DOC_FIS_GED")
	private String docFisGed;

	@Column(name="DOC_LOG_GED")
	private String docLogGed;

	private String note;

	public CsRelSottocartDocProt() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCodRoutingOrgId() {
		return this.codRoutingOrgId;
	}

	public void setCodRoutingOrgId(String codRoutingOrgId) {
		this.codRoutingOrgId = codRoutingOrgId;
	}

	public String getCodiceDocumentoGed() {
		return this.codiceDocumentoGed;
	}

	public void setCodiceDocumentoGed(String codiceDocumentoGed) {
		this.codiceDocumentoGed = codiceDocumentoGed;
	}

	public String getCodiceUfficioGed() {
		return this.codiceUfficioGed;
	}

	public void setCodiceUfficioGed(String codiceUfficioGed) {
		this.codiceUfficioGed = codiceUfficioGed;
	}

	public String getDocFisGed() {
		return this.docFisGed;
	}

	public void setDocFisGed(String docFisGed) {
		this.docFisGed = docFisGed;
	}

	public String getDocLogGed() {
		return this.docLogGed;
	}

	public void setDocLogGed(String docLogGed) {
		this.docLogGed = docLogGed;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}