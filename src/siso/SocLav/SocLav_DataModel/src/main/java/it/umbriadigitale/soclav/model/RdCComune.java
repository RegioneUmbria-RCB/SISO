package it.umbriadigitale.soclav.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="RDC_COMUNE")
public class RdCComune implements Serializable {

		@Id
	    private String belfiore;

		private String descrizione;

		public String getBelfiore() {
			return belfiore;
		}

		public void setBelfiore(String belfiore) {
			this.belfiore = belfiore;
		}

		public String getDescrizione() {
			return descrizione;
		}

		public void setDescrizione(String descrizione) {
			this.descrizione = descrizione;
		}

		 
		 
}