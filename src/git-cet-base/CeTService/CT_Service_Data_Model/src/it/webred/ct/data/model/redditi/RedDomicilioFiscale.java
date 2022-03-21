package it.webred.ct.data.model.redditi;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the RED_DOMICILIO_FISCALE database table.
 * 
 */
@Entity
@Table(name="RED_DOMICILIO_FISCALE")
public class RedDomicilioFiscale implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RedDomicilioFiscalePK id;
	
	@Column(name="ANNO_IMPOSTA")
	private String annoImposta;

	private String cap;

	@Column(name="CODICE_CAT_DOM_FISCALE_ATTUALE")
	private String codiceCatDomFiscaleAttuale;

	@Column(name="CODICE_CAT_DOM_FISCALE_DIC")
	private String codiceCatDomFiscaleDic;

	@Column(name="INDIRIZZO_ATTUALE")
	private String indirizzoAttuale;
	
	@Transient
	private String  desComuneDomFiscaleAttuale;

	@Transient
	private String  desComuneDomFiscaleDic;
	
    public RedDomicilioFiscale() {
    }

	public String getAnnoImposta() {
		return this.annoImposta;
	}

	public void setAnnoImposta(String annoImposta) {
		this.annoImposta = annoImposta;
	}

	public String getCap() {
		return this.cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getCodiceCatDomFiscaleAttuale() {
		return this.codiceCatDomFiscaleAttuale;
	}

	public void setCodiceCatDomFiscaleAttuale(String codiceCatDomFiscaleAttuale) {
		this.codiceCatDomFiscaleAttuale = codiceCatDomFiscaleAttuale;
	}

	public String getCodiceCatDomFiscaleDic() {
		return this.codiceCatDomFiscaleDic;
	}

	public void setCodiceCatDomFiscaleDic(String codiceCatDomFiscaleDic) {
		this.codiceCatDomFiscaleDic = codiceCatDomFiscaleDic;
	}

	
	public String getIndirizzoAttuale() {
		return this.indirizzoAttuale;
	}

	public void setIndirizzoAttuale(String indirizzoAttuale) {
		this.indirizzoAttuale = indirizzoAttuale;
	}

	public RedDomicilioFiscalePK getId() {
		return id;
	}

	public void setId(RedDomicilioFiscalePK id) {
		this.id = id;
	}

	public String getDesComuneDomFiscaleAttuale() {
		return desComuneDomFiscaleAttuale;
	}

	public void setDesComuneDomFiscaleAttuale(String desComuneDomFiscaleAttuale) {
		this.desComuneDomFiscaleAttuale = desComuneDomFiscaleAttuale;
	}

	public String getDesComuneDomFiscaleDic() {
		return desComuneDomFiscaleDic;
	}

	public void setDesComuneDomFiscaleDic(String desComuneDomFiscaleDic) {
		this.desComuneDomFiscaleDic = desComuneDomFiscaleDic;
	}

}