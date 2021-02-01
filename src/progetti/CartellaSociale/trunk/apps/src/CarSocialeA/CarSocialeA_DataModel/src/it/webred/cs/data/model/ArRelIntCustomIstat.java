package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the AR_REL_INT_CUSTOM_ISTAT database table.
 * 
 */
@Entity
@Table(name="AR_REL_INT_CUSTOM_ISTAT")
@NamedQueries(value={
			@NamedQuery(name="ArRelIntCustomIstat.findAll", query="SELECT a FROM ArRelIntCustomIstat a")
		})

	 
public class ArRelIntCustomIstat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	@Column(name = "PRESTAZIONE_ISTAT_CODICE")
	private String prestazioneIstatCodice;

	//bi-directional many-to-one association to CsCTipoInterventoCustom
	@ManyToOne
	@JoinColumn(name="CUSTOM_CLASSE_ID")
		private CsCTipoInterventoCustom csCInterventoCustom;

		public ArRelIntCustomIstat() {
		}

		public long getId() {
			return this.id;
		}

		public void setId(long id) {
			this.id = id;
		}
		
		public String getPrestazioneIstatCodice() {
			return prestazioneIstatCodice;
		}

		public void setPrestazioneIstatCodice(String prestazioneIstatCodice) {
			this.prestazioneIstatCodice = prestazioneIstatCodice;
		}

		public CsCTipoInterventoCustom getCsCInterventoCustom() {
			return csCInterventoCustom;
		}

		public void setCsCInterventoCustom(CsCTipoInterventoCustom csCInterventoCustom) {
			this.csCInterventoCustom = csCInterventoCustom;
		}

}
