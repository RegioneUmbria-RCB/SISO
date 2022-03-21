package eu.smartpeg.rilevazionepresenze.data.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import eu.smartpeg.rilevazionepresenze.data.model.pai.ArCsPaiPTI;

@Entity
@Table(name="AR_RP_TIPOLOGIA_SERVIZIO")
@NamedQuery(name="TipologiaServizio.findAll", query="SELECT t FROM TipologiaServizio t")
public class TipologiaServizio implements Serializable {
	
		private static final long serialVersionUID = 1L;

		@Id
		private long id;


		private String descrizione;
		
		@Column(name = "N_MIN_CONS")
		private long nMinCons;
		
		@Column(name = "N_MIN_MSNA_CONS")
		private long nMinMsnaCons;
		
		@Column(name = "N_MIN_AREA_PENALE_CONS")
		private long nMinAreaPenaleCons;
		
		@Column(name = "N_MIN_EMERG_CONS")
		private long nMinEmergCons;
		
		@Column(name = "N_FRATRIE_CONS")
		private long nFratrieCons;
		
		
		
		
		public TipologiaServizio() {
			
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getDescrizione() {
			return descrizione;
		}

		public void setDescrizione(String descrizione) {
			this.descrizione = descrizione;
		}

		public long getnMinCons() {
			return nMinCons;
		}

		public void setnMinCons(long nMinCons) {
			this.nMinCons = nMinCons;
		}

		public long getnMinMsnaCons() {
			return nMinMsnaCons;
		}

		public void setnMinMsnaCons(long nMinMsnaCons) {
			this.nMinMsnaCons = nMinMsnaCons;
		}

		public long getnMinAreaPenaleCons() {
			return nMinAreaPenaleCons;
		}

		public void setnMinAreaPenaleCons(long nMinAreaPenaleCons) {
			this.nMinAreaPenaleCons = nMinAreaPenaleCons;
		}

		public long getnMinEmergCons() {
			return nMinEmergCons;
		}

		public void setnMinEmergCons(long nMinEmergCons) {
			this.nMinEmergCons = nMinEmergCons;
		}

		public long getnFratrieCons() {
			return nFratrieCons;
		}

		public void setnFratrieCons(long nFratrieCons) {
			this.nFratrieCons = nFratrieCons;
		}

		
}