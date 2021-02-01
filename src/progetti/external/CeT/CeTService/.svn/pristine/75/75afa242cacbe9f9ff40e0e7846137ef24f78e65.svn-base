package it.webred.ct.data.model.docfa;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * The persistent class for the DOCFA_PLANIMETRIE database table.
 * 
 */
@Entity
@Table(name="DOCFA_PLANIMETRIE")
public class DocfaPlanimetrie implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DocfaPlanimetriePK id;
	
    @Temporal( TemporalType.DATE)
	@Column(name="DATA_REGISTRAZIONE")
	private Date dataRegistrazione;

	private BigDecimal formato;

    @Temporal( TemporalType.DATE)
	private Date fornitura;

	@Column(name="IDENTIFICATIVO_IMMO")
	private BigDecimal identificativoImmo;

	private String protocollo;

	private BigDecimal scala;
	
	@Transient
	private String fornituraStr;

    public DocfaPlanimetrie() {
    }

	public Date getDataRegistrazione() {
		return this.dataRegistrazione;
	}

	public void setDataRegistrazione(Date dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}

	public BigDecimal getFormato() {
		return this.formato;
	}

	public void setFormato(BigDecimal formato) {
		this.formato = formato;
	}

	public Date getFornitura() {
		return this.fornitura;
	}

	public void setFornitura(Date fornitura) {
		this.fornitura = fornitura;
	}

	public BigDecimal getIdentificativoImmo() {
		return this.identificativoImmo;
	}

	public void setIdentificativoImmo(BigDecimal identificativoImmo) {
		this.identificativoImmo = identificativoImmo;
	}

	public String getProtocollo() {
		return this.protocollo;
	}

	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}

	public BigDecimal getScala() {
		return this.scala;
	}

	public void setScala(BigDecimal scala) {
		this.scala = scala;
	}

	public DocfaPlanimetriePK getId() {
		return id;
	}

	public void setId(DocfaPlanimetriePK id) {
		this.id = id;
	}

	public String getFornituraStr() {
		String fornituraStr="";
		
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			fornituraStr= sdf.format(fornitura);
		}catch(Exception e){
			
		}
		
		return fornituraStr;
	}

	public void setFornituraStr(String fornituraStr) {
		this.fornituraStr = fornituraStr;
	}

}