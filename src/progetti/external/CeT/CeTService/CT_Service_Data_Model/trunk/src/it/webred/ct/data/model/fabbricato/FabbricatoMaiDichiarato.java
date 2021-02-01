package it.webred.ct.data.model.fabbricato;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the FABBRICATO_MAI_DICHIARATO database table.
 * 
 */
@Entity
@Table(name="FABBRICATO_MAI_DICHIARATO")
public class FabbricatoMaiDichiarato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_COMUNICAZ_GU")
	private Date dataComunicazGu;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_MANCATA_DICH")
	private Date dataMancataDich;

	private String foglio;

	private String particella;

	private String sezione;

    public FabbricatoMaiDichiarato() {
    }

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDataComunicazGu() {
		return this.dataComunicazGu;
	}

	public void setDataComunicazGu(Date dataComunicazGu) {
		this.dataComunicazGu = dataComunicazGu;
	}

	public Date getDataMancataDich() {
		return this.dataMancataDich;
	}

	public void setDataMancataDich(Date dataMancataDich) {
		this.dataMancataDich = dataMancataDich;
	}

	public String getFoglio() {
		return this.foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public String getParticella() {
		return this.particella;
	}

	public void setParticella(String particella) {
		this.particella = particella;
	}

	public String getSezione() {
		return this.sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}

}