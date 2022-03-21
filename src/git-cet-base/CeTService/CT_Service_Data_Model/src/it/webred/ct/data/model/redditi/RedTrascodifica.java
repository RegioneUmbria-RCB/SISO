package it.webred.ct.data.model.redditi;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the RED_TRASCODIFICA database table.
 * 
 */
@Entity
@Table(name="RED_TRASCODIFICA")
public class RedTrascodifica implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RedTrascodificaPK id;
	
	private String descrizione;

	@Column(name="FLG_IMPORTO")
	private BigDecimal flgImporto;
	
	@Column(name="CENT_DIVISORE")
	private BigDecimal centDivisore;

    public RedTrascodifica() {
    }

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public BigDecimal getFlgImporto() {
		return this.flgImporto;
	}

	public void setFlgImporto(BigDecimal flgImporto) {
		this.flgImporto = flgImporto;
	}

	public BigDecimal getCentDivisore() {
		return centDivisore;
	}

	public void setCentDivisore(BigDecimal centDivisore) {
		this.centDivisore = centDivisore;
	}

	public RedTrascodificaPK getId() {
		return id;
	}

	public void setId(RedTrascodificaPK id) {
		this.id = id;
	}

	
}