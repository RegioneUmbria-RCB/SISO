package it.webred.ct.data.model.docfa;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class DocfaDatiCensuariPK implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Temporal( TemporalType.DATE)
	private Date fornitura;

	@Column(name="IDENTIFICATIVO_IMMOBILE")
	private String identificativoImmobile;
	
	@Column(name="PROTOCOLLO_REGISTRAZIONE")
	private String protocolloRegistrazione;
	
	@Column(name="DATA_REGISTRAZIONE")
	private String dataRegistrazione;
	
	public Date getFornitura() {
		return fornitura;
	}

	public void setFornitura(Date fornitura) {
		this.fornitura = fornitura;
	}

	public String getIdentificativoImmobile() {
		return identificativoImmobile;
	}

	public void setIdentificativoImmobile(String identificativoImmobile) {
		this.identificativoImmobile = identificativoImmobile;
	}

	public String getProtocolloRegistrazione() {
		return protocolloRegistrazione;
	}

	public void setProtocolloRegistrazione(String protocolloRegistrazione) {
		this.protocolloRegistrazione = protocolloRegistrazione;
	}

	public String getDataRegistrazione() {
		return dataRegistrazione;
	}

	public void setDataRegistrazione(String dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if(obj instanceof DocfaDatiCensuariPK){
			
		DocfaDatiCensuariPK pk = (DocfaDatiCensuariPK) obj;
		return fornitura.compareTo(pk.getFornitura()) ==0
				&& identificativoImmobile.equals(pk.getIdentificativoImmobile())
				&& protocolloRegistrazione.equals(pk.getProtocolloRegistrazione())
				&& dataRegistrazione.equals(pk.getDataRegistrazione());
		}
		return false;
	}
	
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.fornitura.hashCode();
		hash = hash * prime + this.identificativoImmobile.hashCode();
		hash = hash * prime + this.protocolloRegistrazione.hashCode();
		hash = hash * prime + this.dataRegistrazione.hashCode();
		
		return hash;
    }

}
