package eu.smartpeg.rilevazionepresenze.data.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the AR_RP_TB_TIPO_DOCUMENTO database table.
 * 
 */
@Entity
@Table(name="AR_RP_TB_TIPO_DOCUMENTO")
@NamedQuery(name="TipoDocumento.findAll", query="SELECT t FROM TipoDocumento t")
public class TipoDocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	private String code;

	private String descrizione;

	//bi-directional many-to-one association to ArRpDocumentiAnag
//	@OneToMany(mappedBy="tipoDocumento")
//	private List<DocumentiAnag> documentiAnags;
	
	
	//bi-directional many-to-one association to ArRpDocumentiAnag
//		@OneToMany(mappedBy="arRpTbTipoDocumento")
//		private List<ArRpDocumentiAnag> arRpDocumentiAnags;

		
	public TipoDocumento() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

//	public List<DocumentiAnag> getDocumentiAnags() {
//		return this.documentiAnags;
//	}
//
//	public void setDocumentiAnags(List<DocumentiAnag> documentiAnags) {
//		this.documentiAnags = documentiAnags;
//	}
//
//	public DocumentiAnag addDocumentiAnag(DocumentiAnag documentiAnag) {
//		getDocumentiAnags().add(documentiAnag);
//		documentiAnag.setTipoDocumento(this);
//
//		return documentiAnag;
//	}
//
//	public DocumentiAnag removeDocumentiAnag(DocumentiAnag documentiAnag) {
//		getDocumentiAnags().remove(documentiAnag);
//		documentiAnag.setTipoDocumento(null);
//
//		return documentiAnag;
//	}

}