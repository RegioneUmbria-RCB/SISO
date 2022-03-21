package it.webred.ct.data.model.centriestivi;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the SIT_CRES_FASCIA_ETA database table.
 * 
 */
@Entity
@Table(name="SIT_CRES_FASCIA_ETA")
public class SitCresFasciaEta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idfascia;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_FINE_VAL")
	private Date dataFineVal;

	private String descrizione;
	
	@Column(name="LABEL_PREFERENZA_TURNO")
	private String labelPreferenzaTurno;

    public SitCresFasciaEta() {
    }

	public String getIdfascia() {
		return this.idfascia;
	}

	public void setIdfascia(String idfascia) {
		this.idfascia = idfascia;
	}

	public Date getDataFineVal() {
		return this.dataFineVal;
	}

	public void setDataFineVal(Date dataFineVal) {
		this.dataFineVal = dataFineVal;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getLabelPreferenzaTurno() {
		return labelPreferenzaTurno;
	}

	public void setLabelPreferenzaTurno(String labelPreferenzaTurno) {
		this.labelPreferenzaTurno = labelPreferenzaTurno;
	}

}