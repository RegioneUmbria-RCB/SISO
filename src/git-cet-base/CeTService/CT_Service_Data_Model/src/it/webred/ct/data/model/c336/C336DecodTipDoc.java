package it.webred.ct.data.model.c336;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * The persistent class for the S_C336_DECOD_TIP_DOC database table.
 * 
 */
@Entity
@Table(name="S_C336_DECOD_TIP_DOC")
public class C336DecodTipDoc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="COD_TIP_DOC")
	private String codTipDoc;

	@Column(name="DES_TIP_DOC")
	private String desTipDoc;

	public String getCodTipDoc() {
		return codTipDoc;
	}

	public void setCodTipDoc(String codTipDoc) {
		this.codTipDoc = codTipDoc;
	}

	public String getDesTipDoc() {
		return desTipDoc;
	}

	public void setDesTipDoc(String desTipDoc) {
		this.desTipDoc = desTipDoc;
	}
	
}
