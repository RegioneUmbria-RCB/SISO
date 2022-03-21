package it.webred.ct.data.model.imu;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the SALDO_IMU_DATI_ELAB database table.
 * 
 */
@Entity
@Table(name="SALDO_IMU_DATI_ELAB")
public class SaldoImuDatiElab implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SaldoImuDatiElabPK id;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_INS")
	private Date dtIns;

    @Lob()
	private String json;

    public SaldoImuDatiElab() {
    }

	public SaldoImuDatiElabPK getId() {
		return this.id;
	}

	public void setId(SaldoImuDatiElabPK id) {
		this.id = id;
	}
	
	public Date getDtIns() {
		return this.dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public String getJson() {
		return this.json;
	}

	public void setJson(String json) {
		this.json = json;
	}

}