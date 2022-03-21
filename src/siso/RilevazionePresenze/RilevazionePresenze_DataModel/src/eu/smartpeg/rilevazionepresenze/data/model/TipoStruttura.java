package eu.smartpeg.rilevazionepresenze.data.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import eu.smartpeg.rilevazionepresenze.data.model.pai.ArCsPaiPTI;


/**
 * The persistent class for the AR_RP_TB_TIPO_STRUTTURA database table.
 *
 */
@Entity
@Table(name="AR_RP_TB_TIPO_STRUTTURA")
@NamedQuery(name="TipoStruttura.findAll", query="SELECT t FROM TipoStruttura t")
public class TipoStruttura implements Serializable {
	
		private static final long serialVersionUID = 1L;

		@Id
		private long id;

		private String code;

		private String descrizione;
		
		@Column(name = "TIPO_FUNZIONE")
		private String tipoFunzione;
		
		
		@OneToMany(mappedBy="tipoStruttura")	
		private List<Struttura> strutture;
		
		@OneToMany(mappedBy="tipoStruttura")	
		private List<ArCsPaiPTI> minori;
		
		
		public TipoStruttura() {
			
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getDescrizione() {
			return descrizione;
		}

		public void setDescrizione(String descrizione) {
			this.descrizione = descrizione;
		}

		public List<Struttura> getStrutture() {
			return strutture;
		}

		public void setStrutture(List<Struttura> strutture) {
			this.strutture = strutture;
		}

		public String getTipoFunzione() {
			return tipoFunzione;
		}

		public void setTipoFunzione(String tipoFunzione) {
			this.tipoFunzione = tipoFunzione;
		}

		public List<ArCsPaiPTI> getMinori() {
			return minori;
		}

		public void setMinori(List<ArCsPaiPTI> minori) {
			this.minori = minori;
		}
		
		
		
}