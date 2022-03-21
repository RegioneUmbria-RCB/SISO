package it.webred.ct.data.model.centriestivi;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * The persistent class for the SIT_CRES_TURNO database table.
 * 
 */
@Entity
@Table(name="SIT_CRES_TURNO")
public class SitCresTurno implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_TURNO")
	private String idTurno;

    @Temporal( TemporalType.DATE)
	@Column(name="A_DATA")
	private Date aData;

    @Temporal( TemporalType.DATE)
	@Column(name="DA_DATA")
	private Date daData;

	@Column(name="FK_IDCRES" )
	private String fkIdcres;

	@Column(name="NUM_TURNO")
	private BigDecimal numTurno;

	@Column(name="ONLINE_COD_TURNO")
	private String onlineCodTurno;
	
	@Column(name="BUS")
	private BigDecimal bus;

	//bi-directional many-to-one association to SitCresCentro
    @ManyToOne
	@JoinColumn(name="FK_IDCRES", insertable=false, updatable=false)
	private SitCresCentro sitCresCentro;
	
    public SitCresTurno() {
    }

	public String getIdTurno() {
		return this.idTurno;
	}

	public void setIdTurno(String idTurno) {
		this.idTurno = idTurno;
	}

	public Date getAData() {
		return this.aData;
	}
	
	public String getPeriodoString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd MMMM yyyy");
		String periodo = sdf.format(getDaData()) + " - " + sdf2.format(getAData());
		return periodo;
	}
	
	public String getBusString() {
		if(new BigDecimal(0).equals(bus))
			return "(non previsto bus)";
		return "";
	}

	public void setAData(Date aData) {
		this.aData = aData;
	}

	public Date getDaData() {
		return this.daData;
	}

	public void setDaData(Date daData) {
		this.daData = daData;
	}

	public SitCresCentro getSitCresCentro() {
		return sitCresCentro;
	}

	public void setSitCresCentro(SitCresCentro sitCresCentro) {
		this.sitCresCentro = sitCresCentro;
	}

	public BigDecimal getNumTurno() {
		return this.numTurno;
	}

	public void setNumTurno(BigDecimal numTurno) {
		this.numTurno = numTurno;
	}

	public String getOnlineCodTurno() {
		return this.onlineCodTurno;
	}

	public void setOnlineCodTurno(String onlineCodTurno) {
		this.onlineCodTurno = onlineCodTurno;
	}

	public String getFkIdcres() {
		return fkIdcres;
	}

	public void setFkIdcres(String fkIdcres) {
		this.fkIdcres = fkIdcres;
	}

	public BigDecimal getBus() {
		return bus;
	}

	public void setBus(BigDecimal bus) {
		this.bus = bus;
	}

}