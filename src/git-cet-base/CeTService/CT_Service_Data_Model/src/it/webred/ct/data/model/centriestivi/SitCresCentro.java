package it.webred.ct.data.model.centriestivi;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the SIT_CRES_CENTRO database table.
 * 
 */
@Entity
@Table(name="SIT_CRES_CENTRO")
public class SitCresCentro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idcres;

    @Temporal( TemporalType.DATE)
	@Column(name="A_DATA")
	private Date aData;

	@Column(name="BUS_OBBLIGATORIO")
	private BigDecimal busObbligatorio;

    @Temporal( TemporalType.DATE)
	@Column(name="DA_DATA")
	private Date daData;

	private String descrizione;

	@Column(name="FASCIA_ETA")
	private String fasciaEta;

	@Column(name="NUM_TURNI")
	private BigDecimal numTurni;

	@Column(name="ONLINE_CODICE_CRES")
	private String onlineCodiceCres;

	//bi-directional many-to-one association to SitCresTurno
	@OneToMany(mappedBy="sitCresCentro")
	private List<SitCresTurno> sitCresTurnos;
	
    public SitCresCentro() {
    }

	public String getIdcres() {
		return this.idcres;
	}

	public void setIdcres(String idcres) {
		this.idcres = idcres;
	}

	public Date getAData() {
		return this.aData;
	}

	public void setAData(Date aData) {
		this.aData = aData;
	}

	public BigDecimal getBusObbligatorio() {
		return this.busObbligatorio;
	}

	public void setBusObbligatorio(BigDecimal busObbligatorio) {
		this.busObbligatorio = busObbligatorio;
	}

	public Date getDaData() {
		return this.daData;
	}

	public void setDaData(Date daData) {
		this.daData = daData;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getFasciaEta() {
		return this.fasciaEta;
	}

	public void setFasciaEta(String fasciaEta) {
		this.fasciaEta = fasciaEta;
	}

	public BigDecimal getNumTurni() {
		return this.numTurni;
	}

	public void setNumTurni(BigDecimal numTurni) {
		this.numTurni = numTurni;
	}

	public String getOnlineCodiceCres() {
		return this.onlineCodiceCres;
	}

	public void setOnlineCodiceCres(String onlineCodiceCres) {
		this.onlineCodiceCres = onlineCodiceCres;
	}

	public List<SitCresTurno> getSitCresTurnos() {
		return sitCresTurnos;
	}

	public void setSitCresTurnos(List<SitCresTurno> sitCresTurnos) {
		this.sitCresTurnos = sitCresTurnos;
	}

}