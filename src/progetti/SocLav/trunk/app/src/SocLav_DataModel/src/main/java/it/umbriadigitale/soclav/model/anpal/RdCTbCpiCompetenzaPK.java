package it.umbriadigitale.soclav.model.anpal;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RdCTbCpiCompetenzaPK implements Serializable {

	@Column(name="COD_CPI")
	private String codCpi;
	
	@Column(name="COMUNE_COD_CAT")
	private String comuneCod;
	
	@Column(name="DT_INIZIO_VAL")
	private Date dtInizioVal;
	
	@Column(name="DT_FINE_VAL")
	private Date dtFineVal;

	public String getCodCpi() {
		return codCpi;
	}

	public void setCodCpi(String codCpi) {
		this.codCpi = codCpi;
	}

	public String getComuneCod() {
		return comuneCod;
	}

	public void setComuneCod(String comuneCod) {
		this.comuneCod = comuneCod;
	}

	public Date getDtInizioVal() {
		return dtInizioVal;
	}

	public void setDtInizioVal(Date dtInizioVal) {
		this.dtInizioVal = dtInizioVal;
	}

	public Date getDtFineVal() {
		return dtFineVal;
	}

	public void setDtFineVal(Date dtFineVal) {
		this.dtFineVal = dtFineVal;
	}
	
}
