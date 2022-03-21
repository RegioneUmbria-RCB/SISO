package it.webred.ct.data.model.redditi;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
/**
 * The primary key class for the RedRedditiDichiarati database table.
 * 
 */
@Embeddable
public class RedRedditiDichiaratiPK implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(name="IDE_TELEMATICO")
	private String ideTelematico;
	
	@Column(name="CODICE_FISCALE_DIC")
	private String codiceFiscaleDic;
	
	@Column(name="ANNO_IMPOSTA")
	private String annoImposta;

	@Column(name="CODICE_QUADRO")
	private String codiceQuadro;

	public  RedRedditiDichiaratiPK() {
		 
	}
	public String getCodiceFiscaleDic() {
		return this.codiceFiscaleDic;
	}

	public void setCodiceFiscaleDic(String codiceFiscaleDic) {
		this.codiceFiscaleDic = codiceFiscaleDic;
	}
	public String getIdeTelematico() {
		return this.ideTelematico;
	}

	public void setIdeTelematico(String ideTelematico) {
		this.ideTelematico = ideTelematico;
	}
	public String getAnnoImposta() {
		return this.annoImposta;
	}

	public void setAnnoImposta(String annoImposta) {
		this.annoImposta = annoImposta;
	}

	public String getCodiceQuadro() {
		return this.codiceQuadro;
	}

	public void setCodiceQuadro(String codiceQuadro) {
		this.codiceQuadro = codiceQuadro;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RedRedditiDichiaratiPK)) {
			return false;
		}
		RedRedditiDichiaratiPK castOther = (RedRedditiDichiaratiPK)other;
		return 
			this.ideTelematico.equals(castOther.ideTelematico)
			&& this.codiceFiscaleDic.equals(castOther.codiceFiscaleDic)
		    && this.codiceQuadro.equals(castOther.codiceQuadro)
		    && this.annoImposta.equals(castOther.annoImposta);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.ideTelematico.hashCode();
		hash = hash * prime + this.codiceFiscaleDic.hashCode();
		hash = hash * prime + this.codiceQuadro.hashCode();
		hash = hash * prime + this.annoImposta.hashCode();
		return hash;
    }
}
